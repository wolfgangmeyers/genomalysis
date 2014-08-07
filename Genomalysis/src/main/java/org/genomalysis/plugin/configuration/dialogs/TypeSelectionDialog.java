package org.genomalysis.plugin.configuration.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TypeSelectionDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private boolean accept = false;
    private List<Class<?>> types = new ArrayList<>();
    private JButton btnCancel;
    private JButton jButton1;
    private JComboBox<String> cbClassNames;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;

    public Class<?> getSelectedType() {
        Class<?> result = null;
        int index = this.cbClassNames.getSelectedIndex();
        if ((index > -1) && (index < this.types.size())) {
            result = this.types.get(index);
        }

        return result;
    }

    public TypeSelectionDialog(Frame parent) {
        super(parent, true);
        initComponents();
    }

    public boolean showDialog(List<Class<?>> classes) {
        this.types.clear();
        this.types.addAll(classes);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (Class<?> clazz : classes) {
            model.addElement(clazz.getName());
        }
        this.cbClassNames.setModel(model);

        this.accept = false;
        pack();

        setVisible(true);
        return this.accept;
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jLabel1 = new JLabel();
        this.cbClassNames = new JComboBox<String>();
        this.jPanel2 = new JPanel();
        this.btnCancel = new JButton();
        this.jButton1 = new JButton();

        setDefaultCloseOperation(2);
        setResizable(false);

        this.jPanel1.setLayout(new BorderLayout(0, 10));

        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText("Please Select a Type:");
        this.jPanel1.add(this.jLabel1, "North");

        this.cbClassNames.setBackground(new Color(255, 255, 255));
        this.jPanel1.add(this.cbClassNames, "Center");

        this.jPanel2.setLayout(new GridLayout(1, 0, 10, 0));

        this.btnCancel.setText("Cancel");
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }

        });
        this.jPanel2.add(this.btnCancel);

        this.jButton1.setText("OK");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }

        });
        this.jPanel2.add(this.jButton1);

        this.jPanel1.add(this.jPanel2, "South");

        getContentPane().add(this.jPanel1, "Center");

        pack();
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        setVisible(false);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        if (this.cbClassNames.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this.jLabel1, "Select a type",
                    "Error", 0);
        } else {
            this.accept = true;
            setVisible(false);
        }
    }
}