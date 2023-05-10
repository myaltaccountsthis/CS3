import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Intermediate {
    public static int unboundKnapsack(int W, int[] val, int[] wt) {
        assert val.length == wt.length;
        assert W >= 0;
        int[] maxValues = new int[W + 1];
        Arrays.fill(maxValues, -1);
        maxValues[0] = 0;
        for (int weight = 0; weight < W; weight++) {
            if (maxValues[weight] == -1)
                continue;
            for (int i = 0; i < val.length; i++) {
                int w = weight + wt[i];
                if (w <= W)
                    maxValues[w] = Math.max(maxValues[w], maxValues[weight] + val[i]);
            }
        }
        return maxValues[W];
    }

    public static void jillRidesAgain(int[] niceness, int r) {
        int[] dp = new int[niceness.length];
        int max = niceness[0];
        dp[0] = niceness[0];
        for (int i = 1; i < niceness.length; i++) {
            dp[i] = Math.max(niceness[i], dp[i - 1] + niceness[i]);
            max = Math.max(max, dp[i]);
        }
        System.out.println(Arrays.toString(dp));
        int start, end;
        int longestStart = 0, longestEnd = -1;
        for (end = niceness.length - 1; end >= 0; end--) {
            if (dp[end] == max) {
                int n = max;
                int min = end;
                for (start = end; start >= 0; start--) {
                    n -= niceness[start];
                    if (n == 0)
                        min = start;
                }
                if (end - min > longestEnd - longestStart) {
                    longestStart = min;
                    longestEnd = end;
                }
            }
        }
        if (longestEnd != -1 && dp[longestEnd] > 0)
            System.out.printf("The nicest part of route %d is between stops %d and %d%n", r + 1, longestStart + 1, longestEnd + 2);
        else
            System.out.printf("Route %d has no nice parts%n", r + 1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(unboundKnapsack(100, new int[] {1, 30}, new int[] {1, 50}));
        System.out.println(unboundKnapsack(8, new int[] {10, 40, 50, 70}, new int[] {1, 3, 4, 5}));
        {
            Scanner input = new Scanner(new File("jillridesagain.dat"));
            int n = input.nextInt();
            for (int r = 0; r < n; r++) {
                int s = input.nextInt();
                int[] niceness = new int[s - 1];
                for (int i = 0; i < s - 1; i++) {
                    niceness[i] = input.nextInt();
                }
                jillRidesAgain(niceness, r);
            }
        }
    }
}
