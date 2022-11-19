import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class FunPanel extends JPanel
{
    public FunPanel()
    {
        super.setPreferredSize(new Dimension(1024, 1024));
        super.setBackground(Color.WHITE);
    }

//    public void draw(Graphics g, double minAngle, double maxAngle, int n) {
//        if (n <= 0)
//            return;
//        double angle = (maxAngle + minAngle) / 2;
//        g.setColor(Color.getHSBColor((float) ((angle * 1.8) * 2), 1, 1));
//        if (angle > Math.PI / 4) {
//            g.drawLine(0, 0, (int) (Math.cos(angle) * Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight())), getHeight());
//        }
//        else {
//            g.drawLine(0, 0, getWidth(), (int) (Math.sin(angle) * Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight())));
//        }
//        draw(g, minAngle, angle, n - 1);
//        draw(g, angle, maxAngle, n - 1);
//    }

    private void drawLine(Graphics g, double left, double top, double right, double bottom) {
        g.setColor(Color.getHSBColor((float) (Math.abs((left + right - getWidth()) / 2) + Math.abs((top + bottom - getHeight()) / 2)) / Math.max(getWidth(), getHeight()), 1, 1));
        g.drawLine((int) left, (int) top, (int) right, (int) bottom);
    }

    public void draw(Graphics g, double left, double top, double right, double bottom, int n) {
        if (n == 0)
            return;

        g.drawLine((int) left, (int) top, (int) left, (int) bottom);
        g.drawLine((int) left, (int) top, (int) right, (int) top);
        g.drawLine((int) right, (int) top, (int) right, (int) bottom);
        g.drawLine((int) left, (int) bottom, (int) right, (int) bottom);
        double quarterX = (right - left) / 4;
        double quarterY = (bottom - top) / 4;
        drawLine(g, left + quarterX, top + quarterY, left + quarterX, bottom - quarterY);
        drawLine(g, left + quarterX, top + quarterY, right - quarterX, top + quarterY);
        drawLine(g, right - quarterX, top + quarterY, right - quarterX, bottom - quarterY);
        drawLine(g, left + quarterX, bottom - quarterY, right - quarterX, bottom - quarterY);
        // corners
        draw(g, left, top, left + quarterX, top + quarterY, n - 1);
        draw(g, left, bottom - quarterY, left + quarterX, bottom, n - 1);
        draw(g, right - quarterX, bottom - quarterY, right, bottom, n - 1);
        draw(g, right - quarterX, top, right, top + quarterY, n - 1);
        // edges
        draw(g, left, top + quarterY, left + quarterX, bottom - quarterY, n - 1);
        draw(g, left + quarterX, bottom - quarterY, right - quarterX, bottom, n - 1);
        draw(g, right - quarterX, top + quarterY, right, bottom - quarterY, n - 1);
        draw(g, left + quarterX, top, right - quarterX, top + quarterY, n - 1);
        // center
        draw(g, left + quarterX, top + quarterY, right - quarterX, bottom - quarterY, n - 1);
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

//        draw(g, 0, Math.PI / 2, 12);
        draw(g, 0, 0, width, height,5);
    }
}

public class Fun
{
    public static void main ( String[] args )
    {
        /*
         * A frame is a container for a panel
         * The panel is where the drawing will take place
         */
        JFrame frame = new JFrame("Fun");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FunPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
