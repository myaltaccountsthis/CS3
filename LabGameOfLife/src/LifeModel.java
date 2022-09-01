import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

	private int getNumAdjacent(int row, int col) {
		int numAdjacent = 0;
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && !(r == row && c == col) && grid[r][c].isAliveNow())
					numAdjacent++;
			}
		}
		return numAdjacent;
	}

	/** main logic method for updating the state of the grid / simulation */
	private void oneGeneration()
	{
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				LifeCell cell = grid[row][col];
				int numAdjacent = getNumAdjacent(row, col);
				if (cell.isAliveNow()) {
					grid[row][col].setAliveNext(numAdjacent >= 2 && numAdjacent <= 3);
				}
				else {
					grid[row][col].setAliveNext(numAdjacent == 3);
				}
			}
		}
		for (LifeCell[] row : grid)
			for (LifeCell cell : row)
				cell.setAliveNow(cell.isAliveNext());
	}
}

