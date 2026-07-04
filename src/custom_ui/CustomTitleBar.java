/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

/**
 *
 * @author Ugochukwu Nwodo
 */
public class CustomTitleBar extends JPanel implements Serializable 
{
    public enum CloseAction { EXIT, DISPOSE }

    // --- Behavior ---
    private CloseAction closeAction     = CloseAction.DISPOSE;
    private boolean     showClose       = true;
    private boolean     showMinimize    = true;
    private boolean showMaximize = false;
    private javax.swing.JButton maximizeBtn;

    // --- Colors ---
    private Color barColor              = new Color(33, 55, 98);
    private Color titleColor            = Color.WHITE;
    private Color closeNormalColor      = new Color(33, 55, 98);
    private Color closeHoverColor       = new Color(196, 43, 28);
    private Color minimizeNormalColor   = new Color(33, 55, 98);
    private Color minimizeHoverColor    = new Color(80, 80, 80);

    // --- Corner radius (mirrors parent RoundedPanel if set) ---
    private int cornerRadius            = 0;

    // --- Text ---
    private String titleText            = "Application";
    private Font   titleFont            = new Font("Segoe UI", Font.BOLD, 14);

    // --- Internals ---
    private JLabel  titleLabel;
    private JButton closeBtn;
    private JButton minimizeBtn;
    private JPanel  btnPanel;
    private JFrame  parentFrame;
    private Point   dragStart;

    public CustomTitleBar() { init(); }

    public CustomTitleBar(JFrame parent) {
        this.parentFrame = parent;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 45));
        setOpaque(false);

        titleLabel = new JLabel("  " + titleText);
        titleLabel.setForeground(titleColor);
        titleLabel.setFont(titleFont);

        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);

        minimizeBtn = makeButton("-");
        closeBtn    = makeButton("X");

        applyButtonStyle(minimizeBtn, minimizeNormalColor, minimizeHoverColor);
        applyButtonStyle(closeBtn,    closeNormalColor,    closeHoverColor);

        rewireCloseButton();
        minimizeBtn.addActionListener(e -> {
            if (parentFrame != null) parentFrame.setState(JFrame.ICONIFIED);
        });

        add(titleLabel, BorderLayout.CENTER);
        refreshButtons();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { dragStart = e.getPoint(); }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (parentFrame != null) {
                    Point cur = e.getLocationOnScreen();
                    parentFrame.setLocation(cur.x - dragStart.x, cur.y - dragStart.y);
                }
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(barColor); // always use barColor directly, never getBackground()

        if (cornerRadius > 0) {
            g2.fillRoundRect(0, 0, getWidth(), getHeight() + cornerRadius,
                             cornerRadius, cornerRadius);
        } else {
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.dispose();
        super.paintComponent(g);
    }
    
    //  Button helpers
    private JButton makeButton(String symbol) {
        JButton btn = new JButton(symbol);
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    private void applyButtonStyle(JButton btn, Color normal, Color hover) {
        btn.setBackground(normal);
        btn.setForeground(titleColor);
        btn.setContentAreaFilled(true);

        for (MouseListener ml : btn.getMouseListeners()) {
            if (ml.getClass().isAnonymousClass()) btn.removeMouseListener(ml);
        }
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(normal); }
        });
    }

    private void rewireCloseButton() {
        for (ActionListener al : closeBtn.getActionListeners())
            closeBtn.removeActionListener(al);
        closeBtn.addActionListener(e -> {
            if (closeAction == CloseAction.EXIT) {
                System.exit(0);
            } else {
                if (parentFrame != null) parentFrame.dispose();
            }
        });
    }

    private void refreshButtons() {
        btnPanel.removeAll();
        if (showMinimize) btnPanel.add(minimizeBtn);
        if (showMaximize && maximizeBtn != null) btnPanel.add(maximizeBtn);
        if (showClose)    btnPanel.add(closeBtn);
        remove(btnPanel);
        add(btnPanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    public void setParentFrame(JFrame frame) { this.parentFrame = frame; }
    public JFrame getParentFrame()           { return parentFrame; }

    public CloseAction getCloseAction()             { return closeAction; }
    public void setCloseAction(CloseAction action)  {
        this.closeAction = action;
        rewireCloseButton();
    }

    public boolean isShowClose()              { return showClose; }
    public void setShowClose(boolean show)    {
        this.showClose = show;
        refreshButtons();
    }

    public boolean isShowMinimize()           { return showMinimize; }
    public void setShowMinimize(boolean show) {
        this.showMinimize = show;
        refreshButtons();
    }

    public int getCornerRadius()              { return cornerRadius; }
    public void setCornerRadius(int radius)   {
        this.cornerRadius = Math.max(0, radius);
        repaint();
    }

    public Color getBarColor()                { return barColor; }
    public void setBarColor(Color color)      {
        this.barColor = color;
        repaint();
    }

    public Color getTitleColor()              { return titleColor; }
    public void setTitleColor(Color color)    {
        this.titleColor = color;
        titleLabel.setForeground(color);
        closeBtn.setForeground(color);
        minimizeBtn.setForeground(color);
        repaint();
    }

    public Color getCloseNormalColor()              { return closeNormalColor; }
    public void setCloseNormalColor(Color color)    {
        this.closeNormalColor = color;
        applyButtonStyle(closeBtn, closeNormalColor, closeHoverColor);
    }

    public Color getCloseHoverColor()               { return closeHoverColor; }
    public void setCloseHoverColor(Color color)     {
        this.closeHoverColor = color;
        applyButtonStyle(closeBtn, closeNormalColor, closeHoverColor);
    }

    public Color getMinimizeNormalColor()           { return minimizeNormalColor; }
    public void setMinimizeNormalColor(Color color) {
        this.minimizeNormalColor = color;
        applyButtonStyle(minimizeBtn, minimizeNormalColor, minimizeHoverColor);
    }

    public Color getMinimizeHoverColor()            { return minimizeHoverColor; }
    public void setMinimizeHoverColor(Color color)  {
        this.minimizeHoverColor = color;
        applyButtonStyle(minimizeBtn, minimizeNormalColor, minimizeHoverColor);
    }

    public String getTitleText()              { return titleText; }
    public void setTitleText(String text)     {
        this.titleText = text;
        titleLabel.setText("  " + text);
    }

    public Font getTitleFont()                { return titleFont; }
    public void setTitleFont(Font font)       {
        this.titleFont = font;
        titleLabel.setFont(font);
    }
    public void setShowMaximize(boolean show) {
        this.showMaximize = show;
        if (show && maximizeBtn == null) {
            maximizeBtn = makeButton("▢");
            maximizeBtn.addActionListener(e -> {
                if (parentFrame != null) {
                    if (parentFrame.getExtendedState() == java.awt.Frame.MAXIMIZED_BOTH) {
                        parentFrame.setExtendedState(java.awt.Frame.NORMAL);
                        maximizeBtn.setText("[]");
                    } else {
                        parentFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
                        maximizeBtn.setText("{}");
                    }
                }
            });
            applyButtonStyle(maximizeBtn, minimizeNormalColor, minimizeHoverColor);
        }
        refreshButtons();
    }
}
