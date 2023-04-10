import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Marketing {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("marketing.dat"));
        int n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n; i++) {
            String line = input.nextLine();
            String[] tokens = line.split(",");
            for (int j = 0; j < tokens.length; j++) {
                tokens[j] = tokens[j].replaceAll("\"", "");
            }
            System.out.println(howMany(tokens));
        }
    }

    public static long howMany(String[] compete) {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (int i = 0; i < compete.length; i++) {
            adjacencyList.putIfAbsent(i, new HashSet<>());
            Scanner sc = new Scanner(compete[i]);
            while (sc.hasNextInt()) {
                int x = sc.nextInt();
                adjacencyList.putIfAbsent(x, new HashSet<>());
                adjacencyList.get(x).add(i);
                adjacencyList.get(i).add(x);
            }
        }
        boolean[] isAdult = new boolean[compete.length];
        long count = solve(adjacencyList, isAdult, 0, false) + solve(adjacencyList, isAdult, 0, true);
        if (count == 0)
            return -1;
        return count;
    }

    private static long solve(Map<Integer, Set<Integer>> adjacencyList, boolean[] isAdult, int i, boolean adult) {
        if (i >= isAdult.length) {
            // only add once
            return adult ? 1 : 0;
        }
        // double the next one if empty, but return if not adult (so it only runs once)
        if (adjacencyList.get(i).isEmpty() && !adult)
            return 0;
        // check adjacency
        for (int adj : adjacencyList.get(i)) {
            if (adj < i && isAdult[adj] == adult)
                return 0;
        }
        isAdult[i] = adult;
        long count = solve(adjacencyList, isAdult, i + 1, false) + solve(adjacencyList, isAdult, i + 1, true);
        isAdult[i] = false;
        if (adjacencyList.get(i).isEmpty()) {
            return 2 * count;
        }
        return count;
    }
}
