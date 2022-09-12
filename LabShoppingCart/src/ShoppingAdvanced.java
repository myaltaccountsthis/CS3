public class ShoppingAdvanced {
    // returns the greatest profit from i to stock.length - 1
    private int recursiveSell(int[] stock, int n, int i) {
        if (i == stock.length - 1 || n == 0)
            return 0;

        int highest = 0;
        for (int j = i + 1; j < stock.length; j++) {
            highest = Math.max(highest, recursiveSell(stock, n - 1, j) + stock[j] - stock[i]);
            highest = Math.max(highest, recursiveSell(stock, n, j));
        }
        return highest;
    }

    public int buySell2(int[] stock) {
        return recursiveSell(stock, Integer.MAX_VALUE, 0);
    }

    public int buySell3(int[] stock) {
        return recursiveSell(stock, 2, 0);
    }

    public int buySell4(int k, int[] stock) {
        return recursiveSell(stock, k, 0);
    }

    public static void main(String[] args) {
        ShoppingAdvanced a = new ShoppingAdvanced();

        System.out.println(a.buySell2(new int[] {1, 2, 7, 4, 11}));
        System.out.println(a.buySell2(new int[] {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8}));

        System.out.println(a.buySell3(new int[] {1, 4, 7, 2, 11}));
        System.out.println(a.buySell3(new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9}));

        System.out.println(a.buySell4(4, new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0}));
        System.out.println(a.buySell4(2, new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9}));
    }
}
