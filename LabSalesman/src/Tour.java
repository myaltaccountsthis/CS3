public class Tour 
{
	private Node head;
	private int size;

	/** create an empty tour */
	public Tour()
	{
		this.head = null;
		this.size = 0;
	}
	
	/** create a four-point tour, for debugging */
	public Tour(Point a, Point b, Point c, Point d)
	{
		Node[] nodes = new Node[] {new Node(a), new Node(b), new Node(c), new Node(d)};
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].next = nodes[(i + 1) % nodes.length];
		}
		this.head = nodes[0];
		this.size = 4;
	}
	
	/** print tour (one point per line) to std output */
	public void show()
	{
		Node current = head;
		for (int i = 0; i < size; i++) {
			System.out.println(current.p);
			current = current.next;
		}
	}
	
	/** draw the tour using StdDraw */
	public void draw()
	{
		Node current = head;
		for (int i = 0; i < size; i++) {
			current.p.drawTo(current.next.p);
			current = current.next;
		}
	}
	
	/** return number of nodes in the tour */
	public int size()
	{
		return size;
	}
	
	/** return the total distance "traveled", from start to all nodes and back to start */
	public double distance()
	{
		double totalDistance = 0.0;
		Node current = head;
		for (int i = 0; i < size; i++) {
			totalDistance += current.p.distanceTo(current.next.p);
			current = current.next;
		}
		
		return totalDistance;
	}
	
	/** insert p using nearest neighbor heuristic */
    public void insertNearest(Point p) 
    {
		Node node = new Node(p);
		if (head == null) {
			head = node;
			node.next = node;
		}
		else {
			Node closest = head;
			double closestDistance = Double.MAX_VALUE;
			Node current = head;
			for (int i = 0; i < size; i++) {
				double distance = current.p.distanceTo(p);
				if (distance < closestDistance) {
					closest = current;
					closestDistance = distance;
				}
				current = current.next;
			}
			node.next = closest.next;
			closest.next = node;
		}
		size++;
    }

	/** insert p using smallest increase heuristic */
    public void insertSmallest(Point p) 
    {
		Node node = new Node(p);
		if (head == null) {
			head = node;
			node.next = node;
		}
		else {
			Node closest = head;
			double smallestDistance = Double.MAX_VALUE;
			Node current = head;
			for (int i = 0; i < size; i++) {
				double distance = current.p.distanceTo(p) + p.distanceTo(current.next.p) - current.p.distanceTo(current.next.p);
				if (distance < smallestDistance) {
					closest = current;
					smallestDistance = distance;
				}
				current = current.next;
			}
			node.next = closest.next;
			closest.next = node;
		}
		
		size++;
    }

	private static class Node {
		final Point p;
		Node next;

		public Node(Point p) {
			this(p, null);
		}

		public Node(Point p, Node next) {
			this.p = p;
			this.next = next;
		}
	}

	public static void main(String[] args) {

	}
}