/*************************************************************************
 * Name        : Keith Vertanen 
 * Username    : kvertanen
 * Description : Gem Matching game.  Two players alternate choosing 
 *               where to place the next gem.  Blocks of the same color
 *               get a score multiplier equal to the number of gems in 
 *               that block.  The game is over when either players row
 *               reaches 16 gems.  
 *************************************************************************/

import java.awt.Font;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class GemGame 
{
	// The maximum number of gems that will fit across the screen
	static final int MAX_GEMS = 16;
	
	// Given a gem's 0-based index, calculate its x-position
	// in a unit box coordinate system. 
	public static double indexToX(int i)
	{
		return (0.5 + i) * (1.0 / MAX_GEMS);
	}

	// Given a mouse x-position, figure out what gem index
	// we should insert before.
	public static int xToIndex(double x)
	{
		return (int) ((x + 0.5 / MAX_GEMS) / (1.0 / MAX_GEMS));
	}

	public static void main(String [] args)
	{
		// Constants that define how we draw various things on the screen
		final double PLAYER1_Y 			= 0.7;
		final double PLAYER2_Y 			= 0.3;
		final double PLAYER_HALF_HEIGHT = 0.15;
		final double TEXT_HEIGHT 		= 0.05;
		final double SCORE_Y 			= 0.95;
		final double INDICATOR_X 		= -0.025;
		final double INDICATOR_RADIUS 	= 0.01;

		// Create a List ADT for each of the two players
		GemList player1 = new GemList();
		GemList player2 = new GemList();

		Gem current = null;
		boolean mouseDown = false;
		boolean turn1 = true;

		String player1Name = "";
		String player2Name = "";
		boolean player2Bot = true;
		Scanner console = new Scanner(System.in);
		System.out.print("Use Computer for Player 2? (y/n): ");
		player2Bot = console.nextLine().equalsIgnoreCase("y");
		System.out.print("Enter Player 1 Name: ");
		while (player1Name.isEmpty())
			player1Name = console.nextLine();
		if (player2Bot)
			player2Name = "Computer";
		else {
			System.out.print("Enter Player 2 Name: ");
			while (player2Name.isEmpty())
				player2Name = console.nextLine();
		}

		int score1 = 0;
		int score2 = 0;

		// Game continues until we fill up the entire row of gems
		while ((player1.size() < MAX_GEMS) && (player2.size() < MAX_GEMS)) {
			StdDraw.clear();
			double mouseX = StdDraw.mouseX();
			double mouseY = StdDraw.mouseY();

			if (current == null)
				current = new Gem();

			if (!turn1 && player2Bot) {
				int[] scoreDiff1 = new int[player1.size() + 1];
				int[] scoreDiff2 = new int[player2.size() + 1];
				for (int i = 0; i < scoreDiff1.length; i++)
					scoreDiff1[i] = score1 - player1.testScore(i, current);
				for (int i = 0; i < scoreDiff2.length; i++)
					scoreDiff2[i] = player2.testScore(i, current) - score2;
				int highest1 = scoreDiff1[0], highestIndex1 = 0;
				int highest2 = scoreDiff2[0], highestIndex2 = 0;
				for (int i = 1; i < scoreDiff1.length; i++) {
					int n = scoreDiff1[i];
					if (n > highest1) {
						highest1 = n;
						highestIndex1 = i;
					}
				}
				for (int i = 1; i < scoreDiff2.length; i++) {
					int n = scoreDiff2[i];
					if (n > highest2) {
						highest2 = n;
						highestIndex2 = i;
					}
				}
				// add to greatest changed list, or prefer own if equal
				if (highest1 > highest2)
					player1.insertBefore(current, highestIndex1);
				else
					player2.insertBefore(current, highestIndex2);

				current = null;
				turn1 = true;
			// Check for a click of the mouse, wait for release of the button
			} else if (StdDraw.mousePressed())
			{
				mouseDown = true;
			}
			else if (mouseDown)
			{
				// See if they clicked in the Player1 area
				if ((mouseY > PLAYER1_Y - PLAYER_HALF_HEIGHT) &&
				    (mouseY < PLAYER1_Y + PLAYER_HALF_HEIGHT))
				{
					player1.insertBefore(current, xToIndex(mouseX));
					current = null;
					turn1 = !turn1;
				}
				// See if they clicked in the Player2 area
				else if ((mouseY > PLAYER2_Y - PLAYER_HALF_HEIGHT) &&
				         (mouseY < PLAYER2_Y + PLAYER_HALF_HEIGHT))
				{
					player2.insertBefore(current, xToIndex(mouseX));
					current = null;
					turn1 = !turn1;
				}
				// Regardless, this click is over even if we didn't drop the gem
				mouseDown = false;
			}

			score1 = player1.score();
			score2 = player2.score();

			// Background to show each player
			StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
			StdDraw.filledRectangle(0.5, PLAYER2_Y, 1.0, PLAYER_HALF_HEIGHT);
			StdDraw.filledRectangle(0.5, PLAYER1_Y, 1.0, PLAYER_HALF_HEIGHT);

			// Display the player labels plus their current score
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
			StdDraw.textLeft(0.0, PLAYER1_Y + PLAYER_HALF_HEIGHT - TEXT_HEIGHT, player1Name + ": " + score1);
			StdDraw.textLeft(0.0, PLAYER2_Y + PLAYER_HALF_HEIGHT - TEXT_HEIGHT, player2Name + ": " + score2);

			// Draw a little blue dot to indicate whose turn it is
			StdDraw.setPenColor(StdDraw.BLUE);
			if (turn1)
				StdDraw.filledCircle(INDICATOR_X, PLAYER1_Y + PLAYER_HALF_HEIGHT - TEXT_HEIGHT, INDICATOR_RADIUS);
			else
				StdDraw.filledCircle(INDICATOR_X, PLAYER2_Y + PLAYER_HALF_HEIGHT - TEXT_HEIGHT, INDICATOR_RADIUS);

			// Draw the gems and then any gem currently being dragged around
			player1.draw(PLAYER1_Y);
			player2.draw(PLAYER2_Y);

			// See if we currently have a gem waiting to be dropped
			if (current != null)
				current.draw(mouseX, mouseY);

			StdDraw.show(10);
		}

		// Display the final message saying who won the game
		StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
        if (score1 == score2)
        	StdDraw.text(0.5, SCORE_Y, "Tie game!");
        else if (score1 > score2)
        	StdDraw.text(0.5, SCORE_Y, "Player 1 wins!");
        else
        	StdDraw.text(0.5, SCORE_Y, "Player 2 wins!");

		StdDraw.show(0);
	}
}
