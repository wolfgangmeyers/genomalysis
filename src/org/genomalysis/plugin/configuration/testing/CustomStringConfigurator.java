/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.testing;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;

/**
 *
 * @author ameyers
 */
public class CustomStringConfigurator implements IPropertyConfigurator{

    public Object showDialog(JComponent base, Object target) throws ConfigurationException {
        return "CustomStringConfigurator was here!";
    }

}
