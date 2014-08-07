package org.genomalysis.plugin.configuration.dialogs;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;

public class ColorConfigurator implements IPropertyConfigurator {
    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        Object result = target;
        if (target instanceof Color) {
            Color c = (Color) target;
            c = JColorChooser.showDialog(base, "Choose a Color", c);
            if (c != null)
                result = c;
        } else if (target != null) {
            throw new ConfigurationException("Expected Color, but got "
                    + target.getClass());
        }
        return result;
    }
}