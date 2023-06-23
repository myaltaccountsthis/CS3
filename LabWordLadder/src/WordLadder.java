import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordLadder {
    private final Collection<String> dictionary;

    public WordLadder(String dictionaryFile) throws FileNotFoundException {
        long t = System.nanoTime();
        // scanner bad
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
        dictionary = reader.lines().map(String::toLowerCase).toList();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Load time: " + (System.nanoTime() - t) / 1e6 + " ms");
    }

    private class Solver {
        private final String start, end;
        private boolean ran = false;
        private Stack<String> solution = null;
        long adjTime = 0;

        private final Collection<String> validWords = new HashSet<>();
        private final Queue<Stack<String>> queue = new LinkedList<>();

        public Solver(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getMessage() {
            if (!ran)
                return "Ladder not checked";
            if (solution != null)
                return "Found a ladder! >>> " + solution;
            return "No ladder between " + start + " and " + end;
        }

        /**
         * Return all words that differ by one letter from {@code String word}, if they are not in {@code ArrayList<String> ignoreList}
         *
         * @param word
         * @return
         */
        private Collection<String> getAdjacentWords(String word) {
            long t = System.nanoTime();
            Collection<String> words = new ArrayList<>();
            // optimization, the fastest way to get all adjacent chars (26 * word.length(), e.g. 130 for 5 letter word)
            // compare to looping through all in validWords and checking each one's character difference (e.g. 8.4k for 5 letter word)
            char[] arr = word.toCharArray();
            for (int i = 0; i < word.length(); i++) {
                char original = word.charAt(i);
                for (char c = 'a'; c <= 'z'; c++) {
                    // using char array is faster bc string concatenation substring is slow
                    arr[i] = c;
                    String s = new String(arr);
                    // don't need to check if s.equals(word) bc validWords.contains(word) will return false
                    if (validWords.contains(s))
                        words.add(s);
                }
                arr[i] = original;
            }
            adjTime += System.nanoTime() - t;
            return words;
        }

        public void solve() {
            assert !ran : "Solver already ran";
            ran = true;

            if (start.length() != end.length())
                return;

            { // setup
                for (String s : dictionary)
                    if (s.length() == start.length())
                        validWords.add(s);
                if (!validWords.contains(start) || !validWords.contains(end))
                    return;
                queue.offer(new Stack<>() {{ push(start); }});
                validWords.remove(start);
            }

            Stack<String> shortest = null;
            while (!queue.isEmpty()) {
                Stack<String> stack = queue.poll();
                if (stack.peek().equals(end)) {
                    shortest = stack;
                    break;
                }

                Collection<String> adjacent = getAdjacentWords(stack.peek());
                for (String str : adjacent) {
                    Stack<String> s = new Stack<>() {{ addAll(stack); push(str); }};
                    validWords.remove(str);
                    queue.offer(s);
                }
            }

            this.solution = shortest;
        }
    }

    private class SolverBetter {
        private final String start, end;
        private boolean ran = false;
        private List<String> solution = null;
        long adjTime = 0;

        private final Collection<String> validWords = new HashSet<>();
        private final Map<String, String> parents = new HashMap<>();
        private final Map<String, Integer> costs = new HashMap<>();
        private final Map<String, Integer> heuristics = new HashMap<>();
        private final PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(s -> costs.get(s) + heuristics.get(s)));

        public SolverBetter(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getMessage() {
            if (!ran)
                return "Ladder not checked";
            if (solution != null)
                return "Found a ladder! >>> " + solution;
            return "No ladder between " + start + " and " + end;
        }

        /**
         * Return all words that differ by one letter from {@code String word}, if they are not in {@code ArrayList<String> ignoreList}
         *
         * @param word
         * @return
         */
        private Collection<String> getAdjacentWords(String word) {
            long t = System.nanoTime();
            Collection<String> words = new ArrayList<>();
            // optimization, the fastest way to get all adjacent chars (26 * word.length(), e.g. 130 for 5 letter word)
            // compare to looping through all in validWords and checking each one's character difference (e.g. 8.4k for 5 letter word)
            char[] arr = word.toCharArray();
            for (int i = 0; i < word.length(); i++) {
                char original = word.charAt(i);
                for (char c = 'a'; c <= 'z'; c++) {
                    // using char array is faster bc string concatenation substring is slow
                    arr[i] = c;
                    String s = new String(arr);
                    // don't need to check if s.equals(word) bc validWords.contains(word) will return false
                    if (validWords.contains(s))
                        words.add(s);
                }
                arr[i] = original;
            }
            adjTime += System.nanoTime() - t;
            return words;
        }

        private int h(String word) {
            int err = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != end.charAt(i))
                    err++;
            }
            return err;
        }

        public void solve() {
            assert !ran : "Solver already ran";
            ran = true;

            if (start.length() != end.length())
                return;

            { // setup
                for (String s : dictionary)
                    if (s.length() == start.length())
                        validWords.add(s);
                if (!validWords.contains(start) || !validWords.contains(end))
                    return;
                parents.put(start, null);
                costs.put(start, 0);
                heuristics.put(start, h(start));
                pq.offer(start);
                validWords.remove(start);
            }

            while (!pq.isEmpty()) {
                String current = pq.poll();
                if (current.equals(end)) {
                    break;
                }
                Collection<String> adjacent = getAdjacentWords(current);
                for (String str : adjacent) {
                    // this only runs once per word
                    parents.put(str, current);
                    costs.put(str, costs.get(current) + 1);
                    heuristics.put(str, h(str));
                    pq.offer(str);
                    validWords.remove(str);
                }
            }

            if (parents.containsKey(end)) {
                LinkedList<String> list = new LinkedList<>();
                for (String word = end; word != null; word = parents.get(word))
                    list.addFirst(word);
                this.solution = list;
            }
        }
    }

    public Solver solve(String start, String end) {
        Solver solver = new Solver(start, end);
        solver.solve();
        return solver;
    }

    public SolverBetter solveImproved(String start, String end) {
        SolverBetter solver = new SolverBetter(start, end);
        solver.solve();
        return solver;
    }

    public void solveFile(String fileName) throws FileNotFoundException {
        long t = System.nanoTime();
        System.out.println("ms\t\tadj ms\t  result");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Iterable<String> lines = reader.lines().toList();
        for (String line : lines) {
            String[] tokens = line.split(" ");
            String start = tokens[0], end = tokens[1];
            {
                long solve = System.nanoTime();
                Solver solver = solve(start, end);
                System.out.printf("%d\t\t%d\t\t%s%n", (System.nanoTime() - solve) / 1000000, solver.adjTime / 1000000, solver.getMessage());
            }
            {
                long solve = System.nanoTime();
                SolverBetter solver = solveImproved(start, end);
                System.out.printf("%d\t\t%d\t\t%s%n", (System.nanoTime() - solve) / 1000000, solver.adjTime / 1000000, solver.getMessage());
            }
        }
        System.out.println("Took " + (System.nanoTime() - t) / 1000000 + " ms to solve file");
    }

    public static void main(String[] args) throws FileNotFoundException {
        long t = System.nanoTime();
        WordLadder wordLadder = new WordLadder("dictionary.txt");
        wordLadder.solveFile("input.txt");
        System.out.println("Total time: " + (System.nanoTime() - t) / 1e6 + " ms");
    }
}
