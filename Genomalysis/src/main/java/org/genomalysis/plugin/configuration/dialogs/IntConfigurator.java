/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.dialogs;

import java.awt.Frame;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;

/**
 *
 * @author ameyers
 */
public class IntConfigurator implements IPropertyConfigurator {

    private IntConfigDialog dlg = null;

    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        if (dlg == null) {
            Frame frame = DialogHelper.getRootFrame(base);
            dlg = new IntConfigDialog(frame);
        }
        dlg.showDialog(target);
        return dlg.getValue();
    }

}
