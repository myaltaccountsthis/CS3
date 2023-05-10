import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PascalTriangle {
    private final Map<Pair, Long> cache = new HashMap<>();

    public long pascal(int row, int col) {
        Pair pair = new Pair(row, col);
        if (cache.containsKey(pair))
            return cache.get(pair);
        if (row <= 1 || col == 0 || col == row)
            return 1;
        long a = pascal(row - 1, col), b = pascal(row - 1, col - 1);
        cache.put(new Pair(row - 1, col), a);
        cache.put(new Pair(row - 1, col - 1), b);
        return a + b;
    }

    public void printPascal(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int c = 0; c <= i; c++) {
                System.out.print(pascal(i, c) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        PascalTriangle triangle = new PascalTriangle();
        Main.trackMillis(() -> triangle.printPascal(40));
    }

    static class Pair {
        int row;
        int col;

        public Pair(int row, int col)  {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return row == pair.row && col == pair.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
