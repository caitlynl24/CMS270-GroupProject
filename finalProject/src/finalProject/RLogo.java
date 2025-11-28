package finalProject;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RLogo extends JPanel {
    private final Color BLUE = new Color(0, 102, 204);
    private final Color GOLD = new Color(255, 204, 0);
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        //Background outline (blue)
        g2.setColor(BLUE);
        g2.fillRoundRect(10, 10, w - 20, h - 20, 20, 20);

        //Inner R shape (gold)
        g2.setColor(GOLD);
        g2.setFont(new Font("SansSerif", Font.BOLD, (int)(h * 0.7)));

        FontMetrics fm = g2.getFontMetrics();
        String r = "R";
        int textWidth = fm.stringWidth(r);
        int textHeight = fm.getAscent();

        int x = (w - textWidth) / 2;
        int y = (h + textHeight) / 2 - 10;

        g2.drawString(r, x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
}