package org.genomalysis.plugin.configuration.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RenameDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private String name;
    private JButton btnCancel;
    private JButton btnOK;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextField txtName;

    public RenameDialog(Frame parent) {
        super(parent, true);
        initComponents();
    }

    public String showDialog(String currentName) {
        if (currentName == null)
            currentName = "";
        this.name = currentName;
        this.txtName
                .setText("                                                                                             ");
        pack();
        this.txtName.setText(this.name);
        setVisible(true);
        return this.name;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.txtName = new JTextField();
        this.jPanel2 = new JPanel();
        this.btnOK = new JButton();
        this.btnCancel = new JButton();

        setDefaultCloseOperation(0);
        setTitle("Rename Instance");
        setResizable(false);
        getContentPane().setLayout(new GridLayout(2, 0, 0, 10));

        this.jPanel1.setLayout(new BorderLayout(10, 0));

        this.jLabel1.setText("Enter New Name:");
        this.jPanel1.add(this.jLabel1, "West");

        this.txtName.setMinimumSize(new Dimension(100, 20));
        this.jPanel1.add(this.txtName, "Center");

        getContentPane().add(this.jPanel1);

        this.btnOK.setText("OK");
        this.btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }

        });
        this.jPanel2.add(this.btnOK);

        this.btnCancel.setText("Cancel");
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }

        });
        this.jPanel2.add(this.btnCancel);

        getContentPane().add(this.jPanel2);

        pack();
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        setVisible(false);
    }

    private void btnOKActionPerformed(ActionEvent evt) {
        this.name = this.txtName.getText();
        setVisible(false);
    }
}