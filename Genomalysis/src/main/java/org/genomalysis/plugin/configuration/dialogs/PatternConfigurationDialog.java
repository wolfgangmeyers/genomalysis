/*
 * PatternConfigurationDialog.java
 *
 * Created on March 22, 2008, 10:45 AM
 */

package org.genomalysis.plugin.configuration.dialogs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

/**
 *
 * @author ameyers
 */
public class PatternConfigurationDialog extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    private static final String invalidMessage = "Regular Expression is invalid";
    private static final String empty = "";
    private Pattern oldValue = null;
    private Pattern newValue = null;

    public Pattern getPattern() {
        return oldValue;
    }

    public void setPattern(Pattern value) {
        this.oldValue = value;
        this.newValue = value;
        txtPattern.setText(newValue.pattern());
    }

    /** Creates new form PatternConfigurationDialog */
    public PatternConfigurationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        jLabel1 = new javax.swing.JLabel();
        txtPattern = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblErrorMessage = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnTest = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTestRegex = new javax.swing.JTextArea();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        jPanel2.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        jPanel1.setLayout(new java.awt.BorderLayout(5, 5));

        jLabel1.setText("Regex Pattern (Case Sensitive):");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        txtPattern.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPatternKeyReleased(evt);
            }
        });
        jPanel1.add(txtPattern, java.awt.BorderLayout.CENTER);
        
        popupMenu = new JPopupMenu();
        addPopup(txtPattern, popupMenu);
        
        mntmCut = new JMenuItem("Cut");
        popupMenu.add(mntmCut);
        this.mntmCut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCutActionPerformed(evt);
            }
        });
        mntmCopy = new JMenuItem("Copy");
        popupMenu.add(mntmCopy);
        this.mntmCopy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCopyActionPerformed(evt);
            }
        });
        mntmPaste = new JMenuItem("Paste");
        popupMenu.add(mntmPaste);
        this.mntmPaste.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequencePasteActionPerformed(evt);
            }
        });
        jPanel2.add(jPanel1);

        lblErrorMessage.setForeground(new java.awt.Color(255, 51, 51));
        jPanel3.add(lblErrorMessage);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout(10, 10));

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel5.add(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel5.add(btnCancel);

        jPanel4.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,
                56, 5));

        jLabel2.setText("Test Regex Pattern in Text Area Below");
        jPanel6.add(jLabel2);

        btnTest.setText("Test");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });
        jPanel6.add(btnTest);

        jPanel4.add(jPanel6, java.awt.BorderLayout.NORTH);

        txtTestRegex.setColumns(20);
        txtTestRegex.setRows(5);
        txtTestRegex.setLineWrap(true);
        txtTestRegex
                .setText("Enter text here to test Regular Expression Pattern");
        jScrollPane1.setViewportView(txtTestRegex);
        
        popupMenu_1 = new JPopupMenu();
        addPopup(txtTestRegex, popupMenu_1);
        
        mntmCut_1 = new JMenuItem("Cut");
        popupMenu_1.add(mntmCut_1);
        this.mntmCut_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCutActionPerformed2(evt);
            }
        });
        mntmCopy_1 = new JMenuItem("Copy");
        popupMenu_1.add(mntmCopy_1);
        this.mntmCopy_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequenceCopyActionPerformed2(evt);
            }
        });
        mntmPaste_1 = new JMenuItem("Paste");
        popupMenu_1.add(mntmPaste_1);
        this.mntmPaste_1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuViewSequencePasteActionPerformed2(evt);
            }
        });
        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPatternKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtPatternKeyReleased
        try {
            Pattern pattern = Pattern.compile(txtPattern.getText());
            lblErrorMessage.setText(empty);
            btnOK.setEnabled(true);
            btnTest.setEnabled(true);
            newValue = pattern;
        } catch (PatternSyntaxException ex) {
            lblErrorMessage.setText(invalidMessage);
            btnOK.setEnabled(false);
            btnTest.setEnabled(false);
        }
    }// GEN-LAST:event_txtPatternKeyReleased

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
    }// GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        oldValue = newValue;
        setVisible(false);
    }// GEN-LAST:event_btnOKActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTestActionPerformed
        Matcher patternMatcher = newValue.matcher(txtTestRegex.getText());
        int counter = 0;
        while (patternMatcher.find()) {
            counter++;
        }
        JOptionPane.showMessageDialog(this, counter + " matches found.",
                "Test Results", JOptionPane.INFORMATION_MESSAGE);
    }// GEN-LAST:event_btnTestActionPerformed

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PatternConfigurationDialog dialog = new PatternConfigurationDialog(
                        new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnTest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JTextField txtPattern;
    private javax.swing.JTextArea txtTestRegex;
    private JPopupMenu popupMenu;
    private JMenuItem mntmCopy;
    private JMenuItem mntmPaste;
    private JMenuItem mntmCut;
    private JPopupMenu popupMenu_1;
    private JMenuItem mntmCut_1;
    private JMenuItem mntmCopy_1;
    private JMenuItem mntmPaste_1;
    // End of variables declaration//GEN-END:variables

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
	}
	private void menuViewSequencePasteActionPerformed(ActionEvent evt) {
        this.txtPattern.paste();
	} 
	private void menuViewSequenceCopyActionPerformed(ActionEvent evt) {
        this.txtPattern.copy();
	}
	private void menuViewSequenceCutActionPerformed(ActionEvent evt) {
        this.txtPattern.cut();
    }
	private void menuViewSequencePasteActionPerformed2(ActionEvent evt) {
        this.txtTestRegex.paste();
	} 
	private void menuViewSequenceCopyActionPerformed2(ActionEvent evt) {
        this.txtTestRegex.copy();
	}
	private void menuViewSequenceCutActionPerformed2(ActionEvent evt) {
        this.txtTestRegex.cut();
    }
}
