import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Runner
{
	public static void main(String[] args) throws IOException
	{
		String fileName = "wiki.txt";
		
		Scanner in = new Scanner(new File(fileName));
		
		int N = in.nextInt();
		
		Term[] terms = new Term[N];
		
		for (int i = 0; i < N; i++) {
			long weight = in.nextLong();        // read the next weight
			String query = in.next();           // read the next query
			terms[i] = new Term(query, weight); // construct the term
		}

		// read in queries from standard input and print out the top k matching terms
		Scanner console = new Scanner(System.in);

		System.out.print("How many matching terms? ");
		int k = console.nextInt();

		Autocomplete autocomplete = new Autocomplete(terms);

		System.out.print("Word? ");
		String prefix = console.next(); //one word only, at the moment
		
		Term[] results = autocomplete.allMatches(prefix);
		
		for (int i = 0; i < Math.min(k, results.length); i++)
			System.out.println(results[i]);
	}
}