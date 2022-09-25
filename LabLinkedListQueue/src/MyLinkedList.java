import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public MyLinkedList(T val) {
        this.head = new ListNode(val);
        this.tail = head;
        this.size = 1;
    }

    public MyLinkedList(T... vals) {
        this();
        for (T val : vals)
            add(val);
    }

    public void add(T newVal) {
        if (head == null) {
            head = new ListNode(newVal);
            tail = head;
        }
        else {
            tail.next = new ListNode(newVal);
            tail = tail.next;
        }
        size++;
    }

    public boolean contains(T target) {
        for (ListNode current = head; current != null; current = current.next) {
            if (current.val == target)
                return true;
        }
        return false;
    }

    public T get(int index) {
        if (index >= 0)
            for (ListNode current = head; current != null; current = current.next, index--) {
                if (index == 0)
                    return (T) current.val;
            }

        throw new IndexOutOfBoundsException();
    }

    public int indexOf(T target) {
        int index = 0;
        for (ListNode current = head; current != null; current = current.next, index++) {
            if (current.val == target)
                return index;
        }
        return -1;
    }

    public void set(T newVal, int index) {
        if (index >= 0) {
            for (ListNode current = head; current != null; current = current.next, index--) {
                if (index == 0) {
                    current.val = newVal;
                    return;
                }
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public T remove(int index) {
        if (index >= 0) {
            // i will be 1 ahead in order to override current.next
            int i = 1;
            for (ListNode current = head; current != null; current = current.next, i++) {
                // current cannot be null here, if it is then the loop will not run and this will throw an error
                if (index == 0) {
                    head = current.next;
                    size--;
                    return (T) current.val;
                }
                if (i == index) {
                    Object temp = current.next.val;
                    current.next = current.next.next;
                    if (current.next == null)
                        tail = current;
                    size--;
                    return (T) temp;
                }
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public void add(T newVal, int index) {
        if (index >= 0) {
            // i will be 1 ahead in order to override current.next
            int i = 1;
            ListNode current;
            for (current = head; current != null; current = current.next, i++) {
                // current cannot be null here, if it is then the loop will not run and this will throw an error
                if (index == 0) {
                    head = new ListNode(newVal);
                    head.next = current;
                    size++;
                    return;
                }
                if (i == index) {
                    ListNode node = new ListNode(newVal);
                    node.next = current.next;
                    if (current == tail)
                        tail = node;
                    current.next = node;
                    size++;
                    return;
                }
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        /*
        for (T currentVal : this) {
            sb.append(currentVal);
            sb.append(", ");
        }
        
        if (sb.length() > 1)
            sb.delete(sb.length() - 2, sb.length());
        */

        for (ListNode current = head; current != null; current = current.next) {
            if (current != head)
                sb.append(", ");
            sb.append(current.val);
        }

        return sb.append("]").toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new NodeIterator<>();
    }

    private class NodeIterator<E> implements Iterator<T> {
        private ListNode current = MyLinkedList.this.head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            Object temp = current.val;
            current = current.next;
            return (T) temp;
        }
    }

    private class ListNode {
        T val;
        ListNode next;

        public ListNode(T val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "" + this.val;
        }
    }
}