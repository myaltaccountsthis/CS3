import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public MyLinkedList(T val) {
        this.head = new ListNode<>(val);
        this.tail = head;
        this.size = 1;
    }

    public void add(T newVal) {
        if (head == null) {
            head = new ListNode<>(newVal);
            tail = head;
        }
        else {
            tail.next = new ListNode<>(newVal);
            tail = tail.next;
        }
        size++;
    }

    public boolean contains(T target) {
        for (ListNode<T> current = head; current != null; current = current.next) {
            if (current.val == target)
                return true;
        }
        return false;
    }

    public T get(int index) {
        if (index >= 0)
            for (ListNode<T> current = head; current != null; current = current.next, index--) {
                if (index == 0)
                    return current.val;
            }

        throw new IndexOutOfBoundsException();
    }

    public int indexOf(T target) {
        int index = 0;
        for (ListNode<T> current = head; current != null; current = current.next, index++) {
            if (current.val == target)
                return index;
        }
        return -1;
    }

    public void set(T newVal, int index) {
        if (index >= 0) {
            for (ListNode<T> current = head; current != null; current = current.next, index--) {
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
            for (ListNode<T> current = head; current != null; current = current.next, i++) {
                // current cannot be null here, if it is then the loop will not run and this will throw an error
                if (index == 0) {
                    head = current.next;
                    size--;
                    return current.val;
                }
                if (i == index) {
                    T temp = current.next.val;
                    current.next = current.next.next;
                    if (current.next == null)
                        tail = current;
                    size--;
                    return temp;
                }
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public void add(T newVal, int index) {
        if (index >= 0) {
            // i will be 1 ahead in order to override current.next
            int i = 1;
            ListNode<T> current;
            for (current = head; current != null; current = current.next, i++) {
                // current cannot be null here, if it is then the loop will not run and this will throw an error
                if (index == 0) {
                    head = new ListNode<>(newVal);
                    head.next = current;
                    size++;
                    return;
                }
                if (i == index) {
                    ListNode<T> node = new ListNode<>(newVal);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (ListNode<T> current = head; current != null; current = current.next) {
            if (current != head)
                sb.append(", ");
            sb.append(current.val);
        }

        return sb.append("]").toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new NodeIterator<T>();
    }

    private class NodeIterator<T> implements Iterator<T> {
        private ListNode<Object> current = MyLinkedList.this.head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T temp = current.val;
            current = current.next;
            return (T) temp;
        }
    }

    private class ListNode<T> {
        T val;
        ListNode<T> next;

        public ListNode(T val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "" + this.val;
        }
    }
}
