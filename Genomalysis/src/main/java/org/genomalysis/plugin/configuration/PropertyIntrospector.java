/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.PropertyGetter;
import org.genomalysis.plugin.configuration.annotations.PropertySetter;
import org.genomalysis.plugin.configuration.generics.GenericWrapper;

/**
 *
 * @author ameyers
 */
public class PropertyIntrospector {
    
    public static PropertyDescriptor getProperties(Object obj) throws ConfigurationException {
        PropertyDescriptor result = new PropertyDescriptor();
        List<Property> properties = new ArrayList<Property>();
        List<String> propertyNames = getPropertyNames(obj.getClass());
        findOrphanedSetters(obj.getClass(), propertyNames);
        for (String propertyName : propertyNames) {
            
            Method setter = findSetter(obj.getClass(), propertyName);
            Method getter = findGetter(obj.getClass(), propertyName);
            //make sure property methods match, and have the right 
            //return types / arguments
            validatePropertyMethods(propertyName, getter, setter);
            //everything looks OK so far...
            Property property = new Property();
            property.setGetter(getter);
            property.setSetter(setter);
            property.setTargetObject(obj);
            property.setPropertyName(propertyName);
      property.setPropertyType(getter.getReturnType());
      setPropertyConfigurator(property, obj);
            //now, let's see if we can find an IPropertyConfigurationDialog on the
            //method annotation of the setter...
      properties.add(property);

      if (getter.getReturnType() == List.class) {
        Type returnType = getter.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
          ParameterizedType pType = (ParameterizedType)returnType;
          Type[] args = pType.getActualTypeArguments();
          if ((args.length > 0) && (args[0] instanceof Class))
          {
            Object innerValue = null;
            try {
              if (getter.invoke(obj, new Object[0]) == null) {
                innerValue = new ArrayList();
                setter.invoke(obj, new Object[] { innerValue });
              } else {
                innerValue = getter.invoke(obj, new Object[0]);
              }
            } catch (Exception ex) {
              ex.printStackTrace();
            }

            Class genericListType = (Class)args[0];
            GenericWrapper listWrapper = new GenericWrapper(genericListType, innerValue);
            listWrapper.setDescription("List");
            GenericWrapper outerWrapper = new GenericWrapper(List.class, listWrapper);
            try {
              getter = GenericWrapper.class.getMethod("getValue", new Class[0]);
              setter = GenericWrapper.class.getMethod("setValue", new Class[] { Object.class });
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            property.setSetter(setter);
            property.setGetter(getter);
            property.setPropertyType(GenericWrapper.class);
            property.setTargetObject(outerWrapper);
          }
        }
      }
    }
    Property[] propArray = new Property[properties.size()];
    propArray = (Property[])properties.toArray(propArray);
    result.setProperties(propArray);
    return result;
  }

  private static void setPropertyConfigurator(Property property, Object host, Class<?> type) throws org.genomalysis.plugin.configuration.ConfigurationException {
    Method setter = property.getSetter();

    PropertySetter setterAnnotation = (PropertySetter)setter.getAnnotation(PropertySetter.class);

    if (setterAnnotation.ConfiguratorType() == VoidConfigurator.class)
    {
      Class propertyType = null;

      propertyType = type;

      if (ConfigurationTables.getPropertyConfiguratorTable().IsClassRegisteredForConfiguration(propertyType)) {
        IPropertyConfigurator dialog = ConfigurationTables.getPropertyConfiguratorTable().getConfigurationDialog(propertyType);
        property.setConfigurator(dialog);
      }
      else if (propertyType.getAnnotation(Configurator.class) != null) {
        Configurator propertyTypeAnnotation = (Configurator)propertyType.getAnnotation(Configurator.class);
        Class configClass = propertyTypeAnnotation.value();
        IPropertyConfigurator configurator = getConfigurationDialogInstance(configClass, propertyType, property.getPropertyName());
        property.setConfigurator(configurator);
      }
      else
      {
        property.setConfigurator(VoidConfigurator.getInstance());
      }
    }
    else {
      Class configClass = setterAnnotation.ConfiguratorType();
      property.setConfigurator(getConfigurationDialogInstance(configClass, host.getClass(), property.getPropertyName()));
    }
  }

