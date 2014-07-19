package org.genomalysis.plugin.configuration.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.genomalysis.plugin.configuration.ConfigurationException;
import org.genomalysis.plugin.configuration.Property;
import org.genomalysis.plugin.configuration.generics.GenericWrapper;

public class GenericListDialog extends JDialog
{
  private Class<?> type = null;
  private List<Object> elementList = new ArrayList();
  private List<Property> propList = new ArrayList();
  private List<JLabel> indexLabels = new ArrayList();
  private JPanel StatsPanel;
  private JButton btnCancel;
  private JButton btnOK;
  private JPanel buttonsPanel;
  private JPanel controlPanel;
  private JButton jButton1;
  private JButton jButton2;
  private JButton jButton3;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JPanel jPanel3;
  private JPanel listPanel;
  private JPanel mainPanel;

  public GenericListDialog(Frame parent)
  {
    super(parent, true);
    initComponents();
  }

  private void initComponents()
  {
    this.mainPanel = new JPanel();
    this.controlPanel = new JPanel();
    this.jPanel2 = new JPanel();
    this.jButton1 = new JButton();
    this.jPanel1 = new JPanel();
    this.btnOK = new JButton();
    this.btnCancel = new JButton();
    this.listPanel = new JPanel();
    this.jPanel3 = new JPanel();
    this.StatsPanel = new JPanel();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.buttonsPanel = new JPanel();
    this.jButton2 = new JButton();
    this.jButton3 = new JButton();
    this.jLabel3 = new JLabel();

    setDefaultCloseOperation(2);

    this.mainPanel.setLayout(new BorderLayout(0, 20));

    this.controlPanel.setLayout(new BorderLayout());

    this.jButton1.setText("Add...");
    this.jButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }

    });
    this.jPanel2.add(this.jButton1);

    this.controlPanel.add(this.jPanel2, "North");

    this.btnOK.setText("OK");
    this.btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
          btnOKActionPerformed(evt);
      }

    });
    this.jPanel1.add(this.btnOK);

    this.btnCancel.setText("Cancel");
    this.btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }

    });
    this.jPanel1.add(this.btnCancel);

    this.controlPanel.add(this.jPanel1, "South");

    this.mainPanel.add(this.controlPanel, "Center");

    this.listPanel.setLayout(new GridLayout(1, 1, 0, 10));

    this.jPanel3.setLayout(new BorderLayout());

    this.jLabel1.setText("0:");
    this.StatsPanel.add(this.jLabel1);

    this.jLabel2.setText("Typename");
    this.StatsPanel.add(this.jLabel2);

    this.jPanel3.add(this.StatsPanel, "West");

    this.buttonsPanel.setLayout(new GridLayout(1, 0, 10, 0));

    this.jButton2.setText("Configure...");
    this.buttonsPanel.add(this.jButton2);

    this.jButton3.setText("Remove");
    this.buttonsPanel.add(this.jButton3);

    this.jPanel3.add(this.buttonsPanel, "East");

    this.jLabel3.setHorizontalAlignment(0);
    this.jLabel3.setText("Object.toString()");
    this.jPanel3.add(this.jLabel3, "Center");

    this.listPanel.add(this.jPanel3);

    this.mainPanel.add(this.listPanel, "North");

    getContentPane().add(this.mainPanel, "Center");

    pack();
  }

  private void jButton1ActionPerformed(ActionEvent evt) {
    GenericWrapper elem = new GenericWrapper(this.type);
    Property prop = elem.getProperty();
    this.propList.add(prop);
    JPanel panel = assembleElementPanel(prop, elem);
    addPanelElement(panel);
    updateIndexLabels();
    pack();
  }

  private void addPanelElement(JPanel panel) {
    int rows = ((GridLayout)this.listPanel.getLayout()).getRows();
    ((GridLayout)this.listPanel.getLayout()).setRows(rows + 1);
    this.listPanel.add(panel);
  }

  private void btnOKActionPerformed(ActionEvent evt) {
    this.elementList.clear();
    for (Iterator i$ = this.propList.iterator(); i$.hasNext(); ) { Property prop = (Property)i$.next();
      Object element = prop.getPropertyValue();
      this.elementList.add(element);
    }
    setVisible(false);
  }

  private void btnCancelActionPerformed(ActionEvent evt) {
    setVisible(false);
  }

  private JPanel assembleElementPanel(final Property prop, final Object host) {
    final JDialog dlg = this;
    final JPanel container = new JPanel();
    final JPanel statpanel = new JPanel();
    final JPanel buttonpanel = new JPanel();
    final JButton removeButton = new JButton("Remove");
    final JButton configureButton = new JButton("Configure...");

    BorderLayout containerLayout = new BorderLayout();
    container.setLayout(containerLayout);
    FlowLayout statLayout = new FlowLayout(); statLayout.setHgap(5); statLayout.setVgap(5);
    statpanel.setLayout(statLayout);
    GridLayout buttonLayout = new GridLayout(1, 2, 10, 0);
    buttonpanel.setLayout(buttonLayout);

    final JLabel indexLabel = new JLabel(String.valueOf(this.elementList.size()));
    final JLabel typeLabel = new JLabel(prop.getPropertyValue().getClass().getSimpleName());

    final JLabel valueLabel = new JLabel(prop.getPropertyValue().toString());

    this.indexLabels.add(indexLabel);

    buttonpanel.add(configureButton);
    buttonpanel.add(removeButton);
    statpanel.add(indexLabel);
    statpanel.add(indexLabel);
    statpanel.add(typeLabel);
    container.add(statpanel, "West");
    container.add(valueLabel, "Center");
    container.add(buttonpanel, "East");

    removeButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt) {
        dlg.remove(container);
        propList.remove(prop);
        int rows = ((GridLayout)dlg.getLayout()).getRows();
        ((GridLayout)dlg.getLayout()).setRows(rows - 1);
        dlg.pack();
        dlg.remove(indexLabel);
        //GenericListDialog.access$600(this.this$0);

      }

    });
    configureButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Object currentValue;
        try {
          currentValue = prop.getPropertyValue();
          if (currentValue == null)
            DialogHelper.attemptNullPropertyInstantiation(prop, host, container);

          prop.configure(container);
          currentValue = prop.getPropertyValue();
          if (currentValue == null) {
            valueLabel.setText("NULL");
            typeLabel.setText(prop.getGetter().getReturnType().getSimpleName());
          } else {
            valueLabel.setText(currentValue.toString());
            typeLabel.setText(currentValue.getClass().getSimpleName());
          }
          dlg.pack();
        } catch (ConfigurationException ex) {
          ex.printStackTrace();
          _showError(ex.getMessage());
        }

      }

    });
    return container;
  }

  private void _showError(String message) {
    JOptionPane.showMessageDialog(this.controlPanel, message, "Error", 0);
  }

  private void updateIndexLabels() {
    for (int i = 0; i < this.indexLabels.size(); ++i) {
      JLabel indexLabel = (JLabel)this.indexLabels.get(i);
      indexLabel.setText(String.valueOf(i));
      indexLabel.repaint();
    }
  }

  public void showDialog(List toBeConfigured, Class<?> genericType) {
    setTitle("Configuration: List of " + genericType.getSimpleName());
    this.elementList = toBeConfigured;
    this.type = genericType;
    this.listPanel.removeAll();
    ((GridLayout)this.listPanel.getLayout()).setRows(0);
    this.indexLabels.clear();
    this.propList.clear();

    for (Iterator i$ = toBeConfigured.iterator(); i$.hasNext(); ) { Object o = i$.next();
      GenericWrapper elem = new GenericWrapper(genericType);
      elem.setValue(o);
      Property prop = elem.getProperty();
      this.propList.add(prop);
      JPanel propPanel = assembleElementPanel(prop, elem);
      addPanelElement(propPanel);
    }
    updateIndexLabels();

    pack();
    setVisible(true);
  }
}