import javax.swing.*;
import java.awt.*;

class SierpinskiPanel extends JPanel {
    public SierpinskiPanel()
    {
        super.setPreferredSize(new Dimension(400, 400));
        super.setBackground(Color.WHITE);
    }

    public void drawSierpinski(int startX, int startY, int length, Graphics g, int n) {
        if (n == 0)
            return;
        g.drawLine(startX, startY, startX, startY + length);
        g.drawLine(startX, startY, startX + length, startY);
        g.drawLine(startX + length, startY, startX, startY + length);
        drawSierpinski(startX, startY, length / 2, g, n - 1);
        drawSierpinski(startX, startY + length / 2, length / 2, g, n - 1);
        drawSierpinski(startX + length / 2, startY, length / 2, g, n - 1);
    }

    public void paintComponent(Graphics g)
    {
        int width  = getWidth();
        int height = getHeight();

        super.paintComponent(g);

        /*
         * DRAWING CODE BELOW
         */
        g.setColor(Color.BLUE);
        //g.drawLine(0, 0, width - 1, height - 1);
        drawSierpinski(0, 0, Math.min(width, height), g, 7);
    }
}

public class Sierpinski {
    public static void main ( String[] args )
    {
        /*
         * A frame is a container for a panel
         * The panel is where the drawing will take place
         */
        JFrame frame = new JFrame("Snowflake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SierpinskiPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
