import java.util.Arrays;
import java.util.Comparator;

public class Searcher {
    private Term[] terms;
    private Term key;
    private Comparator<Term> comp;

    private Searcher(Term[] terms, Term key, Comparator<Term> comp) {
        this.terms = terms;
        this.key = key;
        this.comp = comp;
    }

    private int binarySearchFirst(int left, int right) {
        if (left > right)
            return left;
        int middle = (left + right) / 2;
        int comparison = comp.compare(key, terms[middle]);
        if (comparison <= 0) {
            return binarySearchFirst(left, middle - 1);
        }
        else {
            return binarySearchFirst(middle + 1, right);
        }
    }

    private int binarySearchLast(int left, int right) {
        if (left > right)
            return right;
        int middle = (left + right) / 2;
        int comparison = comp.compare(key, terms[middle]);
        if (comparison < 0) {
            return binarySearchLast(left, middle - 1);
        }
        else {
            return binarySearchLast(middle + 1, right);
        }
    }

    public static int firstIndexOf(Term[] a, Term key, Comparator<Term> comp) {
        int i = new Searcher(a, key, comp).binarySearchFirst(0, a.length - 1);
        return comp.compare(a[i], key) == 0 ? i : -1;
    }

    public static int lastIndexOf(Term[] a, Term key, Comparator<Term> comp) {
        int i = new Searcher(a, key, comp).binarySearchLast(0, a.length - 1);
        return comp.compare(a[i], key) == 0 ? i : -1;
    }

    public static void main(String[] args) {
        Term[] terms = new Term[] { new Term("cow", 10), new Term("pig", 15), new Term("bee", 8), new Term("horse", 12), new Term("horseshoe", 18), new Term("beehive", 12) };
        Term key = new Term("horse", 12);
        Comparator<Term>[] comparators = new Comparator[] { Comparator.naturalOrder(), Term.byPrefixOrder(5), Term.byReverseWeightOrder() };
        Arrays.stream(comparators).forEach(comparator -> {
            Arrays.sort(terms, comparator);
            System.out.println(Arrays.toString(terms));
            System.out.println(firstIndexOf(terms, key, comparator));
            System.out.println(lastIndexOf(terms, key, comparator));
            System.out.println();
        });
        // should print:
        /*
        3
        3

        3
        4

        2
        3
         */
    }
}
