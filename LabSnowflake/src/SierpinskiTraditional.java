import javax.swing.*;
import java.awt.*;

class SierpinskiTraditionalPanel extends JPanel {
    public SierpinskiTraditionalPanel()
    {
        super.setPreferredSize(new Dimension(400, 400));
        super.setBackground(Color.WHITE);
    }

    // start is top of triangle
    public void drawSierpinski(double startX, double startY, double height, Graphics g, int n, int w) {
        if (n <= 0)
            return;

        double side = 2 * height / Math.sqrt(3);
        g.setColor(Color.getHSBColor((float) ((-startX + w / 2 + startY * 1.8) / w * 1.2), 1, 1));
        g.drawLine((int) startX, (int) startY, (int) (startX - side / 2), (int) (startY + height));
        g.drawLine((int) startX, (int) startY, (int) (startX + side / 2), (int) (startY + height));
        g.drawLine((int) (startX - side / 2), (int) (startY + height), (int) (startX + side / 2), (int) (startY + height));
        drawSierpinski(startX, startY, height / 2, g, n - 1, w);
        drawSierpinski(startX - side / 4, startY + height / 2, height / 2, g, n - 1, w);
        drawSierpinski(startX + side / 4, startY + height / 2, height / 2, g, n - 1, w);
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
        drawSierpinski(width / 2.0, 0, Math.min(width * Math.sqrt(3) / 2, height), g, (int) (Math.log(Math.min(width, height) / 10.0) / Math.log(1.69)), width);
    }
}

public class SierpinskiTraditional {
    public static void main ( String[] args )
    {
        /*
         * A frame is a container for a panel
         * The panel is where the drawing will take place
         */
        JFrame frame = new JFrame("Snowflake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SierpinskiTraditionalPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
