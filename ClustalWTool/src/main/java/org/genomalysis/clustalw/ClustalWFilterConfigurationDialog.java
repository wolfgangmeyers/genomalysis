package org.genomalysis.clustalw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ClustalWFilterConfigurationDialog extends JDialog
{
  private ClustalWFilterConfiguration myConfig;
  NumberFormat fmt = NumberFormat.getPercentInstance();
  private JButton btnCancel;
  private JButton btnOK;
  private JCheckBox chkEnableIdentityThreshold;
  private JCheckBox chkEnableStrongThreshold;
  private JCheckBox chkEnableWeakThreshold;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JPanel jPanel3;
  private JPanel jPanel4;
  private JPanel jPanel5;
  private JPanel jPanel6;
  private JPanel jPanel7;
  private JPanel jPanel8;
  private JPanel jPanel9;
  private JLabel lblIdentityThreshold;
  private JLabel lblStrongThreshold;
  private JLabel lblWeakThreshold;
  private JSlider sldrIdentityThreshold;
  private JSlider sldrStrongThreshold;
  private JSlider sldrWeakThreshold;

  public ClustalWFilterConfigurationDialog(Frame parent)
  {
    super(parent, true);
    initComponents();
  }

  private void initComponents()
  {
    this.jPanel1 = new JPanel();
    this.jPanel6 = new JPanel();
    this.jLabel1 = new JLabel();
    this.lblIdentityThreshold = new JLabel();
    this.sldrIdentityThreshold = new JSlider();
    this.jPanel7 = new JPanel();
    this.chkEnableIdentityThreshold = new JCheckBox();
    this.jPanel2 = new JPanel();
    this.jPanel8 = new JPanel();
    this.jLabel2 = new JLabel();
    this.lblStrongThreshold = new JLabel();
    this.sldrStrongThreshold = new JSlider();
    this.chkEnableStrongThreshold = new JCheckBox();
    this.jPanel3 = new JPanel();
    this.jPanel9 = new JPanel();
    this.jLabel3 = new JLabel();
    this.lblWeakThreshold = new JLabel();
    this.sldrWeakThreshold = new JSlider();
    this.chkEnableWeakThreshold = new JCheckBox();
    this.jPanel4 = new JPanel();
    this.jPanel5 = new JPanel();
    this.btnOK = new JButton();
    this.btnCancel = new JButton();

    setDefaultCloseOperation(2);
    setTitle("Configure ClustalW Filter");
    getContentPane().setLayout(new GridLayout(4, 0));

    this.jPanel1.setLayout(new GridLayout(3, 0));

    this.jLabel1.setText("Identity Threshold:");
    this.jPanel6.add(this.jLabel1);

    this.lblIdentityThreshold.setText("0%");
    this.jPanel6.add(this.lblIdentityThreshold);

    this.jPanel1.add(this.jPanel6);

    this.sldrIdentityThreshold.setValue(0);
    this.sldrIdentityThreshold.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        sldrIdentityThresholdStateChanged(evt);
      }

    });
    this.jPanel1.add(this.sldrIdentityThreshold);

    this.jPanel7.setLayout(new BorderLayout());

    this.chkEnableIdentityThreshold.setText("Enable");
    this.jPanel7.add(this.chkEnableIdentityThreshold, "Center");

    this.jPanel1.add(this.jPanel7);

    getContentPane().add(this.jPanel1);

    this.jPanel2.setLayout(new GridLayout(3, 0));

    this.jLabel2.setText("Strong Threshold:");
    this.jPanel8.add(this.jLabel2);

    this.lblStrongThreshold.setText("0%");
    this.jPanel8.add(this.lblStrongThreshold);

    this.jPanel2.add(this.jPanel8);

    this.sldrStrongThreshold.setValue(0);
    this.sldrStrongThreshold.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        sldrStrongThresholdStateChanged(evt);
      }

    });
    this.jPanel2.add(this.sldrStrongThreshold);

    this.chkEnableStrongThreshold.setText("Enable");
    this.jPanel2.add(this.chkEnableStrongThreshold);

    getContentPane().add(this.jPanel2);

    this.jPanel3.setLayout(new GridLayout(3, 0));

    this.jLabel3.setText("Weak Threshold:");
    this.jPanel9.add(this.jLabel3);

    this.lblWeakThreshold.setText("0%");
    this.jPanel9.add(this.lblWeakThreshold);

    this.jPanel3.add(this.jPanel9);

    this.sldrWeakThreshold.setValue(0);
    this.sldrWeakThreshold.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        sldrWeakThresholdStateChanged(evt);
      }

    });
    this.jPanel3.add(this.sldrWeakThreshold);

    this.chkEnableWeakThreshold.setText("Enable");
    this.jPanel3.add(this.chkEnableWeakThreshold);

    getContentPane().add(this.jPanel3);

    this.jPanel4.setLayout(new BorderLayout());

    this.btnOK.setText("OK");
    this.btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnOKActionPerformed(evt);
      }

    });
    this.jPanel5.add(this.btnOK);

    this.btnCancel.setText("Cancel");
    this.btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }

    });
    this.jPanel5.add(this.btnCancel);

    this.jPanel4.add(this.jPanel5, "South");

    getContentPane().add(this.jPanel4);

    pack();
  }

  private void btnOKActionPerformed(ActionEvent evt) {
    boolean valid = (this.chkEnableIdentityThreshold.isEnabled()) || (this.chkEnableStrongThreshold.isEnabled()) || (this.chkEnableWeakThreshold.isEnabled());

    if (valid) {
      updateModel();
      setVisible(false);
    } else {
      JOptionPane.showMessageDialog(this.jPanel1, "You must enable at least one threshold", "Error", 0);
    }
  }

  private void btnCancelActionPerformed(ActionEvent evt) {
    setVisible(false);
  }

  private void sldrIdentityThresholdStateChanged(ChangeEvent evt) {
    this.lblIdentityThreshold.setText(this.fmt.format(this.sldrIdentityThreshold.getValue() / 100.0D));
  }

  private void sldrStrongThresholdStateChanged(ChangeEvent evt) {
    this.lblStrongThreshold.setText(this.fmt.format(this.sldrStrongThreshold.getValue() / 100.0D));
  }

  private void sldrWeakThresholdStateChanged(ChangeEvent evt) {
    this.lblWeakThreshold.setText(this.fmt.format(this.sldrWeakThreshold.getValue() / 100.0D));
  }

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame();
        ClustalWFilterConfiguration config = new ClustalWFilterConfiguration();
        ClustalWFilterConfiguration newConfig = new ClustalWFilterConfigurationDialog(frame).showDialog(config);
        System.out.println("Old config:\n\n" + config.toString() + "\n\n");
        System.out.println("New config\n\n" + newConfig);
      }
    });
  }

  public ClustalWFilterConfiguration showDialog(ClustalWFilterConfiguration initialConfig) {
    this.myConfig = initialConfig;
    if (this.myConfig == null)
      this.myConfig = new ClustalWFilterConfiguration();
    updateUI();
    setVisible(true);
    return this.myConfig;
  }

  private void updateUI() {
    this.chkEnableIdentityThreshold.setSelected(this.myConfig.getIdentityConstraint().isEnabled());
    this.chkEnableStrongThreshold.setSelected(this.myConfig.getStrongConstraint().isEnabled());
    this.chkEnableWeakThreshold.setSelected(this.myConfig.getWeakConstraint().isEnabled());
    this.sldrIdentityThreshold.setValue((int)(this.myConfig.getIdentityConstraint().getLowerBound() * 100.0D));
    this.sldrStrongThreshold.setValue((int)(this.myConfig.getStrongConstraint().getLowerBound() * 100.0D));
    this.sldrWeakThreshold.setValue((int)(this.myConfig.getWeakConstraint().getLowerBound() * 100.0D));
  }

  private void updateModel() {
    this.myConfig.getIdentityConstraint().setEnabled(this.chkEnableIdentityThreshold.isSelected());
    this.myConfig.getStrongConstraint().setEnabled(this.chkEnableStrongThreshold.isSelected());
    this.myConfig.getWeakConstraint().setEnabled(this.chkEnableWeakThreshold.isSelected());
    this.myConfig.getIdentityConstraint().setLowerBound(this.sldrIdentityThreshold.getValue() / 100.0D);
    this.myConfig.getStrongConstraint().setLowerBound(this.sldrStrongThreshold.getValue() / 100.0D);
    this.myConfig.getWeakConstraint().setLowerBound(this.sldrWeakThreshold.getValue() / 100.0D);
  }
}