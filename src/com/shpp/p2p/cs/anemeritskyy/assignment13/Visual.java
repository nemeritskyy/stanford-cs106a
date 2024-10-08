package com.shpp.p2p.cs.anemeritskyy.assignment13;

import acm.graphics.GImage;

import javax.swing.*;
import java.awt.*;

public class Visual extends JFrame {
    private final String FILENAME = "test.jpg";
    private CanvasVisual canvas = new CanvasVisual();

    public Visual() {
        setTitle("Handling viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(canvas);
    }

    public void run() {
        Assignment13Part1.main(new String[]{FILENAME});
        GImage imageAfterHandling = Assignment13Part1.getImageAfterHandling();
        canvas.setImage(imageAfterHandling);
        pack();
        setVisible(true);
    }

    private static class CanvasVisual extends JPanel {
        private GImage image;

        public CanvasVisual() {
            this.setVisible(true);
        }

        public void setImage(GImage newImage) {
            this.image = newImage;
            if (image != null) {
                this.setPreferredSize(new Dimension((int) image.getWidth(), (int) image.getHeight()));
            }
            this.revalidate();
            this.repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image.getImage(), 0, 0, this);
            }
        }
    }

    public static void main(String[] args) {
        Visual frame = new Visual();
        frame.setVisible(true);
        frame.run();
    }
}
