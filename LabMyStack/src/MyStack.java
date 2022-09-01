import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack {
    private Integer[] stack;
    private int size;
    private Integer[] min;

    public MyStack() {
        this(2);
    }

    public MyStack(int initCap) {
        this.stack = new Integer[initCap];
        this.size = 0;
        this.min = new Integer[initCap];
        Arrays.fill(min, Integer.MAX_VALUE);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Integer peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack[size - 1];
    }

    public Integer pop() {
        if (isEmpty())
            throw new EmptyStackException();
        Integer item = stack[size - 1];
        size--;
        return item;
    }

    public void push(Integer item) {
        if (size == stack.length)
            doubleCapacity();
        stack[size] = item;
        min[size] = (size > 0 ? Math.min(min[size - 1], item) : item);
        size++;
    }

    private void doubleCapacity() {
        this.stack = Arrays.copyOf(stack, stack.length * 2);
        this.min = Arrays.copyOf(stack, stack.length * 2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = size - 1; i >= 0; i--) {
            sb.append(stack[i]);
            if (i == size - 1)
                sb.append("\t\t<----- TOP");
            sb.append('\n');
        }
        return sb.append("-------").toString();
    }

    public Integer getMin() {
        if (isEmpty())
            throw new EmptyStackException();
        return min[size - 1];
    }
}
