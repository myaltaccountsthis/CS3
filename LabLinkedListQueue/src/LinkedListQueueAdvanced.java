import java.util.ArrayList;
import java.util.List;

public class LinkedListQueueAdvanced {
    public static void main(String[] args) {
        // Cycle detection
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < i; j++) {
                MyLinkedList<Integer> list = new MyLinkedList<>(1, 4, 3, 6, 8, 4, 3);
                list.createCycle(i, j);
                System.out.println(list.detectCycle());
            }
        }
        // mergeK
        testMergeK(new int[] {12, 24, 35, 45, 75}, new int[] {14, 23, 27, 34, 53, 64, 75}, new int[] {29, 37, 48, 57});
    }

    // ADVANCED
    public static ListNode mergeK(List<ListNode> heads) {
        if (heads.size() == 0)
            return null;
        ListNode head = null;
        ListNode prev = null;
        while (true) {
            int smallIndex = -1;
            int smallVal = Integer.MAX_VALUE;
            for (int i = 0; i < heads.size(); i++) {
                ListNode current = heads.get(i);
                // make sure this head exists
                if (current != null && current.val < smallVal) {
                    smallIndex = i;
                    smallVal = current.val;
                }
            }
            if (smallIndex == -1)
                break;
            if (head == null) {
                head = new ListNode(heads.get(smallIndex).val);
                prev = head;
            }
            else {
                prev.next = new ListNode(heads.get(smallIndex).val);
                prev = prev.next;
            }
            heads.set(smallIndex, heads.get(smallIndex).next);
        }
        return head;
    }

    public static void testMergeK(int[]... vals) {
        List<ListNode> nodes = new ArrayList<>();
        for (int[] arr : vals) {
            assert arr.length > 0;
            ListNode head = new ListNode(arr[0]);
            ListNode prev = head;
            for (int i = 1; i < arr.length; i++) {
                prev.next = new ListNode(arr[i]);
                prev = prev.next;
            }
            nodes.add(head);
        }
        ListNode start = mergeK(nodes);
        StringBuilder sb = new StringBuilder("[");
        for (ListNode node = start; node != null; node = node.next) {
            if (node != start)
                sb.append(',').append(' ');
            sb.append(node.val);
        }
        System.out.println(sb.append(']'));
    }

    private static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "" + this.val;
        }
    }
}
