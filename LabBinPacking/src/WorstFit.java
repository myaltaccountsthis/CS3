import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WorstFit {
    private List<Integer> files;

    public WorstFit(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            files = reader.lines().map(Integer::parseInt).toList();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() {
        // ordered in most remaining size
        PriorityQueue<Disk> queue = new PriorityQueue<>();
        for (Integer fileSize : files) {
            Disk disk = queue.peek();
            if (disk == null || disk.getRemainingSize() < fileSize) {
                Disk newDisk = new Disk();
                newDisk.add(fileSize);
                queue.offer(newDisk);
            }
            else
                disk.add(fileSize);
        }
        printResult(queue);
    }

    public void solveDecreasing() {
        // stores files in a queue with larger files first
        PriorityQueue<Integer> fileQueue = new PriorityQueue<>(Comparator.reverseOrder());
        fileQueue.addAll(files);
        // ordered in most remaining size
        PriorityQueue<Disk> queue = new PriorityQueue<>();
        while (!fileQueue.isEmpty()) {
            Integer fileSize = fileQueue.poll();
            Disk disk = queue.poll();
            if (disk == null || disk.getRemainingSize() < fileSize) {
                if (disk != null)
                    queue.offer(disk);
                disk = new Disk();
            }
            disk.add(fileSize);
            queue.offer(disk);
        }
        printResult(queue);
    }

    private void printResult(Collection<Disk> disks) {
        System.out.println("Total size\t= " + (disks.stream().mapToInt(Disk::getSize).sum() / 1e6) + " GB");
        System.out.println("Disks req'd\t= " + disks.size());
        if (disks.size() < 100)
            disks.stream().sorted(Comparator.naturalOrder()).forEach(disk -> System.out.println("\t" + disk));
    }

    public static void main(String[] args) {
        WorstFit solver = new WorstFit("input20.txt");
        solver.solve();
        solver.solveDecreasing();
    }
}
