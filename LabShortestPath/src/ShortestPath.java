import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ShortestPath {
    private final Map<Integer, Set<Integer>> adjacencyList;
    private final Map<Integer, Location> locations;
    private final Integer start, end;

    public ShortestPath(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));
        int V = input.nextInt(), E = input.nextInt();
        adjacencyList = new HashMap<>();
        locations = new HashMap<>();
        // load graph from input
        for (int i = 0; i < V; i++) {
            int index = input.nextInt();
            int x = input.nextInt(), y = input.nextInt();
            Location location = new Location(x, y);
            locations.put(index, location);
            adjacencyList.put(index, new HashSet<>());
        }
        for (int i = 0; i < E; i++) {
            int a = input.nextInt(), b = input.nextInt();
            adjacencyList.get(a).add(b);
            adjacencyList.get(b).add(a);
        }
        start = 0;
        end = locations.size() - 1;
    }

    public void dijkstra() {
        Map<Integer, Double> costs = new HashMap<>();
        Map<Integer, Integer> parents = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(costs::get));
        costs.put(0, 0.0);
        pq.offer(start);
        while (!pq.isEmpty()) {
            int current = pq.poll();
            visited.add(current);
            if (current == end)
                break;
            for (int adj : adjacencyList.get(current)) {
                if (visited.contains(adj))
                    continue;
                double cost = costs.get(current);
                cost += locations.get(current).distanceTo(locations.get(adj));
                if (cost < costs.getOrDefault(adj, Double.POSITIVE_INFINITY)) {
                    if (costs.containsKey(adj))
                        pq.remove(adj);
                    costs.put(adj, cost);
                    parents.put(adj, current);
                    pq.offer(adj);
                }
            }
        }
        if (!parents.containsKey(end)) {
            System.out.println("No path found");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        for (int current = end; current != start; current = parents.get(current))
            path.addFirst(current);
        path.addFirst(start);
        System.out.println(path.stream().map(Object::toString).collect(Collectors.joining(" -> ")));
        System.out.printf("Distance: %.1f%n", costs.get(end));
    }

    public void aStar() {
        Map<Integer, Double> costs = new HashMap<>();
        Map<Integer, Integer> parents = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> costs.get(i) + getAStarHeuristic(i)));
        costs.put(0, 0.0);
        pq.offer(start);
        while (!pq.isEmpty()) {
            int current = pq.poll();
            visited.add(current);
            if (current == end)
                break;
            for (int adj : adjacencyList.get(current)) {
                if (visited.contains(adj))
                    continue;
                double cost = costs.get(current);
                cost += locations.get(current).distanceTo(locations.get(adj));
                if (cost < costs.getOrDefault(adj, Double.POSITIVE_INFINITY)) {
                    if (costs.containsKey(adj))
                        pq.remove(adj);
                    costs.put(adj, cost);
                    parents.put(adj, current);
                    pq.offer(adj);
                }
            }
        }
        if (!parents.containsKey(end)) {
            System.out.println("No path found");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        for (int current = end; current != start; current = parents.get(current))
            path.addFirst(current);
        path.addFirst(start);
        System.out.println(path.stream().map(Object::toString).collect(Collectors.joining(" -> ")));
        System.out.printf("Distance: %.1f%n", costs.get(end));
    }

    private static final double H_SCALE = 1.0;
    private double getAStarHeuristic(int current) {
        return H_SCALE * locations.get(current).distanceTo(locations.get(end));
    }

    public static void main(String[] args) throws FileNotFoundException {
        for (String fileName : new String[] { "input6.txt", "grid25x25.txt", "usa.txt" }) {
            System.out.println(fileName);
            ShortestPath path = new ShortestPath(fileName);
            System.out.println("Dijkstra");
            long t = System.nanoTime();
            path.dijkstra();
            long tDijkstra = System.nanoTime() - t;
            System.out.printf("Time: %.3fms%n", tDijkstra / 1e6);
            System.out.println("A*");
            t = System.nanoTime();
            path.aStar();
            long tAStar = System.nanoTime() - t;
            System.out.printf("Time: %.3fms%n", tAStar / 1e6);
            System.out.println();
        }
    }
}
