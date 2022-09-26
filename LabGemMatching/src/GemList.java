public class GemList
{
	private Node head;
	private int size;

	public int size() {
		return size;
	}

	public void draw(double y) {
		int i = 0;
		for (Node current = head; current != null; current = current.next, i++) {
			current.gem.draw(1.0 / 16 * (i + .5), y);
		}
	}

	@Override
	public String toString() {
		if (head == null)
			return "<none>";
		StringBuilder sb = new StringBuilder();
		for (Node current = head; current != null; current = current.next) {
			if (current != head)
				sb.append(" -> ");
			sb.append(current.gem);
		}
		return sb.toString();
	}

	public void insertBefore(Gem gem, int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException();
		index = Math.min(index, size);
		Node node = new Node(gem);
		if (index == 0) {
			if (head != null)
				node.next = head;
			head = node;
		}
		else {
			int i = 1;
			Node current;
			for (current = head; current != null; current = current.next, i++) {
				if (i == index) {
					node.next = current.next;
					current.next = node;
					break;
				}
			}
		}
		size++;
	}

	public int score() {
		GemType prevType = null;
		int score = 0;
		int currentMultiplier = 0;
		int consecutivePoints = 0;
		for (Node current = head; current != null; current = current.next) {
			Gem gem = current.gem;
			GemType currentType = gem.getType();
			if (currentType == prevType) {
				consecutivePoints += gem.getPoints();
				currentMultiplier++;
			}
			else {
				score += consecutivePoints * currentMultiplier;
				consecutivePoints = gem.getPoints();
				currentMultiplier = 1;
			}
			prevType = currentType;
		}
		score += consecutivePoints * currentMultiplier;
		return score;
	}

	public int testScore(int index, Gem toAdd) {
		index = Math.min(index, size);
		GemType prevType = null;
		int i = 0;
		int score = 0;
		int currentMultiplier = 0;
		int consecutivePoints = 0;
		for (Node current = head; current != null || index == i; i++) {
			Gem gem;
			if (i == index)
				gem = toAdd;
			else
				gem = current.gem;
			GemType currentType = gem.getType();
			if (currentType == prevType) {
				consecutivePoints += gem.getPoints();
				currentMultiplier++;
			} else {
				score += consecutivePoints * currentMultiplier;
				consecutivePoints = gem.getPoints();
				currentMultiplier = 1;
			}
			prevType = currentType;
			if (i != index)
				current = current.next;
		}
		score += consecutivePoints * currentMultiplier;
		return score;
	}

	private class Node {
		private Gem gem;
		private Node next;

		public Node(Gem gem) {
			this.gem = gem;
		}
	}

	public static void main(String [] args)
	{
		System.out.println(new GemList().head);
		GemList list = new GemList();
		System.out.println(list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.9);

		list.insertBefore(new Gem(GemType.BLUE, 10), 0);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.8);

		list.insertBefore(new Gem(GemType.BLUE, 20), 99);  //not a mistake, should still work
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.7);

		list.insertBefore(new Gem(GemType.ORANGE, 30), 1);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.6);

		list.insertBefore(new Gem(GemType.ORANGE, 10), 2);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.5);

		list.insertBefore(new Gem(GemType.ORANGE, 50), 3);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.4);

		list.insertBefore(new Gem(GemType.GREEN, 50), 2);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.3);
	}	
}
