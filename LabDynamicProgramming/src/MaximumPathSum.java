import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MaximumPathSum {
    public static int maxSum(int[][] triangle) {
        int[][] sumTriangle = new int[triangle.length][triangle[0].length];
        System.arraycopy(triangle[triangle.length - 1], 0, sumTriangle[triangle.length - 1], 0, sumTriangle.length);
        for (int r = triangle.length - 2; r >= 0; r--) {
            for (int c = 0; c <= r; c++) {
                sumTriangle[r][c] = Math.max(sumTriangle[r + 1][c], sumTriangle[r + 1][c + 1]) + triangle[r][c];
            }
        }
        return sumTriangle[0][0];
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(maxSum(new int[][] {{3, 0, 0, 0}, {7, 4, 0, 0}, {2, 4, 6, 0}, {8, 5, 9, 3}}));
        Scanner input = new Scanner(new File("triangle.txt"));
        int[][] triangle = new int[100][100];
        for (int i = 0; i < triangle.length; i++) {
            for (int j = 0; j <= i; j++) {
                triangle[i][j] = input.nextInt();
            }
        }
        System.out.println(maxSum(triangle));
    }
}
