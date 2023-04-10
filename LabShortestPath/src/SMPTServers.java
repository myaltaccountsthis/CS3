import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SMPTServers {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("smpt.txt"));
        int N = input.nextInt();
        for (int i = 0; i < N; i++) {
            int n = input.nextInt(), m = input.nextInt(), s = input.nextInt(), t = input.nextInt();
            input.nextLine();
            Map<Integer, Set<Edge>> adjacencyList = new HashMap<>();
            for (int j = 0; j < n; j++)
                adjacencyList.put(j, new HashSet<>());
            for (int j = 0; j < m; j++) {
                Scanner sc = new Scanner(input.nextLine());
                int a = sc.nextInt(), b = sc.nextInt(), latency = sc.nextInt();
                adjacencyList.get(a).add(new Edge(b, latency));
                adjacencyList.get(b).add(new Edge(a, latency));
            }
            boolean[] visited = new boolean[n];
            Map<Integer, Integer> costs = new HashMap<>();
            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(costs::get));
            costs.put(s, 0);
            pq.offer(s);
            while (!pq.isEmpty()) {
                int current = pq.poll();
                visited[current] = true;
                if (current == t)
                    break;
                for (Edge adj : adjacencyList.get(current)) {
                    int index = adj.index;
                    if (visited[index])
                        continue;
                    int cost = costs.get(current) + adj.latency;
                    if (cost < costs.getOrDefault(index, Integer.MAX_VALUE)) {
                        if (costs.containsKey(index))
                            pq.remove(index);
                        costs.put(index, cost);
                        pq.offer(index);
                    }
                }
            }
            System.out.println(costs.containsKey(t) ? costs.get(t) : "unreachable");
        }
    }

    private static class Edge {
        int index;
        int latency;

        public Edge(int index, int latency) {
            this.index = index;
            this.latency = latency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return index == edge.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }
}
