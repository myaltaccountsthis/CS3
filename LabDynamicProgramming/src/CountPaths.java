public class CountPaths {
    public static int numberOfPaths(int m, int n) {
        if (m <= 0 || n <= 0)
            return 0;
        // matrix starts at [1,1]
        int[][] matrix = new int[m + 1][n + 1];
        matrix[1][1] = 1;
        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                if (r == 1 && c == 1)
                    continue;
                matrix[r][c] = matrix[r - 1][c] + matrix[r][c - 1] + matrix[r - 1][c - 1];
            }
        }
        return matrix[m][n];
    }

    public static void main(String[] args) {
        System.out.println(numberOfPaths(3, 3));
        System.out.println(numberOfPaths(5, 5));
    }
}
