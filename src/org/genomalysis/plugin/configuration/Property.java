/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.plugin.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author ameyers
 */
public class Property {

    private Method getter;
    private Method setter;
    private Object targetObject;
  private Class<?> propertyType;
    private IPropertyConfigurator configurator;
    private String propertyName;

    public void configure(JComponent base) throws ConfigurationException {
        try {
            Object currentValue = getGetter().invoke(targetObject, new Object[]{});
            if(currentValue == null){
                try{
                    propertyType.asSubclass(Number.class);
                    Constructor<?> constructor = propertyType.getConstructor(new Class[]{String.class});
                    currentValue = constructor.newInstance("0");
                } catch(Exception ex){
                    try{
                        Constructor<?> constructor = propertyType.getConstructor(new Class[]{});
                        currentValue = constructor.newInstance(new Object[]{});
                    }catch(Exception ex2){}
                }
            }
            Object newValue = configurator.showDialog(base, currentValue);
            //System.out.println("Property.configure: newValue is " + newValue == null ? "null" : newValue.toString());
            getSetter().invoke(targetObject, newValue);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConfigurationException("There was a problem configuring this property:\n" + ex.getMessage());
        }
    }

    public Object getPropertyValue(){
        try{
            return getter.invoke(targetObject, new Object[]{});
        }catch(Exception ex){
            return "Error fetching property: " + ex.getClass().getName();
        }
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public IPropertyConfigurator getConfigurator() {
        return configurator;
    }

    public void setConfigurator(IPropertyConfigurator configurator) {
        this.configurator = configurator;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }
  public Class<?> getPropertyType() {
    return this.propertyType;
  }

  public void setPropertyType(Class<?> propertyType) {
    this.propertyType = propertyType;
  }
}
