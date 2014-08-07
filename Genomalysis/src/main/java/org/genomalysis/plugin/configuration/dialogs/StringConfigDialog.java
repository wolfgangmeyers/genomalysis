/*
 * StringConfigDialog.java
 *
 * Created on March 21, 2008, 12:39 PM
 */

package org.genomalysis.plugin.configuration.dialogs;

import org.genomalysis.plugin.configuration.ConfigurationException;

/**
 *
 * @author ameyers
 */
public class StringConfigDialog extends javax.swing.JDialog {
    private static final long serialVersionUID = 1L;
    private String value = "";

    /** Creates new form StringConfigDialog */
    public StringConfigDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtValue = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Enter Text");
        getContentPane().setLayout(new java.awt.BorderLayout(20, 20));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout());

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel1.add(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        txtValue.setColumns(20);
        txtValue.setRows(5);
        jScrollPane1.setViewportView(txtValue);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Enter text:");
        jPanel3.add(jLabel1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
    }// GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        value = txtValue.getText();
        this.setVisible(false);
    }// GEN-LAST:event_btnOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtValue;

    // End of variables declaration//GEN-END:variables

    public Object showDialog(Object target) throws ConfigurationException {
        if (target instanceof String) {
            txtValue.setText((String) target);
            value = (String) target;
        }
        this.setVisible(true);

        return value;
    }

}
