package org.genomalysis.plugin.configuration.dialogs;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BooleanConfigurationDialog extends JDialog
{
  boolean currentValue = true;
  private JButton btnCancel;
  private JButton btnOK;
  private ButtonGroup buttonGroup1;
  private JPanel jPanel1;
  private JRadioButton rbFalse;
  private JRadioButton rbTrue;

  public BooleanConfigurationDialog(Frame parent)
  {
    super(parent, true);
    initComponents();
  }

  private void initComponents()
  {
    this.buttonGroup1 = new ButtonGroup();
    this.rbTrue = new JRadioButton();
    this.rbFalse = new JRadioButton();
    this.jPanel1 = new JPanel();
    this.btnOK = new JButton();
    this.btnCancel = new JButton();

    setDefaultCloseOperation(2);
    setTitle("Select True or False");
    setResizable(false);
    getContentPane().setLayout(new GridLayout(3, 0, 0, 10));

    this.buttonGroup1.add(this.rbTrue);
    this.rbTrue.setSelected(true);
    this.rbTrue.setText("True");
    getContentPane().add(this.rbTrue);

    this.buttonGroup1.add(this.rbFalse);
    this.rbFalse.setText("False");
    getContentPane().add(this.rbFalse);

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

    getContentPane().add(this.jPanel1);

    pack();
  }

  private void btnCancelActionPerformed(ActionEvent evt) {
    setVisible(false);
  }

  private void btnOKActionPerformed(ActionEvent evt) {
    this.currentValue = this.rbTrue.isSelected();
    setVisible(false);
  }

  public Boolean showDialog(Boolean currentValue) {
    if (currentValue == null)
      currentValue = Boolean.valueOf(false);
    this.currentValue = currentValue.booleanValue();
    this.rbTrue.setSelected(currentValue.booleanValue());
    setVisible(true);
    return Boolean.valueOf(this.currentValue);
  }
}