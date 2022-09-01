import java.awt.*;
import javax.swing.*;

/** The view (graphical) component */
public class LifeView extends JPanel
{
    public static final Color DEFAULT_COLOR = Color.BLUE;

	private static final long serialVersionUID = 1L;
	private static int SIZE = 60;

	/** store a reference to the current state of the grid */
    private LifeCell[][] grid;

    private Color color;

    public LifeView()
    {
        grid = new LifeCell[SIZE][SIZE];
        this.color = DEFAULT_COLOR;
    }

    /** entry point from the model, requests grid be redisplayed */
    public void updateView( LifeCell[][] mg )
    {
        grid = mg;
        repaint();
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return this.color;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE+1);
        int testHeight = getHeight() / (SIZE+1);
        // keep each life cell square
        int boxSize = Math.min(testHeight, testWidth);

        for ( int r = 0; r < SIZE; r++ )
        {
            for (int c = 0; c < SIZE; c++ )
            {
                if (grid[r][c] != null)
                {
                    if ( grid[r][c].isAliveNow() )
                        g.setColor( this.color );
                    else
                        g.setColor( new Color(235,235,255) );

                    g.fillRect( (c+1)*boxSize, (r+1)* boxSize, boxSize-2, boxSize-2);
                }
            }
        }
    }
}
