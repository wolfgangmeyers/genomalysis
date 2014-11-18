/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * This is meant to be a flag that a PropertySetter does not have a
 * configuration dialog defined...
 * 
 * @author ameyers
 */
public class VoidConfigurator implements IPropertyConfigurator {
    private static VoidConfigurator instance = new VoidConfigurator();

    public static VoidConfigurator getInstance() {
        return instance;
    }

    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        JOptionPane.showMessageDialog(base,
                "The secretion signal filters are not set up for configuration.",
                "Sorry", 2);
        return target;
    }
}
