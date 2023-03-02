public class Node implements Comparable<Node> {
    public final int frequency;
    public final int character;
    public Node left;
    public Node right;

    public Node(int character, int frequency) {
        this(character, frequency, null, null);
    }

    public Node(int character, int frequency, Node left, Node right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns whether this node is a leaf or not.
     * Letters must be on leafs; there are no letters on inner nodes
     * @return true if this node is a leaf
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(Node o) {
        return frequency - o.frequency;
    }

    @Override
    public String toString() {
        return character + "";
    }
}
