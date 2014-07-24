package org.genomalysis.plugin.configuration;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.plugin.configuration.annotations.Author;
import org.genomalysis.plugin.configuration.annotations.Configurator;
import org.genomalysis.plugin.configuration.annotations.Documentation;
import org.genomalysis.plugin.configuration.annotations.ReplacesType;

public final class ConfigurationTables
{
  private static PropertyConfiguratorTable propertyConfiguratorTable = new PropertyConfiguratorTable();
  private static DocumentationTable documentationTable = new DocumentationTable();
  private static ReplacementTable replacementTable = new ReplacementTable();

  public static PropertyConfiguratorTable getPropertyConfiguratorTable()
  {
    return propertyConfiguratorTable;
  }

  public static DocumentationTable getDocumentationTable() {
    return documentationTable;
  }

  public static ReplacementTable getReplacementTable() {
    return replacementTable;
  }

  public static void introspectClass(Class<?> clazz)
  {
    if ((clazz.getAnnotation(Documentation.class) != null) || (clazz.getAnnotation(Author.class) != null))
    {
      documentationTable.registerDocumentation(clazz);
    }

    Configurator configuratorAnnotation = (Configurator)clazz.getAnnotation(Configurator.class);

    if (configuratorAnnotation != null) {
      Class configuratorClass = configuratorAnnotation.value();
      try
      {
        Constructor cfgCtor = configuratorClass.getConstructor(new Class[0]);

        IPropertyConfigurator configurator = (IPropertyConfigurator)cfgCtor.newInstance(new Object[0]);
        propertyConfiguratorTable.RegisterPropertyConfigurationDialog(clazz, configurator);
      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }

    ReplacesType replacementAnnotation = (ReplacesType)clazz.getAnnotation(ReplacesType.class);
    if (replacementAnnotation != null)
      try {
        Class baseClazz = replacementAnnotation.value();
        replacementTable.registerReplacement(baseClazz, clazz);
      } catch (ConfigurationException ex) {
        Logger.getLogger(ConfigurationTables.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}