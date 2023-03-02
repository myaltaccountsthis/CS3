public class MyBST {
    private BSTNode root;

    public MyBST() {
        this.root = null;
    }

    public int size() {
        return sizeHelper(root);
    }
    private int sizeHelper(BSTNode parent) {
        if (parent == null)
            return 0;
        return 1 + sizeHelper(parent.left) + sizeHelper(parent.right);
    }

    public void insert(Integer n) {
        root = insertHelper(n, root);
    }
    private BSTNode insertHelper(Integer n, BSTNode parent) {
        if (parent == null)
            return new BSTNode(n);
        if (n < parent.val) {
            parent.left = insertHelper(n, parent.left);
        }
        else {
            parent.right = insertHelper(n, parent.right);
        }
        return parent;
    }

    public boolean contains(Integer n) {
        return containsHelper(n, root);
    }
    private boolean containsHelper(Integer n, BSTNode parent) {
        if (parent == null)
            return false;
        if (n.equals(parent.val))
            return true;
        if (n < parent.val)
            return containsHelper(n, parent.left);
        return containsHelper(n, parent.right);
    }

    public Integer getMax() {
        return getMaxHelper(root);
    }
    private Integer getMaxHelper(BSTNode parent) {
        if (parent == null)
            return null;
        if (parent.right != null)
            return getMaxHelper(parent.right);
        return parent.val;
    }

    public Integer getMin() {
        return getMinHelper(root);
    }
    private Integer getMinHelper(BSTNode parent) {
        if (parent == null)
            return null;
        if (parent.left != null)
            return getMinHelper(parent.left);
        return parent.val;
    }

    public void delete(Integer n) {
        root = deleteHelper(n, root);
    }
    private BSTNode deleteHelper(Integer n, BSTNode parent) {
        if (parent == null)
            return null;
        if (n.equals(parent.val)) {
            if (parent.left == null && parent.right == null)
                return null;
            if (parent.left == null)
                return parent.right;
            if (parent.right == null)
                return parent.left;
            /* smallest right
            Integer min = getMinHelper(parent.right);
            BSTNode node = new BSTNode(min);
            node.left = parent.left;
            node.right = deleteHelper(min, parent.right);
             */
            // largest left
            Integer max = getMaxHelper(parent.left);
            BSTNode node = new BSTNode(max);
            node.left = deleteHelper(max, parent.left);
            node.right = parent.right;
            return node;
        }
        if (n < parent.val)
            parent.left = deleteHelper(n, parent.left);
        else
            parent.right = deleteHelper(n, parent.right);
        return parent;
    }

    public void inOrder() {
        inOrderHelper(root);
        System.out.println();
    }
    private void inOrderHelper(BSTNode parent) {
        if (parent == null)
            return;
        inOrderHelper(parent.left);
        System.out.print(parent.val + " ");
        inOrderHelper(parent.right);
    }

    public void print() {
        printHelper(root, 0);
    }
    private void printHelper(BSTNode parent, int depth) {
        if (parent == null)
            return;
        printHelper(parent.right, depth + 1);
        System.out.println("\t".repeat(depth) + parent.val);
        printHelper(parent.left, depth + 1);
    }

    private class BSTNode {
        Integer val;
        BSTNode left, right;

        public BSTNode(Integer val) {
            this.val = val;
            left = right = null;
        }

        @Override
        public String toString() {
            return "" + this.val;
        }
    }
}
