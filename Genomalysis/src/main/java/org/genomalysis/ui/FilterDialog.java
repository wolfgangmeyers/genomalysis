/*
 * FilterDialog.java
 *
 * Created on March 22, 2008, 5:28 AM
 */
package org.genomalysis.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.genomalysis.control.FilterControl;
import org.genomalysis.control.FilterProgressControl;
import org.genomalysis.control.IObserver;
import org.genomalysis.control.IProcessCompleteCallback;
import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.InitializationException;

/**
 *
 * @author  ameyers
 */
public class FilterDialog extends javax.swing.JDialog {

    private FilterControl filterControl = new FilterControl();
    private FilterProgressControl filterProgressControl;
    private FilterProgressPanel filterProgressPanel;
    private JFileChooser fileChooser = new JFileChooser();

    public void showDialog(List<PluginInstance<IProteinSequenceFilter>> filterInstances) {
        filterControl.setFilterInstances(filterInstances);
        setVisible(true);
    }

    /** Creates new form FilterDialog */
    public FilterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        filterProgressControl = filterControl.getProgressControl();
        filterProgressPanel = new FilterProgressPanel();
        filterProgressPanel.setFilterProgressControl(filterProgressControl);
        filterControl.addObserver(new IObserver() {

            public void update() {
                _update();
            }

            public void showError(String errorMsg) {
                _showError(errorMsg, "Error");
            }
        });
    }

    private void _update() {
        Runnable runnable = new Runnable() {

            public void run() {
                String pauseText = (filterControl.isPaused() ? "UnPause" : "Pause");
                btnPause.setText(pauseText);
                btnStart.setEnabled(!filterControl.isRunning());
                btnStop.setEnabled(filterControl.isRunning());
                btnBrowseInputFile.setEnabled(!filterControl.isRunning());
                btnBrowseOutputFile.setEnabled(!filterControl.isRunning());
                btnExit.setEnabled(!filterControl.isRunning());
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException ex) {
                Logger.getLogger(FilterDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FilterDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void _showError(String errorMsg, String title) {
        JOptionPane.showMessageDialog(this, errorMsg, title, JOptionPane.ERROR_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnBrowseInputFile = new javax.swing.JButton();
        btnBrowseOutputFile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtInputFile = new javax.swing.JTextField();
        txtOutputFile = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Protein Sequences Filter");

        mainPanel.setLayout(new java.awt.BorderLayout());

        topPanel.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        btnBrowseInputFile.setText("Browse...");
        btnBrowseInputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseInputFileActionPerformed(evt);
            }
        });
        jPanel2.add(btnBrowseInputFile);

        btnBrowseOutputFile.setText("Browse...");
        btnBrowseOutputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseOutputFileActionPerformed(evt);
            }
        });
        jPanel2.add(btnBrowseOutputFile);

        topPanel.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        txtInputFile.setBackground(new java.awt.Color(255, 255, 255));
        txtInputFile.setEditable(false);
        jPanel3.add(txtInputFile);

        txtOutputFile.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtOutputFile);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        btnStart.setText("Start");
        btnStart.setPreferredSize(new java.awt.Dimension(79, 24));
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        jPanel5.add(btnStart);

        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel5.add(btnStop);

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        jPanel5.add(btnPause);

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel5.add(btnExit);

        jPanel3.add(jPanel5);

        topPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        jLabel1.setText("Input File:");
        jPanel4.add(jLabel1);

        jLabel2.setText("Output File:");
        jPanel4.add(jLabel2);

        jLabel3.setText("Options:");
        jPanel4.add(jLabel3);

        topPanel.add(jPanel4, java.awt.BorderLayout.WEST);

        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        try {
            boolean goodToGo = true;
            final FilterDialog dlg = this;
            if (txtInputFile.getText().equals("")) {
                _showError("Specify an input file", "Error");
                goodToGo = false;
            }
            if (txtOutputFile.getText().equals("")) {
                _showError("Specify an output file", "Error");//GEN-LAST:event_btnStartActionPerformed
                goodToGo = false;
            }
            if (goodToGo) {
                filterControl.setInputFile(new File(txtInputFile.getText()));
                filterControl.setOutputFile(new File(txtOutputFile.getText()));
                mainPanel.add(filterProgressPanel, BorderLayout.CENTER);
                pack();
                filterProgressPanel.repaint();
                filterControl.start(new IProcessCompleteCallback() {

                    public void processCompleted() {
                        JOptionPane.showMessageDialog(mainPanel, "Done!");
                        mainPanel.remove(filterProgressPanel);
                        _update();
                        dlg.pack();
                        dlg.setVisible(false);
                    }
                });
            }
        } catch (InitializationException ex) {
            Logger.getLogger(FilterDialog.class.getName()).log(Level.SEVERE, null, ex);
            for(String reason : ex.getReasons()){
                _showError(reason, "Plugin Initialization Error");
            }
        } catch (IOException ex) {
            Logger.getLogger(FilterDialog.class.getName()).log(Level.SEVERE, null, ex);
            _showError(ex.getMessage(), "IO Error");
        }
    }

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        filterControl.stop();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseActionPerformed
        filterControl.togglePause();
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnBrowseInputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseInputFileActionPerformed
        if (fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            txtInputFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnBrowseInputFileActionPerformed

    private void btnBrowseOutputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseOutputFileActionPerformed
        if (fileChooser.showSaveDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            txtOutputFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnBrowseOutputFileActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseInputFile;
    private javax.swing.JButton btnBrowseOutputFile;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JTextField txtInputFile;
    private javax.swing.JTextField txtOutputFile;
    // End of variables declaration//GEN-END:variables
}