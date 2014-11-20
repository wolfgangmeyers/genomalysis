package org.genomalysis.clustalw;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

public class ClustalWFilterConfigurator implements IPropertyConfigurator {
    private ClustalWFilterConfigurationDialog dlg;

    public ClustalWFilterConfigurator() {
        this.dlg = null;
    }

    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        if (this.dlg == null)
            this.dlg = new ClustalWFilterConfigurationDialog(
                    DialogHelper.getRootFrame(base));

        if (target instanceof ClustalWFilter) {
            ClustalWFilter filter = (ClustalWFilter) target;
            ClustalWFilterConfiguration config = filter.getConfig();
            config = this.dlg.showDialog(config);
            filter.setConfig(config);
        } else {
            throw new ConfigurationException(
                    "This configurator only accepts objects of type ClustalWFilter");
        }
        return target;
    }
}