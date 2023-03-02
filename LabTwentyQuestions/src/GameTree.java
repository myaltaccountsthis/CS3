import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A model for the game of 20 questions
 *
 * @author Rick Mercer
 */
public class GameTree
{
	private TreeNode treeRoot;
	private TreeNode currentNode;
	private String currentFileName;

	/**
	 * Constructor needed to create the game.
	 *
	 * @param fileName
	 *          this is the name of the file we need to import the game questions
	 *          and answers from.
	 */
	public GameTree(String fileName)
	{
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read file " + fileName + ": " + e);
		}
		if (input != null) {
			treeRoot = constructorHelper(input);
			currentNode = treeRoot;
			input.close();
			currentFileName = fileName;
			System.out.println(this);
		}
	}
	private TreeNode constructorHelper(Scanner input) {
		if (!input.hasNextLine())
			return null;
		String line = input.nextLine().trim();
		boolean isQuestion = line.endsWith("?");
		TreeNode current = new TreeNode(line, isQuestion);
		if (isQuestion) {
			current.left = constructorHelper(input);
			current.right = constructorHelper(input);
		}
		return current;
	}

	/*
	 * Add a new question and answer to the currentNode. If the current node has
	 * the answer chicken, theGame.add("Does it swim?", "goose"); should change
	 * that node like this:
	 */
	// -----------Feathers?-----------------Feathers?------
	// -------------/----\------------------/-------\------
	// ------- chicken  horse-----Does it swim?-----horse--
	// -----------------------------/------\---------------
	// --------------------------goose--chicken-----------
	/**
	 * @param newQ
	 *          The question to add where the old answer was.
	 * @param newA
	 *          The new Yes answer for the new question.
	 */
	public void add(String newQ, String newA)
	{
		if (!foundAnswer()) {
			System.out.println("Attempted to add question to a question");
			return;
		}
		currentNode.right = new TreeNode(currentNode.value, false);
		currentNode.isQuestion = true;
		currentNode.value = newQ;
		currentNode.left = new TreeNode(newA, false);
	}

	/**
	 * True if getCurrent() returns an answer rather than a question.
	 *
	 * @return False if the current node is an internal node rather than an answer
	 *         at a leaf.
	 */
	public boolean foundAnswer()
	{
		return !currentNode.isQuestion;
	}

	/**
	 * Return the data for the current node, which could be a question or an
	 * answer.  Current will change based on the users progress through the game.
	 *
	 * @return The current question or answer.
	 */
	public String getCurrent()
	{
		return currentNode.value;
	}

	/**
	 * Ask the game to update the current node by going left for Choice.yes or
	 * right for Choice.no Example code: theGame.playerSelected(Choice.Yes);
	 *
	 * @param yesOrNo
	 */
	public void playerSelected(Choice yesOrNo)
	{
		if (yesOrNo == Choice.Yes) {
			currentNode = currentNode.left;
		}
		else {
			currentNode = currentNode.right;
		}
	}

	/**
	 * Begin a game at the root of the tree. getCurrent should return the question
	 * at the root of this GameTree.
	 */
	public void reStart()
	{
		currentNode = treeRoot;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		toStringHelper(sb, treeRoot, 0);
		return sb.toString();
	}
	private void toStringHelper(StringBuilder sb, TreeNode node, int depth) {
		if (node == null)
			return;
		if (!node.isQuestion) {
			sb.append("- ".repeat(depth)).append(node.value).append('\n');
			return;
		}
		toStringHelper(sb, node.right, depth + 1);
		sb.append("- ".repeat(depth)).append(node.value).append('\n');
		toStringHelper(sb, node.left, depth + 1);
	}

	/**
	 * Overwrite the old file for this gameTree with the current state that may
	 * have new questions added since the game started.
	 *
	 */
	public void saveGame()
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(currentFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (writer != null) {
			saveGameHelper(writer, treeRoot);
			writer.close();
		}
	}
	private void saveGameHelper(PrintWriter writer, TreeNode node) {
		if (node == null)
			return;
		writer.println(node.value);
		saveGameHelper(writer, node.left);
		saveGameHelper(writer, node.right);
	}

	private class TreeNode {
		public boolean isQuestion;
		public String value;
		// left == true, right == false
		public TreeNode left;
		public TreeNode right;

		public TreeNode(String value, boolean isQuestion) {
			this.value = value;
			this.isQuestion = isQuestion;
		}
	}
}
