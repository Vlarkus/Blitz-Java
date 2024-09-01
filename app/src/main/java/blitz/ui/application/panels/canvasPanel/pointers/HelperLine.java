package blitz.ui.application.panels.canvasPanel.pointers;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import blitz.configs.Config;

public class HelperLine extends JComponent {

    private int x1, y1, x2, y2;

    public HelperLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        setBounds();

    }

    private void setBounds() {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        // Add some padding to ensure the line is fully visible
        int padding = 10;
        setBounds(minX - padding, minY - padding, width + 2 * padding, height + 2 * padding);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Config.HELPER_LINE_THICKNESS));
        g2.setColor(Config.HELPER_LINE_COLOR);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.drawLine(x1 - getX(), y1 - getY(), x2 - getX(), y2 - getY());
    }
}
