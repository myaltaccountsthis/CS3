public class RodCutting {
    public static int maxRevenue(int length, int[] prices) {
        if (length == 0) {
            System.out.print(0);
            return 0;
        }
        int[] maxRev = new int[length + 1];
        maxRev[1] = prices[1];
        int[] numCuts = new int[length + 1];
        numCuts[1] = 1;
        for (int i = 2; i <= length; i++) {
            int maxIndex = 0;
            for (int j = 1; j <= i; j++) {
                int rev = maxRev[i - j] + prices[j];
                if (rev > maxRev[i]) {
                    maxRev[i] = rev;
                    maxIndex = j;
                }
            }
            numCuts[i] = numCuts[maxIndex] + 1;
        }
        System.out.printf("Cuts: %d", numCuts[length]);
        return maxRev[length];
    }

    public static void main(String[] args) {
        System.out.println(maxRevenue(4, new int[] {0, 1, 5, 8, 9}));
        int[] prices = new int[] {0, 1, 5, 8, 9, 10, 17, 17, 20};
        for (int i = 0; i < prices.length; i++) {
            System.out.print(i + " ");
            System.out.printf(", Max: %d%n", maxRevenue(i, prices));
        }
    }
}
