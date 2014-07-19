/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genomalysis.plugin.configuration;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.genomalysis.plugin.configuration.dialogs.BooleanConfigurator;
import org.genomalysis.plugin.configuration.dialogs.ColorConfigurator;
import org.genomalysis.plugin.configuration.dialogs.IntConfigurator;
import org.genomalysis.plugin.configuration.dialogs.ListConfigurator;
import org.genomalysis.plugin.configuration.dialogs.PatternConfigurator;
import org.genomalysis.plugin.configuration.dialogs.StringConfigurator;

/**
 *
 * @author ameyers
 */
public final class PropertyConfiguratorTable {

    private HashMap<Class, IPropertyConfigurator> configuratorTable;

    public PropertyConfiguratorTable() {
        configuratorTable = new HashMap<Class, IPropertyConfigurator>();

        //integers
        IntConfigurator cfg = new IntConfigurator();
        RegisterPropertyConfigurationDialog(int.class, cfg);
        RegisterPropertyConfigurationDialog(Integer.class, cfg);

        //strings
        BooleanConfigurator booleanConfigurator = new BooleanConfigurator();
        RegisterPropertyConfigurationDialog(Boolean.class, booleanConfigurator);
        RegisterPropertyConfigurationDialog(Boolean.TYPE, booleanConfigurator);
        RegisterPropertyConfigurationDialog(String.class, new StringConfigurator());
        //Regular Expressions
        RegisterPropertyConfigurationDialog(Pattern.class, new PatternConfigurator());
        RegisterPropertyConfigurationDialog(List.class, new ListConfigurator());
        RegisterPropertyConfigurationDialog(Color.class, new ColorConfigurator());
    }

    public void RegisterPropertyConfigurationDialog(Class toBeConfigured, IPropertyConfigurator configurationDialog) {
        if (!configuratorTable.containsKey(toBeConfigured)) {
            configuratorTable.put(toBeConfigured, configurationDialog);
        }
    }

    public boolean IsClassRegisteredForConfiguration(Class clazz) {
        return configuratorTable.containsKey(clazz);
    }

    public IPropertyConfigurator getConfigurationDialog(Class clazz) {
        return configuratorTable.get(clazz);
    }
}