  public static void setPropertyConfigurator(Property property, Object host) throws org.genomalysis.plugin.configuration.ConfigurationException {
    Class propertyType = property.getPropertyType();
    Method setter = property.getSetter();
    Method getter = property.getGetter();

    PropertySetter setterAnnotation = (PropertySetter)setter.getAnnotation(PropertySetter.class);

    if (setterAnnotation.ConfiguratorType() == VoidConfigurator.class) {
      Object currentPropertyValue = property.getPropertyValue();
      if (currentPropertyValue == null) {
        setPropertyConfigurator(property, host, getter.getReturnType());
      } else {
        setPropertyConfigurator(property, host, currentPropertyValue.getClass());
        if (property.getConfigurator() instanceof VoidConfigurator)
          setPropertyConfigurator(property, host, getter.getReturnType());
      }
    }
    else {
      setPropertyConfigurator(property, host, getter.getReturnType()); }
  }

    private static IPropertyConfigurator getConfigurationDialogInstance(Class<? extends IPropertyConfigurator> configuratorClass, Class configuringClass, String propertyName) throws ConfigurationException {
        try {
            Constructor<? extends IPropertyConfigurator> constructor = configuratorClass.getConstructor();
            IPropertyConfigurator instance = constructor.newInstance();
            return instance;
        } catch (NoSuchMethodException ex) {
            throw new ConfigurationException("Property config dialog defined for property " + propertyName + " has no default constructor defined");
        } catch (IllegalAccessException ex) {
            throw new ConfigurationException("There is a default constructor for configuration dialog defined for property " + propertyName + " in class" + configuringClass.getName() + ", but it is not public");
        } catch (Exception ex) {
            throw new ConfigurationException("There was an unexpected problem creating configuration dialog for property " + propertyName + " in class " + configuringClass);
        }
    }

    private static void validatePropertyMethods(String propertyName, Method getter, Method setter) throws ConfigurationException {
        //make sure setter takes one argument and returns void
        if (!(setter.getReturnType() == void.class && setter.getParameterTypes().length == 1)) {
            throw new ConfigurationException(
                    "Setter method for property " + propertyName + " does not match signature for setter methods (void setMethodName(Object value))");
        }
        //make sure getter returns an object of the same type that setter takes,
        //and make sure getter takes no arguments
        if (getter.getReturnType() != setter.getParameterTypes()[0]) {
            throw new ConfigurationException("Getter and Setter for property " + propertyName + " must be of the same type");
        }
        if (getter.getParameterTypes().length > 0) {
            throw new ConfigurationException("Getter method for property " + propertyName + " cannot accept parameters");
        }
    }

    private static void findOrphanedSetters(Class clazz, List<String> propertyNames) throws ConfigurationException {

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(PropertySetter.class) != null) {
                PropertySetter setter = method.getAnnotation(PropertySetter.class);
                if (!propertyNames.contains(setter.PropertyName())) {
                    throw new ConfigurationException("Property with name " + setter.PropertyName() + " has setter but no getter");
                }
            }
        }
    }

    private static Method findSetter(Class clazz, String propertyName) throws ConfigurationException {
        return findPropertyMethod(clazz, propertyName, PropertySetter.class);
    }

    private static Method findGetter(Class clazz, String propertyName) throws ConfigurationException {
        return findPropertyMethod(clazz, propertyName, PropertyGetter.class);
    }

    private static Method findPropertyMethod(Class clazz, String propertyName, Class annotationType) throws ConfigurationException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annotationType) != null) {
                Annotation ann = method.getAnnotation(annotationType);
                if (annotationMatchesProperty(ann, propertyName)) {
                    return method;
                }
            }
        }
        throw new ConfigurationException(annotationType.getName() + " with name " + propertyName + " could not be found in class " + clazz.getName());
    }

    private static boolean annotationMatchesProperty(Annotation ann, String propertyName) {
        if (ann instanceof PropertyGetter) {
            return ((PropertyGetter) ann).PropertyName().equals(propertyName);
        } else {
            return ((PropertySetter) ann).PropertyName().equals(propertyName);
        }
    }

    private static List<String> getPropertyNames(Class clazz) throws ConfigurationException {
        List<String> propertyNames = new ArrayList<String>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(PropertyGetter.class) != null) {
                PropertyGetter pg = method.getAnnotation(PropertyGetter.class);
                String propName = pg.PropertyName();
                if (propertyNames.contains(propName)) {
                    throw new ConfigurationException("Property " + propName + " has been declared more than once.");
                }
                propertyNames.add(propName);
            }
        }
        return propertyNames;
    }
}
