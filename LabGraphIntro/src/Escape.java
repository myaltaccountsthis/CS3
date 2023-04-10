import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Escape {
    public static final int ROWS = 501, COLUMNS = 501;
    public static final Location START = new Location(0, 0), END = new Location(ROWS - 1, COLUMNS - 1);

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("escape.dat"));
        int n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println(lowest(input.nextLine().split(","), input.nextLine().split(",")));
        }
    }

    public static int lowest(String[] harmful, String[] deadly) {
        // init matrix
        Effect[][] matrix = new Effect[ROWS][COLUMNS];
        for (Effect[] row : matrix)
            Arrays.fill(row, Effect.NORMAL);
        for (String line : harmful) {
            Scanner sc = new Scanner(line);
            int x1 = sc.nextInt(), y1 = sc.nextInt(), x2 = sc.nextInt(), y2 = sc.nextInt();
            if (x2 < x1) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            if (y2 < y1) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int r = y1; r <= y2; r++) {
                for (int c = x1; c <= x2; c++) {
                    matrix[r][c] = Effect.HARMFUL;
                }
            }
        }
        for (String line : deadly) {
            Scanner sc = new Scanner(line);
            int x1 = sc.nextInt(), y1 = sc.nextInt(), x2 = sc.nextInt(), y2 = sc.nextInt();
            if (x2 < x1) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            if (y2 < y1) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int r = y1; r <= y2; r++) {
                for (int c = x1; c <= x2; c++) {
                    matrix[r][c] = Effect.DEADLY;
                }
            }
        }

        // create nodes
        Map<Location, Integer> cost = new HashMap<>();
        cost.put(START, 0);
        LocationPriorityQueue queue = new LocationPriorityQueue(Comparator.comparingInt(cost::get));
        queue.offer(START);
        Set<Location> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            visited.add(current);
            if (current.equals(END))
                break;
            for (int i = -2; i < 2; i++) {
                int r = current.row + i % 2;
                int c = current.col + (i + 1) % 2;
                if (r < 0 || c < 0 || r >= ROWS || c >= COLUMNS || matrix[r][c] == Effect.DEADLY)
                    continue;
                Location adj = new Location(r, c);
                if (visited.contains(adj))
                    continue;
                int myCost = cost.get(current);
                if (matrix[r][c] == Effect.HARMFUL)
                    myCost++;
                if (myCost < cost.getOrDefault(adj, Integer.MAX_VALUE)) {
                    cost.put(adj, myCost);
                }
                queue.offer(adj);
            }
        }

        return cost.getOrDefault(END, -1);
    }

    enum Effect {
        NORMAL, HARMFUL, DEADLY
    }

    static class Location {
        int row;
        int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return row == location.row && col == location.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "(%d, %d)".formatted(col, row);
        }
    }

    static class LocationPriorityQueue {
        Set<Location> set;
        PriorityQueue<Location> pq;

        public LocationPriorityQueue(Comparator<Location> comparator) {
            set = new HashSet<>();
            pq = new PriorityQueue<>(comparator);
        }

        public void offer(Location location) {
            if (!set.add(location))
                pq.remove(location);
            pq.offer(location);
        }

        public Location poll() {
            Location location = pq.poll();
            set.remove(location);
            return location;
        }

        public boolean contains(Location location) {
            return set.contains(location);
        }

        public boolean isEmpty() {
            return pq.isEmpty();
        }
    }
}
