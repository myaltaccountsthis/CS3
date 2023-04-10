import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinaryMaze {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("maze.dat"));
        int rows = input.nextInt(), cols = input.nextInt(), numTests = input.nextInt();
        boolean[][] maze = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                maze[r][c] = input.nextInt() == 1;
            }
        }
        for (int i = 0; i < numTests; i++) {
            Location start = new Location(input.nextInt(), input.nextInt()),
                    end = new Location(input.nextInt(), input.nextInt());
            Map<Location, Integer> cost = new HashMap<>();
            Set<Location> visited = new HashSet<>();
            Queue<Location> queue = new LinkedList<>();
            visited.add(start);
            queue.add(start);
            cost.put(start, 0);
            while (!queue.isEmpty()) {
                Location current = queue.poll();
                if (current.equals(end))
                    break;
                for (int j = -2; j < 2; j++) {
                    int r = current.row + j % 2;
                    int c = current.col + (j + 1) % 2;
                    if (r < 0 || c < 0 || r >= rows || c >= cols || !maze[r][c])
                        continue;
                    Location adj = new Location(r, c);
                    if (!visited.contains(adj)) {
                        visited.add(adj);
                        queue.offer(adj);
                        cost.put(adj, cost.get(current) + 1);
                    }
                }
            }
            System.out.println(cost.getOrDefault(end, -1));
        }
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
    }
}
