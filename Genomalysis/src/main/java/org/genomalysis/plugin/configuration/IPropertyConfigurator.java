/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration;

import javax.swing.JComponent;

/**
 *
 * @author ameyers
 */
public interface IPropertyConfigurator {
    Object showDialog(JComponent base, Object target)
            throws ConfigurationException;
}
