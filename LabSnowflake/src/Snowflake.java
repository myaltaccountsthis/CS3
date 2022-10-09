import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class SnowFlakePanel extends JPanel
{
	public SnowFlakePanel()
	{
		super.setPreferredSize(new Dimension(400, 400));
		super.setBackground(Color.WHITE);
	}

	public void drawSnowflake(double x1, double y1, int length, Graphics g, int n) {
		if (n == 0)
			return;
		for (int i = 0; i < 6; i++) {
			double angle = 2 * Math.PI / 6 * i;
			double x2 = x1 + Math.cos(angle) * length;
			double y2 = y1 + Math.sin(angle) * length;
			g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
			drawSnowflake(x2, y2, length / 3, g, n - 1);
		}
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
		//drawSnowflake(width / 2.0, height / 2.0, length, g, 3);
		Color[] randomColors = new Color[] {Color.RED, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.BLUE};
		int length = Math.min(width, height) / 4;
		for (int i = 0; i < 20; i++) {
			int l = (int) (Math.random() * length / 3);
			g.setColor(randomColors[(int) (Math.random() * randomColors.length)]);
			drawSnowflake(Math.random() * width, Math.random() * height, l, g, (int) (Math.random() * 3 + 1));
		}
	}
}

public class Snowflake
{
	public static void main ( String[] args )
	{
		/*
		 * A frame is a container for a panel
		 * The panel is where the drawing will take place
		 */
		JFrame frame = new JFrame("Snowflake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SnowFlakePanel());
		frame.pack();
		frame.setVisible(true);
	}
}
