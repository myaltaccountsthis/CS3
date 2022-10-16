import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class WordLadder {
    private Collection<String> dictionary;

    public WordLadder(String dictionaryFile) throws FileNotFoundException {
        long t = System.nanoTime();
        dictionary = new ArrayList<>();
        Scanner input = new Scanner(new File(dictionaryFile));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            dictionary.add(line.toLowerCase());
        }
        input.close();
        System.out.println("Load time: " + (System.nanoTime() - t) / 1000000 + " ms");
    }

    private class Solver {
        private final String start, end;
        private boolean ran = false, success = false;
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
            if (success && solution != null)
                return "Found a ladder! >>> " + solution;
            return "No ladder between " + start + " and " + end;
        }

        /**
         * Return all words that differ by one letter from {@link String word}, if they are not in {@link ArrayList<String> ignoreList}
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

        private void onSuccess(Stack<String> s) {
            this.success = true;
            this.solution = s;
        }

        public Stack<String> solve() {
            assert !ran : "Solver already ran";
            ran = true;

            if (start.length() != end.length())
                return null;

            { // setup
                for (String s : dictionary)
                    if (s.length() == start.length())
                        validWords.add(s);
                if (!validWords.contains(start) || !validWords.contains(end))
                    return null;
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

            onSuccess(shortest);
            return shortest;
        }
    }

    public Solver solve(String start, String end) {
        Solver solver = new Solver(start, end);
        solver.solve();
        return solver;
    }

    public void solveFile(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));
        long t = System.currentTimeMillis();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner scanner = new Scanner(line);
            String start = scanner.next(), end = scanner.next();
            long solve = System.currentTimeMillis();
            Solver solver = solve(start, end);
            System.out.println((System.currentTimeMillis() - solve) + "\t" + solver.adjTime / 1000000 + "\t: " + solver.getMessage());
            scanner.close();
        }
        System.out.println("Took " + (System.currentTimeMillis() - t) + " ms");
        input.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordLadder wordLadder = new WordLadder("dictionary.txt");
        wordLadder.solveFile("input.txt");
    }
}
