import java.util.*;

public class Main {
    public static long fibonacci(int n) {
        if (n <= 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static long fibonacciMemo(int n) {
        return fibonacciMemoHelper(new HashMap<>(), n);
    }
    private static long fibonacciMemoHelper(Map<Integer, Long> cache, int n) {
        if (cache.containsKey(n))
            return cache.get(n);
        if (n <= 1)
            return 1;
        long a = fibonacciMemoHelper(cache, n - 1), b = fibonacciMemoHelper(cache, n - 2);
        cache.putIfAbsent(n - 1, a);
        cache.putIfAbsent(n - 2, b);
        return a + b;
    }

    public static int numPaths(int[][] grid) {
        Integer[][] cache = new Integer[grid.length][grid[0].length];
        return numPathsHelper(grid, cache, 0, 0);
    }
    private static int numPathsHelper(int[][] grid, Integer[][] cache, int row, int col) {
        if (row >= grid.length || col >= grid[0].length)
            return 0;
        if (row == grid.length - 1 && col == grid[0].length - 1)
            return 1;
        if (cache[row][col] != null)
            return cache[row][col];
        int a = numPathsHelper(grid, cache, row + 1, col);
        int b = numPathsHelper(grid, cache, row, col + 1);
        if (a > 0)
            cache[row + 1][col] = a;
        if (b > 0)
            cache[row][col + 1] = b;
        return a + b;
    }

    public static int minCoins(int total, int[] coins) {
        Map<Integer, Integer> cache = new HashMap<>();
        int n = minCoinsHelper(total, coins, cache);
        return n == Integer.MAX_VALUE ? -1 : n;
    }
    private static int minCoinsHelper(int total, int[] coins, Map<Integer, Integer> cache) {
        if (total < 0)
            return Integer.MAX_VALUE;
        if (total == 0)
            return 0;
        if (cache.containsKey(total))
            return cache.get(total);
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int numCoins = minCoinsHelper(total - coin, coins, cache);
            if (numCoins != Integer.MAX_VALUE) {
                min = Math.min(min, numCoins);
                cache.put(total - coin, numCoins);
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1;
    }

    public static int robHousesArr(int[] houses) {
        int[] cache = new int[houses.length];
        return robHousesArrHelper(houses, cache, 0, false);
    }
    private static int robHousesArrHelper(int[] houses, int[] cache, int i, boolean robbedPrev) {
        if (i >= houses.length)
            return 0;
        if (cache[i] > 0) {
            // return if cannot rob this house
            if (robbedPrev)
                return cache[i];
        }
        int max;
        if (robbedPrev) {
            max = robHousesArrHelper(houses, cache, i + 1, false);
            cache[i] = max;
        }
        else {
            int a = robHousesArrHelper(houses, cache, i + 1, false), b = robHousesArrHelper(houses, cache, i + 1, true) + houses[i];
            max = Math.max(a, b);
            if (a == max)
                cache[i] = max;
        }
        return max;
    }

    public static int robHousesMap(int[] houses) {
        return robHousesMapHelper(houses, new HashMap<>(), 0, false);
    }
    private static int robHousesMapHelper(int[] houses, Map<Integer, Integer> cache, int i, boolean robbedPrev) {
        if (i >= houses.length)
            return 0;
        if (cache.containsKey(i)) {
            // return if cannot rob this house
            if (robbedPrev)
                return cache.get(i);
        }
        int max;
        if (robbedPrev) {
            max = robHousesMapHelper(houses, cache, i + 1, false);
            cache.put(i, max);
        }
        else {
            int a = robHousesMapHelper(houses, cache, i + 1, false), b = robHousesMapHelper(houses, cache, i + 1, true) + houses[i];
            max = Math.max(a, b);
            if (a == max)
                cache.put(i, max);
        }
        return max;
    }

    public static void main(String[] args) {
//        trackMillis(() -> System.out.println(fibonacci(45)));
        trackMillis(() -> System.out.println(fibonacciMemo(45)));
        System.out.println(numPaths(new int[10][10]));
        System.out.println(minCoins(11, new int[] {9, 6, 5, 1}));
        System.out.println(minCoins(13, new int[] {9, 6, 5, 1}));
        System.out.println(minCoins(4, new int[] {3, 5, 7}));
        trackMillis(() -> System.out.println(robHousesArr(new int[] {200,234,182,111,87,194,221,217,71,162,140,51,81,80,232,193,223,103,139,103})));
        trackMillis(() -> System.out.println(robHousesMap(new int[] {200,234,182,111,87,194,221,217,71,162,140,51,81,80,232,193,223,103,139,103})));
    }

    public static void trackMillis(Runnable task) {
        long t = System.nanoTime();
        task.run();
        System.out.println((System.nanoTime() - t) / 1e6 + "ms");
    }
}