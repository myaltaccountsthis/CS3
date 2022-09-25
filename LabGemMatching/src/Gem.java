import java.awt.*;

enum GemType {
    GREEN, BLUE, ORANGE; //define the different types of Gems, comma delimited
}

public class Gem
{
	final int[] pointValues = new int[] {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
	private GemType type;
	private int points;

	public Gem() {
		this.type = GemType.values()[(int) (Math.random() * GemType.values().length)];
		this.points = pointValues[(int) (Math.random() * pointValues.length)];
	}

	public Gem(GemType type, int points) {
		this.type = type;
		this.points = points;
	}

	@Override
	public String toString() {
		return type.name();
	}

	public GemType getType() {
		return type;
	}

	public int getPoints() {
		return points;
	}

	public void draw(double x, double y) {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));
		String fileName = "gem_" + type.name().toLowerCase() + ".png";
		StdDraw.picture(x, y, fileName);
		StdDraw.text(x, y, points + "");
	}

	/** Tester main method */
	public static void main(String [] args)
	{
		final int maxGems = 16;

		// Create a gem of each type
		Gem green  = new Gem(GemType.GREEN, 10);
		Gem blue   = new Gem(GemType.BLUE, 20);
		Gem orange = new Gem(GemType.ORANGE, 30);
		System.out.println(green  + ", " + green.getType()  + ", " + green.getPoints());
		System.out.println(blue   + ", " + blue.getType()   + ", " + blue.getPoints());
		System.out.println(orange + ", " + orange.getType() + ", " + orange.getPoints());
		green.draw(0.3, 0.7);
		blue.draw(0.5, 0.7);
		orange.draw(0.7, 0.7);

		// A row of random gems
		for (int i = 0; i < maxGems; i++)
		{
			Gem g = new Gem();
			g.draw(1.0 / maxGems * (i + 0.5), 0.5);
		}
	}
}
