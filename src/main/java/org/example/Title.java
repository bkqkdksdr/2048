package org.example;

import javax.swing.*;
import java.awt.*;

public class Title extends JLabel {
    private static final Color[] COLORS = {
            new Color(205, 193, 180), // 0
            new Color(238, 228, 218), // 2
            new Color(237, 224, 200), // 4
            new Color(242, 177, 121), // 8
            new Color(245, 149, 99),  // 16
            new Color(246, 124, 95),  // 32
            new Color(246, 94, 59),   // 64
            new Color(237, 207, 114), // 128
            new Color(237, 204, 97),  // 256
            new Color(237, 200, 80),  // 512
            new Color(237, 197, 63),  // 1024
            new Color(237, 194, 46),  // 2048
            new Color(237, 190, 30),  // 4096
            new Color(237, 187, 20),  // 8192
            new Color(237, 183, 10)   // 16384
    };

    private static final Color DARK_TEXT = new Color(119, 110, 101);
    private static final Color LIGHT_TEXT = new Color(249, 246, 242);

    private int value;

    public Title() {
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setValue(0);
    }

    public void setValue(int value) {
        this.value = value;
        setText(value == 0 ? "" : String.valueOf(value));
        updateAppearance();
    }

    public  int getValue() {
        return value;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
    }

    private void updateAppearance() {
        int index = value == 0 ? 0 : (int) (Math.log(value) / Math.log(2));
        if (index >= COLORS.length) index = COLORS.length - 1;

        setBackground(COLORS[index]);
        setForeground(value < 8 ? DARK_TEXT : LIGHT_TEXT);

        Font font;
        if (value < 10) font = new Font("Arial", Font.BOLD, 32);
        else if (value < 100) font = new Font("Arial", Font.BOLD, 28);
        else if (value < 1000) font = new Font("Arial", Font.BOLD, 24);
        else if (value < 10000) font = new Font("Arial", Font.BOLD, 20);
        else font = new Font("Arial", Font.BOLD, 16);

        setFont(font);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int arcWidth = 10;
        int arcHeight = 10;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        
        g2d.dispose();
        super.paintComponent(g);
    }
}
