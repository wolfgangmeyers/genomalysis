/*
 * IntConfigDialog.java
 *
 * Created on March 21, 2008, 12:03 PM
 */

package org.genomalysis.plugin.configuration.dialogs;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.genomalysis.plugin.configuration.ConfigurationException;

/**
 *
 * @author  ameyers
 */
public class IntConfigDialog extends javax.swing.JDialog{
    
    private int value = 0;
    /** Creates new form IntConfigDialog */
    public IntConfigDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtValue = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Enter Integer Value");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(2, 0, 20, 20));

        jPanel1.setLayout(new java.awt.BorderLayout());

        txtValue.setText("0");
        jPanel1.add(txtValue, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Value:");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout());

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel2.add(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        try{
            setValue(Integer.parseInt(txtValue.getText()));
            setVisible(false);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_btnOKActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed
    
    public void showDialog(Object target) throws ConfigurationException {
        if(target instanceof Integer){
            setValue((Integer) (int)(Integer)target);
            txtValue.setText(target.toString());
        }
        setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
}