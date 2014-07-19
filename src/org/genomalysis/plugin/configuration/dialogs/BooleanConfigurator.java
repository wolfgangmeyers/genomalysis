package org.genomalysis.plugin.configuration.dialogs;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;

public class BooleanConfigurator
  implements IPropertyConfigurator
{
  private BooleanConfigurationDialog dlg;

  public BooleanConfigurator()
  {
    this.dlg = null; }

  public Object showDialog(JComponent base, Object target) throws ConfigurationException {
    if (this.dlg == null)
      this.dlg = new BooleanConfigurationDialog(DialogHelper.getRootFrame(base));

    if (target == null)
      target = Boolean.valueOf(false);

    if (target instanceof Boolean) {
      Boolean b = (Boolean)target;
      target = this.dlg.showDialog(b);
    }
    return target;
  }
}