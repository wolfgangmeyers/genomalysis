package org.genomalysis.clustalw;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

public class ClustalWFilterConfigurator
  implements IPropertyConfigurator
{
  private ClustalWFilterConfigurationDialog dlg;

  public ClustalWFilterConfigurator()
  {
    this.dlg = null; }

  public Object showDialog(JComponent base, Object target) throws ConfigurationException {
    if (this.dlg == null)
      this.dlg = new ClustalWFilterConfigurationDialog(DialogHelper.getRootFrame(base));

    if (target instanceof ClustalWFilterConfiguration) {
      ClustalWFilterConfiguration config = (ClustalWFilterConfiguration)target;
      target = this.dlg.showDialog(config);
    } else {
      throw new ConfigurationException("This configurator only accepts objects of type ClustalWFilterConfiguration");
    }
    return target;
  }
}