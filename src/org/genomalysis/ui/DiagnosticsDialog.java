package org.genomalysis.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.genomalysis.proteintools.ProteinDiagnosticImageElement;
import org.genomalysis.proteintools.ProteinDiagnosticResult;
import org.genomalysis.proteintools.ProteinDiagnosticTextElement;

public class DiagnosticsDialog extends JDialog
{
  private JButton btnOK;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JTabbedPane resultsPanel;

  public DiagnosticsDialog(Frame parent)
  {
    super(parent, true);
    initComponents();
  }

  public void showDialog(ProteinDiagnosticResult resultToDisplay) {
    this.resultsPanel.removeAll();
    for (Iterator i$ = resultToDisplay.getTextResults().iterator(); i$.hasNext(); ) { ProteinDiagnosticTextElement text = (ProteinDiagnosticTextElement)i$.next();
      DiagnosticsTextDisplay display = new DiagnosticsTextDisplay(text.getText());
      this.resultsPanel.addTab(text.getName(), display);
    }
    for(ProteinDiagnosticImageElement img : resultToDisplay.getGraphicResults()){
        DiagnosticsImageDisplay display = new DiagnosticsImageDisplay(img.getImage());
        this.resultsPanel.addTab(img.getName(), display);
    }
    pack();
    setVisible(true);
  }

  private void initComponents()
  {
    this.jPanel1 = new JPanel();
    this.btnOK = new JButton();
    this.jPanel2 = new JPanel();
    this.resultsPanel = new JTabbedPane();

    setDefaultCloseOperation(2);

    this.btnOK.setText("Close");
    this.btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnOKActionPerformed(evt);
      }

    });
    this.jPanel1.add(this.btnOK);

    getContentPane().add(this.jPanel1, "South");

    this.jPanel2.setLayout(new BorderLayout());
    this.jPanel2.add(this.resultsPanel, "Center");

    getContentPane().add(this.jPanel2, "Center");

    pack();
  }

  private void btnOKActionPerformed(ActionEvent evt) {
    setVisible(false);
  }
}