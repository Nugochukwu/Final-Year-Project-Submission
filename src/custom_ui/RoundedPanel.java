package custom_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class RoundedPanel extends JPanel implements Serializable {

    private int cornerRadius;

    // No-arg constructor required by NetBeans GUI builder
    public RoundedPanel() {
        this(20);
    }

    public RoundedPanel(int radius) {
        this.cornerRadius = Math.max(0, radius);
        setOpaque(false);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = Math.max(0, radius);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    // Clip to rounded shape first
    g2.setClip(new java.awt.geom.RoundRectangle2D.Float(
            0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

    // Paint solid background within the clip
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    g2.dispose();

    super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
                         cornerRadius, cornerRadius);
        g2.dispose();
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    @Override
    public Insets getInsets() {
        int pad = cornerRadius / 2;
        return new Insets(pad, pad, pad, pad);
    }
}