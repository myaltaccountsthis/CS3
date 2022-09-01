import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordLadder {
    private ArrayList<String> dictionary;

    public WordLadder(String dictionaryFile) throws FileNotFoundException {
        dictionary = new ArrayList<>();
        Scanner input = new Scanner(new File(dictionaryFile));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            dictionary.add(line.toLowerCase());
        }
        input.close();
    }

    private String getErrorMessage(String start, String end) {
        return "No ladder between " + start + " and " + end;
    }

    private String getSuccessMessage(Stack<String> stack) {
        return "Found a ladder! >>> " + stack;
    }

    /**
     * Return all words that differ by one letter from {@link String word}, if they are not in {@link ArrayList<String> ignoreList}
     * @param word
     * @return
     */
    private Queue<String> getAdjacentWords(String word, Collection<String> validWords) {
        Queue<String> words = new LinkedList<>();
        // third optimization, the fastest way to get all adjacent chars (26 * word.length(), e.g. 130 for 5 letter word)
        // compare to looping through all in validWords and checking each one's character difference (e.g. 8.4k for 5 letter word)
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                // don't need to check if s.equals(word) bc validWords.contains(word) will return false
                String s = word.substring(0, i) + c + word.substring(i + 1);
                if (validWords.contains(s))
                    words.offer(s);
            }
        }
        return words;
    }

    public Stack<String> solve(String start, String end) {
        if (start.length() != end.length())
            return null;

        Collection<String> validWords = new HashSet<>();
        HashMap<String, Integer> wordsRequired = new HashMap<>();
        Queue<Stack<String>> queue = new LinkedList<>();

        { // setup
            for (String s : dictionary)
                if (s.length() == start.length())
                    validWords.add(s);
            if (!validWords.contains(start) || !validWords.contains(end))
                return null;
            if (start.equals(end))
                return new Stack<>() {{ push(start); }};
            Queue<String> adjacent = getAdjacentWords(start, validWords);
            while (!adjacent.isEmpty()) {
                Stack<String> s = new Stack<>();
                s.push(start);
                s.push(adjacent.poll());
                queue.offer(s);
            }
        }

        Stack<String> shortest = null;
        while (!queue.isEmpty()) {
            Stack<String> stack = queue.poll();
            validWords.remove(stack.peek());
            if (shortest != null && stack.size() >= shortest.size())
                continue;
            if (stack.peek().equals(end)) {
                // first optimization, check if this stack is shorter bc if it is >= then it cannot be shorter
                if (shortest == null || stack.size() < shortest.size())
                    shortest = stack;
                if (shortest.size() <= 2)
                    return shortest;
            }
            Queue<String> adjacent = getAdjacentWords(stack.peek(), validWords);
            while (!adjacent.isEmpty()) {
                Stack<String> s = new Stack<>() {{ addAll(stack); }};
                s.push(adjacent.poll());
                // second optimization, check if the number of words to get to s.peek() is shorter than the recorded shortest value, or Integer.MAX_VALUE if there is none
                if (s.size() < wordsRequired.getOrDefault(s.peek(), Integer.MAX_VALUE)) {
                    wordsRequired.put(s.peek(), s.size());
                    queue.offer(s);
                }
            }
        }

        return shortest;
    }

    public void solveFile(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));
        long t = System.currentTimeMillis();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner scanner = new Scanner(line);
            String start = scanner.next(), end = scanner.next();
            Stack<String> solution = solve(start, end);
            System.out.println(solution == null ? getErrorMessage(start, end) : getSuccessMessage(solution));
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
