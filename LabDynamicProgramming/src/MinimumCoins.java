import java.util.Arrays;

public class MinimumCoins {
    public static int minCoins(int sum, int[] values) {
        int[] numCoins = new int[sum + 1];
        Arrays.fill(numCoins, Integer.MAX_VALUE);
        numCoins[0] = 0;
        for (int i = 1; i <= sum; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : values) {
                if (i - coin >= 0)
                    min = Math.min(min, numCoins[i - coin] + 1);
            }
            if (min != Integer.MAX_VALUE)
                numCoins[i] = min;
        }
        return numCoins[sum];
    }

    public static void main(String[] args) {
        System.out.println(minCoins(11, new int[] {9, 6, 5, 1})); // 2
        System.out.println(minCoins(13, new int[] {9, 6, 5, 1})); // 3
        System.out.println(minCoins(20, new int[] {9, 6, 5, 1})); // 3
    }
}
