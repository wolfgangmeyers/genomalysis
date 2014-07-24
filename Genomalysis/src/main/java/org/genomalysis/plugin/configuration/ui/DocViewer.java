package org.genomalysis.plugin.configuration.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.genomalysis.plugin.configuration.dialogs.DialogHelper;

public class DocViewer extends JDialog
{
  private static DocViewer instance;
  private JButton btnOK;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JScrollPane jScrollPane1;
  private JTextArea jTextArea1;

  public static void showDocumentation(JComponent base, String doc)
  {
    showDocumentation(base, doc, "Documentation");
  }

  public static void showDocumentation(JComponent base, String doc, String title) {
    if (instance == null) {
      Frame frame = DialogHelper.getRootFrame(base);
      instance = new DocViewer(frame);
    }
    instance.jTextArea1.setText(doc);
    instance.setTitle(title);
    instance.setVisible(true);
  }

  private DocViewer(Frame parent)
  {
    super(parent, true);
    initComponents();
    setSize(500, 500);
  }

  private void initComponents()
  {
    this.jPanel1 = new JPanel();
    this.jPanel2 = new JPanel();
    this.btnOK = new JButton();
    this.jScrollPane1 = new JScrollPane();
    this.jTextArea1 = new JTextArea();

    setDefaultCloseOperation(0);

    this.jPanel1.setLayout(new BorderLayout());

    this.btnOK.setText("OK");
    this.btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnOKActionPerformed(evt);
      }

    });
    this.jPanel2.add(this.btnOK);

    this.jPanel1.add(this.jPanel2, "South");

    this.jTextArea1.setColumns(20);
    this.jTextArea1.setEditable(false);
    this.jTextArea1.setLineWrap(true);
    this.jTextArea1.setRows(5);
    this.jTextArea1.setWrapStyleWord(true);
    this.jScrollPane1.setViewportView(this.jTextArea1);

    this.jPanel1.add(this.jScrollPane1, "Center");

    getContentPane().add(this.jPanel1, "Center");

    pack();
  }

  private void btnOKActionPerformed(ActionEvent evt) {
    setVisible(false);
  }
}