package org.genomalysis.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DiagnosticsTextDisplay extends JPanel
{
  private JButton btnClose;
  private JPanel jPanel1;
  private JScrollPane jScrollPane1;
  private JTextArea jTextArea1;

  public DiagnosticsTextDisplay(String text)
  {
    initComponents();
    this.jTextArea1.setText(text);
  }

  public DiagnosticsTextDisplay() {
    this("Results of Diagnostics will appear in this window.");
  }

  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.jTextArea1 = new JTextArea();
    this.jPanel1 = new JPanel();
    this.btnClose = new JButton();

    setLayout(new BorderLayout());

    this.jTextArea1.setColumns(20);
    this.jTextArea1.setFont(new Font("Courier 10 Pitch", 0, 12));
    this.jTextArea1.setLineWrap(true);
    this.jTextArea1.setRows(5);
    this.jTextArea1.setText("aaaaaaaaaaaaaaaaaaaaaaa\nFFFFFFFFFFFFFFFFFFF\n...........................\n::::::::::::::::::::::::::::::::::::");
    this.jTextArea1.setWrapStyleWord(true);
    this.jScrollPane1.setViewportView(this.jTextArea1);

    add(this.jScrollPane1, "Center");

    this.btnClose.setText("Close");
    this.btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCloseActionPerformed(evt);
      }

    });
    this.jPanel1.add(this.btnClose);

    add(this.jPanel1, "South");
  }

  private void btnCloseActionPerformed(ActionEvent evt) {
    getParent().remove(this);
  }
}