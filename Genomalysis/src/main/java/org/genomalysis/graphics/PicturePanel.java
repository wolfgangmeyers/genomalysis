package org.genomalysis.graphics;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image image;

    public PicturePanel() {
        initComponents();
    }

    protected void paintComponent(Graphics g) {
        if (getImage() == null) {
            super.paintComponent(g);
        } else {
            g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
            int h) {
        if ((w != getWidth()) || (h != getHeight())) {
            setSize(w, h);
        }

        return super.imageUpdate(img, infoflags, x, y, w, h);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}