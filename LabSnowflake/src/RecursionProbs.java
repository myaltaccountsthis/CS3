import java.util.List;
import java.util.Stack;

public class RecursionProbs {
    public double sumReciprocals(int n) {
        if (n == 0)
            return 0.0;
        return sumReciprocals(n - 1) + 1.0 / n;
    }

    public int productEvens(int n) {
        if (n == 0)
            return 1;
        return productEvens(n - 1) * (n) * 2;
    }

    public String conversion(int num, int base) {
        if (num == 0)
            return "";
        return conversion(num / base, base) + (num % base);
    }

    public int matchingDigits(int a, int b) {
        int matching = 0;
        if (a % 10 == b % 10)
            matching = 1;
        if (a == 0 || b == 0)
            return matching;
        return matchingDigits(a / 10, b / 10) + matching;
    }

    public void doubleUp(Stack<Integer> nums) {
        if (nums.empty())
            return;
        int n = nums.pop();
        doubleUp(nums);
        nums.push(n);
        nums.push(n);
    }

    public void printThis(int n) {
        if (n <= 2) {
            System.out.print("*".repeat(n));
            return;
        }
        System.out.print("<");
        printThis(n - 2);
        System.out.print(">");
    }

    public void printNums2(int n) {
        if (n <= 2) {
            System.out.print("1 ".repeat(n));
            return;
        }
        String str = (n + 1) / 2 + " ";
        System.out.print(str);
        printNums2(n - 2);
        System.out.print(str);
    }

    public static void main(String[] args) {
        RecursionProbs runner = new RecursionProbs();

        System.out.println(runner.sumReciprocals(10)); // 2.929
        System.out.println(runner.sumReciprocals(20)); // 3.598

        System.out.println(runner.productEvens(4)); // 384
        System.out.println(runner.productEvens(6)); // 46080

        System.out.println(runner.conversion(10, 2)); // 1010
        System.out.println(runner.conversion(1453, 8)); // 2655

        System.out.println(runner.matchingDigits(1000, 0)); // 1
        System.out.println(runner.matchingDigits(298892, 7892)); // 3

        Stack<Integer> stack = new Stack<>();
        stack.addAll(List.of(3, 7, 12, 9));
        runner.doubleUp(stack);
        System.out.println(stack); // 3, 3, 7, 7, 12, 12, 9, 9

        for (int i = 1; i <= 8; i++) {
            System.out.print("printThis(" + i + "):\t");
            runner.printThis(i);
            System.out.println();
        }

        for (int i = 1; i <= 10; i++) {
            System.out.print("printNums2(" + i + "):\t");
            runner.printNums2(i);
            System.out.println();
        }
    }
}
