import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PrimePath {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("prime.dat"));
        int n = input.nextInt();

        // run sieve
        Set<Integer> primes = new HashSet<>();
        boolean[] composite = new boolean[10000];
        for (int i = 2; i < composite.length / 2; i++) {
            if (!composite[i]) {
                for (int j = i * 2; j < composite.length; j += i)
                    composite[j] = true;
            }
        }
        for (int i = 1000; i < composite.length; i++) {
            if (!composite[i])
                primes.add(i);
        }

        // make graph
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (int num : primes) {
            Set<Integer> adjacent = new HashSet<>();
            char[] chars = (num + "").toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char original = chars[i];
                for (char c = '0'; c <= '9'; c++) {
                    chars[i] = c;
                    int adj = Integer.parseInt(new String(chars));
                    if (primes.contains(adj) && adj != num) {
                        adjacent.add(adj);
                    }
                }
                chars[i] = original;
            }
            adjacencyList.put(num, adjacent);
        }

        // do test cases
        for (int i = 0; i < n; i++) {
            int start = input.nextInt(), end = input.nextInt();
            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            Map<Integer, Integer> parents = new HashMap<>();
            visited.add(start);
            queue.offer(start);
            while (!queue.isEmpty()) {
                int prime = queue.poll();
                if (prime == end)
                    break;
                for (int adj : adjacencyList.get(prime)) {
                    if (!visited.contains(adj)) {
                        visited.add(adj);
                        queue.offer(adj);
                        parents.put(adj, prime);
                    }
                }
            }
            if (!visited.contains(end)) {
                System.out.println("no path");
            }
            else {
                int count = 0;
                for (int prime = end; prime != start; prime = parents.get(prime))
                    count++;
                System.out.println(count);
            }
        }
    }
}
