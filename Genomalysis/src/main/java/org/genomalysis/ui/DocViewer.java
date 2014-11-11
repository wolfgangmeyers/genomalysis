/*
 * DocViewer.java
 *
 * Created on March 22, 2008, 3:09 AM
 */

package org.genomalysis.ui;

import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.WindowConstants;

import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

/**
 *
 * @author ameyers
 */
public class DocViewer extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    private static DocViewer instance;

    public static void showDocumentation(JComponent base, String doc) {
        showDocumentation(base, doc, "Documentation");
    }

    public static void showDocumentation(JComponent base, String doc,
            String title) {
        if (instance == null) {
            Frame frame = DialogHelper.getRootFrame(base);
            instance = new DocViewer(frame);
        }
        instance.jTextArea1.setText(doc);
        instance.setTitle(title);
        instance.setVisible(true);
    }

    /** Creates new form DocViewer */
    private DocViewer(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        this.setSize(500, 500);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel2.add(btnOK);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        setVisible(false);
    }// GEN-LAST:event_btnOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
