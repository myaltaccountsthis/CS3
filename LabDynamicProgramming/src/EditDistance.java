import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class EditDistance {
    public static int penalty(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
//        Scanner input = new Scanner(new File("ecoli50000.txt"));
        String s1 = input.next(), s2 = input.next();
        input.close();
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray();
        int[][] minCosts = new int[c1.length + 1][c2.length + 1];
        for (int r = c1.length - 1; r >= 0; r--)
            minCosts[r][c2.length] = (c1.length - r) * 2;
        for (int c = c2.length - 1; c >= 0; c--)
            minCosts[c1.length][c] = (c2.length - c) * 2;
        for (int r = c1.length - 1; r >= 0; r--) {
            for (int c = c2.length - 1; c >= 0; c--) {
                minCosts[r][c] = min(minCosts[r + 1][c] + 2, minCosts[r][c + 1] + 2, minCosts[r + 1][c + 1] + penalty(c1[r], c2[c]));
            }
        }
//        for (int[] row : minCosts)
//            System.out.println(Arrays.toString(row));
        System.out.printf("Edit distance = %d%n", minCosts[0][0]);
        int r = 0, c = 0;
        while (r != c1.length || c != c2.length) {
            if (r < c1.length && minCosts[r][c] == minCosts[r + 1][c] + 2) {
                System.out.printf("%c -  2%n", c1[r]);
                r++;
            }
            else if (c < c2.length && minCosts[r][c] == minCosts[r][c + 1] + 2) {
                System.out.printf("- %c  2%n", c2[c]);
                c++;
            }
            else {
                System.out.printf("%c %c  %d%n", c1[r], c2[c], penalty(c1[r], c2[c]));
                r++;
                c++;
            }
        }
    }
}
