import java.util.Arrays;
import java.util.Random;

public class MinHeap {
    private Integer[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 8;

    public MinHeap() {
        heap = new Integer[DEFAULT_CAPACITY];
        size = 0;
    }

    public MinHeap(int size) {
        int heapSize = DEFAULT_CAPACITY;
        while (size >= heapSize)
            heapSize *= 2;
        heap = new Integer[heapSize];
    }

    public MinHeap(Integer... nums) {
        this(nums.length);
        buildHeap(nums);
    }

    private void buildHeap(Integer[] nums) {
        while (nums.length >= heap.length)
            doubleCapacity();
        System.arraycopy(nums, 0, heap, 1, nums.length);
        size = nums.length;
        // Loop through the range [1, size/2] descending and sift down elements
        for (int i = size / 2; i > 0; i--) {
            siftDown(i);
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peekMinimum() {
        return heap[1];
    }

    public int getLeftChildIndex(int index) {
        return 2 * index;
    }

    public int getRightChildIndex(int index) {
        return 2 * index + 1;
    }

    public int getParentIndex(int index) {
        return index / 2;
    }

    private void doubleCapacity() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    public void insert(int value) {
        if (size == heap.length - 1)
            doubleCapacity();
        int nextIndex = ++size;
        heap[nextIndex] = value;
        bubbleUp(nextIndex);
    }

    private void bubbleUp(int index) {
        if (index == 1)
            return;
        // if this value is less than the parent's, swap the places and continue
        int parentIndex = getParentIndex(index);
        if (heap[index] < heap[parentIndex]) {
            Integer temp = heap[index];
            heap[index] = heap[parentIndex];
            heap[parentIndex] = temp;
            bubbleUp(parentIndex);
        }
    }

    public int popMinimum() {
        int smallest = peekMinimum();
        int lastIndex = size--;
        heap[1] = heap[lastIndex];
        heap[lastIndex] = null;
        siftDown(1);
        return smallest;
    }

    private void siftDown(int index) {
        if (getRightChildIndex(index) > size)
            return;
        // find the index of the smallest child
        int minChildIndex = getLeftChildIndex(index);
        int rightIndex = getRightChildIndex(index);
        if (heap[rightIndex] < heap[minChildIndex])
            minChildIndex = rightIndex;
        // if the current value is greater than the smallest child, swap the places and continue
        if (heap[index] > heap[minChildIndex]) {
            Integer temp = heap[index];
            heap[index] = heap[minChildIndex];
            heap[minChildIndex] = temp;
            siftDown(minChildIndex);
        }
    }

    @Override
    public String toString()
    {
        String output = "";
        for (int i = 1; i <= getSize(); i++)
            output += heap[i] + ", ";
        return output.substring(0, output.lastIndexOf(","));
    //lazily truncate last comma
    }
    /** method borrowed
     with minor modifications
     from the internet somewhere, for printing */
    public void display() {
        int nBlanks = 32,
                itemsPerRow = 1, column = 0, j = 1;
        String dots = "...............................";
        System.out.println(dots + dots);
        while (j <= this.getSize())
        {
            if (column == 0)
                for (int k = 0; k < nBlanks; k++)
                    System.out.print(' ');
            System.out.print((heap[j] == null)? "" : heap[j]);
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            }
            else
                for (int k = 0; k < nBlanks * 2 - 2; k++)
                    System.out.print(' ');
            j++;
        }
        System.out.println("\n" + dots + dots);
    }

    // test buildHeap
    public static void main(String[] args) {
        // buildHeap test
        Integer[] array = generateArray(1000);
        long t = System.nanoTime();
        MinHeap heap = new MinHeap(array.length);
        heap.buildHeap(array);
        long t1 = System.nanoTime() - t;
        t = System.nanoTime();
        MinHeap heap1 = new MinHeap();
        for (Integer x : array)
            heap1.insert(x);
        long t2 = System.nanoTime() - t;
        System.out.println(t1 + " " + t2);
    }

    private static Integer[] generateArray(int size) {
        Random rng = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++)
            array[i] = rng.nextInt(100);
        return array;
    }
}