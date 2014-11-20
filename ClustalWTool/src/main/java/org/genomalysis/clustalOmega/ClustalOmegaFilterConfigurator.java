package org.genomalysis.clustalOmega;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

public class ClustalOmegaFilterConfigurator implements IPropertyConfigurator {
    private ClustalOmegaFilterConfigurationDialog dlg;

    public ClustalOmegaFilterConfigurator() {
        this.dlg = null;
    }

    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        if (this.dlg == null)
            this.dlg = new ClustalOmegaFilterConfigurationDialog(
                    DialogHelper.getRootFrame(base));

        if (target instanceof ClustalOmegaFilter) {
            ClustalOmegaFilter filter = (ClustalOmegaFilter) target;
            ClustalOmegaFilterConfiguration config = filter.getConfig();
            config = this.dlg.showDialog(config);
            filter.setConfig(config);
        } else {
            throw new ConfigurationException(
                    "This configurator only accepts objects of type ClustalOmegaFilter");
        }
        return target;
    }
}