/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.plugin.configuration.dialogs;

import java.awt.Frame;
import java.util.regex.Pattern;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;

/**
 *
 * @author ameyers
 */
public class PatternConfigurator implements IPropertyConfigurator {

    private PatternConfigurationDialog dlg = null;

    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        Frame frame = DialogHelper.getRootFrame(base);
        Pattern oldPattern = null;
        if (target == null) {
            target = Pattern.compile("\\w*");
        }
        if (target instanceof Pattern) {
            oldPattern = (Pattern) target;
        } else {
            oldPattern = Pattern.compile("\\w*");
        }
        if (dlg == null) {
            dlg = new PatternConfigurationDialog(frame, true);
        }
        dlg.setPattern(oldPattern);
        dlg.setVisible(true);
        return dlg.getPattern();
    }

}
