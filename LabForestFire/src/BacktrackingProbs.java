import java.util.*;

public class BacktrackingProbs {
    public void printBinary(int digits) {
        printBinaryHelper(digits, "");
    }
    private void printBinaryHelper(int digits, String soFar) {
        if (soFar.length() == digits) {
            System.out.print(soFar + ' ');
            return;
        }

        printBinaryHelper(digits, soFar + '0');
        printBinaryHelper(digits, soFar + '1');
    }

    public void climbStairs(int steps) {
        climbStairsHelper(steps, new Stack<>(), 0);
    }
    private void climbStairsHelper(int steps, Stack<Integer> currentSteps, int sum) {
        if (sum > steps)
            return;

        if (sum == steps) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < currentSteps.size(); i++) {
                if (i > 0)
                    sb.append(", ");
                sb.append(currentSteps.get(i));
            }
            System.out.println(sb);
            return;
        }

        currentSteps.push(1);
        sum++;
        climbStairsHelper(steps, currentSteps, sum);
        currentSteps.pop();
        currentSteps.push(2);
        sum++;
        climbStairsHelper(steps, currentSteps, sum);
        currentSteps.pop();
    }

    private enum Direction {
        N, E, NE
    }
    public void campsite(int x, int y) {
        campsiteHelper(x, y, new Stack<>(), 0, 0);
    }
    private void campsiteHelper(int x, int y, Stack<Direction> currentMoves, int currentX, int currentY) {
        if (currentX == x && currentY == y) {
            for (Direction d : currentMoves)
                System.out.print(d + " ");
            System.out.println();
            return;
        }
        boolean moveX = currentX < x, moveY = currentY < y;
        if (moveX) {
            currentMoves.push(Direction.E);
            campsiteHelper(x, y, currentMoves, currentX + 1, currentY);
            currentMoves.pop();
        }
        if (moveY) {
            currentMoves.push(Direction.N);
            campsiteHelper(x, y, currentMoves, currentX, currentY + 1);
            currentMoves.pop();
        }
        if (moveX && moveY) {
            currentMoves.push(Direction.NE);
            campsiteHelper(x, y, currentMoves, currentX + 1, currentY + 1);
            currentMoves.pop();
        }
    }

    public int getMax(List<Integer> nums, int limit) {
        return getMaxHelper(nums, limit, 0, 0);
    }
    private int getMaxHelper(List<Integer> nums, int limit, int index, int sum) {
        if (sum > limit)
            return Integer.MIN_VALUE;
        if (index >= nums.size())
            return sum;

        return Math.max(getMaxHelper(nums, limit, index + 1, sum), getMaxHelper(nums, limit, index + 1, sum + nums.get(index)));
    }

    public int makeChange(int amount) {
        return makeChangeHelper(Arrays.asList(1, 5, 10, 25), 0, 0, amount);
    }
    private int makeChangeHelper(List<Integer> coins, int index, int current, int amount) {
        if (current > amount)
            return 0;
        if (current == amount)
            return 1;

        int sum = 0;
        for (int i = index; i < coins.size(); i++)
            sum += makeChangeHelper(coins, i, current + coins.get(coins.size() - i - 1), amount);
        return sum;
    }

    public int makeChangePrint(int amount) {
        HashSet<String> solutions = new HashSet<>();
        makeChangePrintHelper(Arrays.asList(1, 5, 10, 25), 0, 0, amount, new int[4], solutions);
        for (String s : solutions)
            System.out.println(s);
        return solutions.size();
    }
    private void makeChangePrintHelper(List<Integer> coins, int index, int current, int amount, int[] currentCoins, Set<String> solutions) {
        if (current > amount) {
            return;
        }
        if (current == amount) {
            solutions.add(Arrays.toString(currentCoins));
            return;
        }

        for (int i = index; i < coins.size(); i++) {
            int coinIndex = coins.size() - i - 1;
            currentCoins[coinIndex]++;
            makeChangePrintHelper(coins, i, current + coins.get(coinIndex), amount, currentCoins, solutions);
            currentCoins[coinIndex]--;
        }
    }

    public String longestCommonSub(String a, String b) {
        return longestCommonSubHelper(a, b, 0, 0);
    }
    private String longestCommonSubHelper(String a, String b, int aStart, int bStart) {
        if (aStart == a.length() || bStart == b.length())
            return "";

        String longest = "";
        if (a.charAt(aStart) == b.charAt(bStart))
            longest = a.charAt(aStart) + longestCommonSubHelper(a, b, aStart + 1, bStart + 1);

        String s1 = longestCommonSubHelper(a, b, aStart + 1, bStart);
        String s2 = longestCommonSubHelper(a, b, aStart, bStart + 1);
        if (s1.length() > longest.length())
            longest = s1;
        if (s2.length() > longest.length())
            longest = s2;

        return longest;
    }

    public static void main(String[] args) {
        BacktrackingProbs probs = new BacktrackingProbs();

        probs.printBinary(3);
        System.out.println();

        probs.climbStairs(4);

        probs.campsite(2, 1);

        System.out.println(probs.getMax(Arrays.asList(7, 30, 8, 22, 6, 1, 14), 19));

        System.out.println(probs.makeChange(25));

        probs.makeChangePrint(11);

        System.out.println(probs.longestCommonSub("ABCDEFG", "BGCEHAF"));
    }
}
