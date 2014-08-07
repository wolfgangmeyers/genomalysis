package org.genomalysis.plugin.configuration.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.IPropertyConfigurator;
import org.genomalysis.plugin.configuration.generics.GenericWrapper;

public class ListConfigurator implements IPropertyConfigurator {
    private GenericListDialog dlg;

    public ListConfigurator() {
        this.dlg = null;
    }

    @SuppressWarnings("unchecked")
    public Object showDialog(JComponent base, Object target)
            throws ConfigurationException {
        if (this.dlg == null)
            this.dlg = new GenericListDialog(DialogHelper.getRootFrame(base));

        Object result = target;
        if (result instanceof GenericWrapper) {
            List<Object> list;
            GenericWrapper gw = (GenericWrapper) result;
            Object o = gw.getValue();
            if (o instanceof List) {
                list = (List<Object>) o;
                this.dlg.showDialog(list, gw.getValueType());
            } else if (o == null) {
                list = new ArrayList<>();
                gw.setValue(list);
                this.dlg.showDialog(list, gw.getValueType());
            }
        } else if (result instanceof List) {
            List<Object> list = (List<Object>) result;
            this.dlg.showDialog(list, Object.class);
        }
        return result;
    }
}