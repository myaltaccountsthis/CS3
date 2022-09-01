import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack implements StackADT {
    private Square[] stack;
    private int size;

    public MyStack() {
        this(2);
    }

    public MyStack(int initCap) {
        this.stack = new Square[initCap];
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Square peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack[size - 1];
    }

    @Override
    public Square pop() {
        if (isEmpty())
            throw new EmptyStackException();
        Square item = stack[size - 1];
        size--;
        return item;
    }

    @Override
    public void push(Square item) {
        if (size == stack.length)
            doubleCapacity();
        stack[size] = item;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        Arrays.fill(stack, null);
    }

    private void doubleCapacity() {
        this.stack = Arrays.copyOf(stack, stack.length * 2);
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
}
