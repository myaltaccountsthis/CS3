import java.io.*;
import java.util.*;

class PrefixTree {
	/*
	public static final HashMap<String, Character> replacements = new HashMap<>();
	public static final HashMap<Character, String> substitutes = new HashMap<>();

	static {
		replacements.put("QU", 'Q');

		for (String s : replacements.keySet()) {
			substitutes.put(replacements.get(s), s);
		}
	}

	 */

	private final PrefixNode[] nodes;

	public PrefixTree() {
		nodes = new PrefixNode[26];
	}

	public void add(String word) {
		assert !word.isEmpty() : "Word cannot be empty";
		char[] chars = word.toCharArray();
		PrefixNode current = null;
		for (int i = 0; i < word.length(); i++) {
			// detect QU, maybe change this later to work for any strings (HashMap where [key] word = [value] char, e.g. ["QU"] = 'Q')
			char c = chars[i];
			/*
			for (String toReplace : replacements.keySet()) {
				boolean success = i + toReplace.length() < word.length();
				if (success) {
					for (int j = i; j < i + toReplace.length(); j++) {
						if (chars[j] != toReplace.charAt(j - i)) {
							success = false;
							break;
						}
					}
				}
				if (success) {
					i += toReplace.length() - 1;
					break;
				}
			}
			 */
			if (c == 'Q' && i < word.length() - 1 && word.charAt(i + 1) == 'U')
				i++;
			// if it is the first one, then add it to nodeIndex. the array has to exist bc char cannot be empty (unless this uses strings but strings are slow)
			if (current == null) {
				int nodeIndex = c - 'A';
				if (nodes[nodeIndex] == null)
					nodes[nodeIndex] = new PrefixNode(c);
				current = nodes[nodeIndex];
			}
			else
				current = current.getOrMake(c);
		}
		current.setWord();
	}

	public boolean contains(String word) {
		if (word.isEmpty())
			return false;
		char[] chars = word.toCharArray();
		PrefixNode current = null;
		for (int i = 0; i < word.length(); i++) {
			if (current == null)
				current = getNode(chars[0]);
			else
				current = current.get(chars[i]);
			if (current == null)
				return false;
			if (chars[i] == 'Q') {
				i++;
			}
		}
		return current.isWord;
	}

	public PrefixNode getNode(char c) {
		return nodes[c - 'A'];
	}

	static class PrefixNode {
		private final PrefixNode[] children = new PrefixNode[26];
		private final char value;
		private int size = 0;
		private boolean isWord = false;

		public PrefixNode(char value) {
			this.value = value;
		}

		public PrefixNode getOrMake(char c) {
			if (get(c) == null) {
				children[c - 'A'] = new PrefixNode(c);
				size++;
			}
			return get(c);
		}

		public PrefixNode get(char c) {
			return children[c - 'A'];
		}

		public boolean hasNext() {
			return size != 0;
		}

		@Override
		public String toString() {
			return "PrefixNode{value=" + value + ",size=" + size + "}";
		}

		public String getChildren() {
			StringBuilder sb = new StringBuilder().append('[');
			for (PrefixNode node : children) {
				if (node != null) {
					if (sb.length() > 1)
						sb.append(", ");
					sb.append(node);
				}
			}
			return sb.append(']').toString();
		}

		public void setWord() {
			isWord = true;
		}
	}
}

public class BoggleSolver
{
	private final PrefixTree prefixManager;

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName)
	{
		long t = System.nanoTime();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dictionaryName));
			prefixManager = new PrefixTree();
			reader.lines().forEach(prefixManager::add);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Load time: " + (System.nanoTime() - t) / 1e6);
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board)
	{
		Set<String> words = new HashSet<>();
		for (int row = 0; row < board.rows(); row++) {
			for (int col = 0; col < board.cols(); col++) {
				getAllValidWordsHelper(board, new HashSet<>(), words, new StringBuilder(), row, col, prefixManager.getNode(board.getLetter(row, col)));
			}
		}
		return words;
	}

	private void getAllValidWordsHelper(BoggleBoard board, Set<Integer> used, Set<String> words, StringBuilder currentString, int row, int col, PrefixTree.PrefixNode prefixNode) {
		// base cases. if full or if no word follows this path (prefixNode == null)
		if (used.size() == board.rows() * board.cols())
			return;
		if (prefixNode == null)
			return;

		// check this letter and push
		char letter = board.getLetter(row, col);
		int s = 1;
		currentString.append(letter);
		if (letter == 'Q') {
			currentString.append('U');
			s++;
		}
		used.add(row * board.cols() + col);

		String word = currentString.toString();
		if (word.length() > 2 && prefixManager.contains(word)) {
			words.add(word);
		}

		Iterable<Integer> adjacentIndices = getAdjacentLetters(board, row, col);
		for (int i : adjacentIndices) {
			if (!used.contains(i)) {
				int r = i / board.cols();
				int c = i % board.cols();
				if (prefixNode.hasNext()) {
					// continue recursive search
					getAllValidWordsHelper(board, used, words, currentString, r, c, prefixNode.get(board.getLetter(r, c)));
				}
			}
		}

		// pop after for backtracking
		currentString.delete(currentString.length() - s, currentString.length());
		used.remove(row * board.cols() + col);
	}

	/**
	 * Helper method to get all adjacent letter indices
	 * @param board the board
	 * @param row origin row
	 * @param col origin col
	 * @return Iterable object of adjacent in-bounds indices
	 */
	private Iterable<Integer> getAdjacentLetters(BoggleBoard board, int row, int col) {
		List<Integer> list = new ArrayList<>();
		int lowR = Math.max(0, row - 1), highR = Math.min(row + 1, board.rows() - 1);
		int lowC = Math.max(0, col - 1), highC = Math.min(col + 1, board.cols() - 1);
		for (int r = lowR; r <= highR; r++) {
			for (int c = lowC; c <= highC; c++) {
				list.add(r * board.cols() + c);
			}
		}
		return list;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		if (prefixManager.contains(word)) {
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
		BoggleBoard  board  = new BoggleBoard(PATH + "board-q.txt");
		long t = System.nanoTime();
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

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