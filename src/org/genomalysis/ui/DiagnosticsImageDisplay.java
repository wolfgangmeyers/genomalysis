package org.genomalysis.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.genomalysis.graphics.PicturePanel;

public class DiagnosticsImageDisplay extends JPanel
{
  private JButton btnClose;
  private JPanel jPanel1;
  private JScrollPane jScrollPane1;
  private PicturePanel picturePanel1;

  public DiagnosticsImageDisplay(Image image)
  {
    initComponents();
    this.picturePanel1.setImage(image);
    this.picturePanel1.setSize(image.getWidth(this.picturePanel1), image.getHeight(this.picturePanel1));
  }

  public void paintComponents(Graphics g)
  {
    super.paintComponents(g);
  }

  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.picturePanel1 = new PicturePanel();
    this.jPanel1 = new JPanel();
    this.btnClose = new JButton();

    setLayout(new BorderLayout());

    this.jScrollPane1.setViewportView(this.picturePanel1);

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