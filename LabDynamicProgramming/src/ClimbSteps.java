import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClimbSteps {
    public static int climbSteps(int n) {
        int[] numWays = new int[Math.max(4, n + 1)];
        numWays[0] = 0;
        numWays[1] = 1;
        numWays[2] = 2;
        numWays[3] = 4;
        for (int i = 4; i <= n; i++) {
            numWays[i] = numWays[i - 1] + numWays[i - 2] + numWays[i - 3];
        }
        return numWays[n];
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("climbsteps.dat"));
        int T = input.nextInt();
        for (int i = 0; i < T; i++) {
            System.out.println(climbSteps(input.nextInt()));
        }
    }
}
