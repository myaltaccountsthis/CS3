import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ChildsPlay {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("play.dat"));
        int numTests = input.nextInt();
        for (int i = 0; i < numTests; i++) {
            int n = input.nextInt(), m = input.nextInt(), l = input.nextInt();
            // each number inputted will be one less index
            Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
            for (int j = 0; j < n; j++) {
                adjacencyList.put(j, new HashSet<>());
            }
            for (int j = 0; j < m; j++) {
                adjacencyList.get(input.nextInt() - 1).add(input.nextInt() - 1);
            }
            boolean[] fallen = new boolean[n];
            for (int j = 0; j < l; j++) {
                knockDown(adjacencyList, fallen, input.nextInt() - 1);
            }
            int count = 0;
            for (boolean b : fallen) {
                if (b)
                    count++;
            }
            System.out.println(count);
        }
    }

    private static void knockDown(Map<Integer, Set<Integer>> adjacencyList, boolean[] fallen, int domino) {
        if (fallen[domino])
            return;
        fallen[domino] = true;
        for (int adj : adjacencyList.get(domino)) {
            knockDown(adjacencyList, fallen, adj);
        }
    }
}
