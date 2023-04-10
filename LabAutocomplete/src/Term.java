import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    public Term(String query, long weight) {
        this.query = query;
        this.weight = weight;
    }

    public static Comparator<Term> byReverseWeightOrder() {
        return (a, b) -> (int) Math.signum(b.weight - a.weight);
    }

    public static Comparator<Term> byPrefixOrder(int r) {
        return Comparator.comparing(a -> a.query.substring(0, Math.min(r, a.query.length())));
    }

    @Override
    public int compareTo(Term other) {
        return query.compareTo(other.query);
    }

    public String toString() {
        return "%d\t%s".formatted(weight, query);
    }

    public static void main(String[] args) {
        List<Term> terms = new ArrayList<>(Arrays.asList(new Term("cow", 10), new Term("pig", 15), new Term("bee", 8), new Term("horse", 12), new Term("horseshoe", 18)));
        terms.sort(Comparator.naturalOrder());
        System.out.println(terms);
        terms.sort(byReverseWeightOrder());
        System.out.println(terms);
        terms.sort(byPrefixOrder(4));
        System.out.println(terms);
        terms.sort(byPrefixOrder(6));
        System.out.println(terms);
    }
}
