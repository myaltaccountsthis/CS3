import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Autocomplete {
    private Term[] terms;

    public Autocomplete(Term[] terms) {
        this.terms = terms;
        Arrays.sort(terms, Comparator.naturalOrder());
    }

    public Term[] allMatches(String prefix) {
        long t = System.nanoTime();
        Term prefixTerm = new Term(prefix, 0);
        int first = Searcher.firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length())), last = Searcher.lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));
        if (first == -1)
            return new Term[0];
        Term[] matches = new Term[last - first + 1];
        IntStream.range(0, matches.length).forEach(i -> matches[i] = terms[i + first]);
        Arrays.sort(matches, Term.byReverseWeightOrder());
//        System.out.printf("%.2fms%n", (System.nanoTime() - t) / 1e6);
        return matches;
    }

    public int numberOfMatches(String prefix) {
        Term prefixTerm = new Term(prefix, 0);
        int first = Searcher.firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length())), last = Searcher.lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));
        // don't have to check for -1 bc -1 becomes 0 (the desired result)
        return last - first + 1;
    }

    public static void printMatches(Term[] terms, int limit) {
        IntStream.range(0, Math.min(limit, terms.length)).forEach(i -> System.out.println(terms[i]));
    }

    public static void printMatches(Term[] terms) {
        printMatches(terms, 5);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("wiki.txt"));
        int N = input.nextInt();
        Term[] terms = new Term[N];
        IntStream.range(0, N).forEach(i -> {
            long weight = input.nextLong();
            String query = input.next();
            terms[i] = new Term(query, weight);
        });
        Autocomplete autocomplete = new Autocomplete(terms);
        Term[] searches = autocomplete.allMatches("auto");
        System.out.println("auto");
        printMatches(searches);
        searches = autocomplete.allMatches("comp");
        System.out.println("comp");
        printMatches(searches);
        searches = autocomplete.allMatches("the");
        System.out.println("the");
        printMatches(searches);
    }
}
