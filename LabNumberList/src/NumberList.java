import java.util.Arrays;

public class NumberList {
    private Integer[] list;
    private int size;

    // TODO remove this after testing
    public static void main(String[] args) {
        NumberList list = new NumberList();
        list.add(2);
        list.add(1);
        list.add(3);
        System.out.println(list);
    }

    public NumberList() {
        this.list = new Integer[2];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(list[i]);
        }
        builder.append("]");
        return builder.toString();
    }

    private void doubleCapacity() {
        this.list = Arrays.copyOf(list, list.length * 2);
    }

    public void add(int index, Integer val) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        if (this.list.length == this.size) {
            doubleCapacity();
        }
        for (int i = this.size; i > index; i--) {
            this.list[i] = this.list[i - 1];
        }
        this.list[index] = val;
        this.size++;
    }

    public boolean add(Integer val) {
        this.add(this.size, val);
        return true;
    }

    public Integer get(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        return this.list[index];
    }

    public Integer set(int index, Integer val) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        Integer old = this.list[index];
        this.list[index] = val;
        return old;
    }

    public Integer remove(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        Integer old = this.list[index];
        for (int i = index; i < size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }
        this.size--;
        return old;
    }
}
