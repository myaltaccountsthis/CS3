import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class DictionaryPrefix {
	private HashMap<String, DictionaryPrefix> subPrefixes;
	private String prefix;

	public DictionaryPrefix(Set<String> dictionary) {
		this(dictionary, "", dictionary);
	}

	public DictionaryPrefix(Set<String> dictionary, String newPrefix, Collection<String> parentWords) {
		this.prefix = newPrefix;
		this.subPrefixes = new HashMap<>();
		List<String> words = new ArrayList<>();
		for (String s : parentWords)
			if (s.startsWith(newPrefix))
				words.add(s);

		for (String word : words) {
			if (word.length() != newPrefix.length()) {
				String p = newPrefix + word.charAt(newPrefix.length());
				if (!subPrefixes.containsKey(p)) {
					subPrefixes.put(p, new DictionaryPrefix(dictionary, p, words));
				}
			}
		}
	}

	public DictionaryPrefix getSubPrefix(String s) {
		if (s.endsWith("QU")) {
			DictionaryPrefix p = subPrefixes.get(s.substring(0, s.length() - 1));
			if (p != null)
				return p.subPrefixes.get(s);
		}
		return subPrefixes.get(s);
	}

	public boolean hasNext() {
		return !subPrefixes.isEmpty();
	}
}

public class BoggleSolver
{
	private Set<String> dictionary;
	private DictionaryPrefix prefixManager;

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName)
	{
		dictionary = new HashSet<>();
		try {
			Scanner input = new Scanner(new File(dictionaryName));
			while (input.hasNextLine()) {
				dictionary.add(input.nextLine());
			}
			prefixManager = new DictionaryPrefix(dictionary);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board)
	{
		Set<String> words = new HashSet<>();
		for (int row = 0; row < board.rows(); row++) {
			for (int col = 0; col < board.cols(); col++) {
				getAllValidWordsHelper(board, new HashSet<>(), words, new Stack<>(), row, col, prefixManager);
			}
		}

		return words;
	}

	private void getAllValidWordsHelper(BoggleBoard board, Set<Integer> used, Set<String> words, Stack<String> currentLetters, int row, int col, DictionaryPrefix prefix) {
		if (currentLetters.size() == board.rows() * board.cols())
			return;

		if (prefix == null)
			return;

		Iterable<Integer> adjacentIndices = getAdjacentLetters(board, row, col);
		for (int i : adjacentIndices) {
			if (!used.contains(i)) {
				int r = i / board.cols();
				int c = i % board.cols();
				char letter = board.getLetter(r, c);
				String s = letter + "";
				if (letter == 'Q')
					s += "U";
				used.add(i);
				currentLetters.push(s);
				String word = concatStack(currentLetters);

				// my first optimization, not very good
//				boolean hasPrefix = false;
//				for (String w : dictionary) {
//					if (w.startsWith(word)) {
//						hasPrefix = true;
//						break;
//					}
//				}

				// second optimization, works very well but uses a lot of memory
				boolean hasPrefix = prefix.hasNext();

				if (dictionary.contains(word)) {
					words.add(word);
				}
				if (hasPrefix) {
					getAllValidWordsHelper(board, used, words, currentLetters, r, c, prefix.getSubPrefix(word));
//					getAllValidWordsHelper(board, used, words, currentLetters, r, c, null);
				}
				used.remove(i);
				currentLetters.pop();
			}
		}
	}

	private String concatStack(Stack<String> currentLetters) {
		StringBuilder sb = new StringBuilder();
		currentLetters.forEach(sb::append);
		return sb.toString();
	}

	private Iterable<Integer> getAdjacentLetters(BoggleBoard board, int row, int col) {
		List<Integer> list = new ArrayList<>();
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r >= 0 && r < board.rows() && c >= 0 && c < board.cols()) {
					list.add(r * board.cols() + c);
				}
			}
		}
		return list;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		if (dictionary.contains(word)) {
			int length = word.length();
			if (length >= 8)
				return 11;
			return switch (length) {
				case 7 -> 5;
				case 6 -> 3;
				case 5 -> 2;
				case 3, 4 -> 1;
				default -> 0;
			};
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("WORKING");

		final String PATH   = "./data/";
		BoggleBoard  board  = new BoggleBoard(PATH + "board-points26539.txt");
		long t = System.nanoTime();
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-yawl.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); //should print 84
		System.out.println("Time = " + (System.nanoTime() - t) / 1e6 + " ms");
		System.out.println("Memory = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(2, 20) + " MB");

		//new BoggleGame(4, 4);
	}

}
