import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WordBreak {
    // is it worth using memoization?
    public String wordBreak(String str, Set<String> dict) {
        if (str.isEmpty())
            return str;
        String found = null;
        for (String word : dict) {
            if (str.startsWith(word))
                found = word;
        }
        if (found == null)
            return null;
        String next = wordBreak(str.substring(found.length()), dict);
        if (next == null)
            return null;
        if (next.isEmpty()) {
            return found;
        }
        return found + " " + next;
    }


    public static void main(String[] args) {
        WordBreak w = new WordBreak();
        Set<String> dict = new HashSet<>(Arrays.asList("hello", "how", "are", "you", "today"));
        Main.trackMillis(() -> System.out.println(w.wordBreak("howareyou", dict))); //how are you
        Main.trackMillis(() -> System.out.println(w.wordBreak("todayhello", dict))); //today hello
        Main.trackMillis(() -> System.out.println(w.wordBreak("helloworld", dict))); //null (no solution)
    }
}
