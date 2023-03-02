import com.sun.source.tree.Tree;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class HuffmanTree {
    public static final int PSEUDO_EOF = 256;

    private Node overallRoot;
    private String[] encodings;
    private String eofEncoding;

    public HuffmanTree(int[] count) {
        // smallest frequency first
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0)
                queue.offer(new Node(i, count[i]));
        }
        queue.offer(new Node(PSEUDO_EOF, 1));
        while (queue.size() >= 2) {
            Node a = queue.poll();
            Node b = queue.poll();
            Node combined = new Node(-1, a.frequency + b.frequency, a, b);
            queue.offer(combined);
        }
        overallRoot = queue.poll();
        createEncodings();
    }

    public HuffmanTree(String codeFile) {
        try {
            Scanner input = new Scanner(new File(codeFile));
            while (input.hasNextLine()) {
                int character = Integer.parseInt(input.nextLine());
                String encoding = input.nextLine();
                overallRoot = createNode(overallRoot, character, encoding);
            }
            input.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        createEncodings();
    }
    private Node createNode(Node node, int character, String encoding) {
        // frequency does not matter for loading from code file
        if (encoding.isEmpty())
            return new Node(character, 0);
        if (node == null)
            node = new Node(-1, 0);
        boolean right = encoding.charAt(0) == '1';
        if (right)
            node.right = createNode(node.right, character, encoding.substring(1));
        else
            node.left = createNode(node.left, character, encoding.substring(1));
        return node;
    }

    private void createEncodings() {
        encodings = new String[256];
        createEncodingsHelper(overallRoot, new Stack<>());
    }
    private void createEncodingsHelper(Node node, Stack<Boolean> prefix) {
        if (node == null)
            return;
        if (node.isLeaf()) {
            if (node.character == PSEUDO_EOF) {
                eofEncoding = prefixStackToString(prefix);
                return;
            }
            encodings[node.character] = prefixStackToString(prefix);
            return;
        }
        prefix.add(false);
        createEncodingsHelper(node.left, prefix);
        prefix.pop();
        prefix.add(true);
        createEncodingsHelper(node.right, prefix);
        prefix.pop();
    }

    /**
     * Return the encoding for the char given as an int
     */
    public String getEncoding(int c) {
        if (c >= encodings.length)
            return eofEncoding;
        return encodings[c];
    }

    public void write(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writeHelper(writer, overallRoot, new Stack<>());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeHelper(PrintWriter writer, Node node, Stack<Boolean> prefix) {
        if (node == null)
            return;
        if (node.isLeaf()) {
            writer.println(node.character);
            writer.println(prefixStackToString(prefix));
            return;
        }
        prefix.add(false);
        writeHelper(writer, node.left, prefix);
        prefix.pop();
        prefix.add(true);
        writeHelper(writer, node.right, prefix);
        prefix.pop();
    }
    private String prefixStackToString(Stack<Boolean> stack) {
        StringBuilder sb = new StringBuilder();
        stack.forEach(bool -> sb.append(bool ? 1 : 0));
        return sb.toString();
    }

    public void decode(BitInputStream in, String outputFile) {
        try {
            PrintWriter writer = new PrintWriter(outputFile);
            Node currentNode = overallRoot;
            while (true) {
                int bit = in.readBit();
                // uh oh this should not be reached
                if (bit == -1)
                    throw new RuntimeException("Bit is -1");
                boolean right = bit == 1;
                if (right)
                    currentNode = currentNode.right;
                else
                    currentNode = currentNode.left;
                if (currentNode.isLeaf()) {
                    int character = currentNode.character;
                    // good, break when reached pseudo eof
                    if (character == PSEUDO_EOF)
                        break;
                    writer.print((char) character);
                    currentNode = overallRoot;
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //System.out.println(Arrays.toString(new HuffmanTree("output.txt").encodings));
        TreePrinter.printTree(new HuffmanTree("happy hip hop.code").overallRoot);
        /*
        int[] arr = new int[256];
        arr['y'] = 2;
        arr['c'] = 1;
        arr['x'] = 1;
        arr['a'] = 3;
        arr['b'] = 3;
        new HuffmanTree(arr).write("output.txt");
         */
    }
}
