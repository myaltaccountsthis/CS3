import java.util.LinkedList;
import java.util.List;

public class Disk implements Comparable<Disk> {
    private static final int MAX_SIZE = 1_000_000;
    private static int nextId = 0;

    private final List<Integer> files;
    private int size;
    private final int id;

    public Disk() {
        files = new LinkedList<>();
        id = nextId;
        nextId++;
        size = 0;
    }

    public void add(int file) {
        if (size + file > MAX_SIZE)
            return;
        files.add(file);
        size += file;
    }

    public int getSize() {
        return size;
    }

    public int getRemainingSize() {
        return MAX_SIZE - size;
    }

    @Override
    public int compareTo(Disk o) {
        return -(getRemainingSize() - o.getRemainingSize());
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s", id, getRemainingSize(), String.join(" ", files.stream().map(Object::toString).toList()));
    }
}
