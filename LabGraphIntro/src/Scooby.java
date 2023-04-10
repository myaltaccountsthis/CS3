import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Scooby {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("scooby.dat"));
        int n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n; i++) {
            String line = input.nextLine();
            String[] edges = line.split(" ");
            line = input.nextLine();
            String start = line.substring(0, 1), end = line.substring(1, 2);
            Map<String, Set<String>> adjacencyList = new HashMap<>();
            for (String edge : edges) {
                String edgeStart = edge.substring(0, 1), edgeEnd = edge.substring(1, 2);
                adjacencyList.putIfAbsent(edgeStart, new HashSet<>());
                adjacencyList.putIfAbsent(edgeEnd, new HashSet<>());
                adjacencyList.get(edgeStart).add(edgeEnd);
                adjacencyList.get(edgeEnd).add(edgeStart);
            }
            Set<String> visited = new HashSet<>();
            Stack<String> stack = new Stack<>();
            visited.add(start);
            stack.add(start);
            while (!stack.isEmpty()) {
                String current = stack.pop();
                if (!adjacencyList.containsKey(current))
                    continue;
                for (String adj : adjacencyList.get(current)) {
                    if (!visited.contains(adj)) {
                        visited.add(adj);
                        stack.push(adj);
                        if (adj.equals(end))
                            break;
                    }
                }
            }
            if (visited.contains(end)) {
                System.out.println("yes");
            }
            else {
                System.out.println("no");
            }
        }
    }
}
