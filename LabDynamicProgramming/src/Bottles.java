import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bottles {
    public static int maxVolume(int[] bottles) {
        if (bottles.length == 0)
            return 0;
        if (bottles.length == 1)
            return bottles[0];
        int[] maxCumVol = new int[bottles.length];
        maxCumVol[0] = bottles[0];
        maxCumVol[1] = Math.max(bottles[0], bottles[1]);
        for (int i = 2; i < maxCumVol.length; i++) {
            maxCumVol[i] = Math.max(maxCumVol[i - 2] + bottles[i], maxCumVol[i - 1]);
        }
        return maxCumVol[maxCumVol.length - 1];
    }

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println(maxVolume(new int[] {15, 20, 5, 18, 19, 27, 31, 21, 3, 0, 17, 16}));
        Scanner input = new Scanner(new File("bottles.dat"));
        int n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n; i++) {
            String line = input.nextLine();
            String[] tokens = line.split(" ");
            int[] bottles = new int[tokens.length];
            for (int j = 0; j < tokens.length; j++)
                bottles[j] = Integer.parseInt(tokens[j]);
            System.out.println(maxVolume(bottles));
        }
    }
}
