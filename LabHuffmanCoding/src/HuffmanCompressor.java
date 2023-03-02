import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class HuffmanCompressor {
    private static String getFilePrefix(String fileName) {
        int lastSlash = fileName.lastIndexOf('\\');
        if (lastSlash < 0)
            lastSlash = 0;
        else
            lastSlash++;
        return fileName.substring(lastSlash, fileName.lastIndexOf('.'));
    }

    public static void compress(String fileName) {
        try {
            List<String> lines = new BufferedReader(new FileReader(fileName)).lines().toList();
            int[] charFrequencies = new int[256];
            for (String line : lines) {
                for (char c : line.toCharArray())
                    charFrequencies[c]++;
            }
            if (lines.size() > 1) {
                charFrequencies['\n'] = lines.size() - 1;
            }
            String filePrefix = getFilePrefix(fileName);
            HuffmanTree tree = new HuffmanTree(charFrequencies);
            tree.write(filePrefix + ".code");
            BitOutputStream output = new BitOutputStream(filePrefix + ".short");
            // source of slowness
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                for (char c : line.toCharArray()) {
                    writeEncoding(output, tree.getEncoding(c));
                }
                if (i < lines.size() - 1) {
                    writeEncoding(output, tree.getEncoding('\n'));
                }
            }
            writeEncoding(output, tree.getEncoding(256));
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeEncoding(BitOutputStream output, String encoding) {
        for (char c : encoding.toCharArray()) {
            output.writeBit(c - '0');
        }
    }

    public static void expand(String codeFile, String fileName) {
        String filePrefix = getFilePrefix(fileName);
        new HuffmanTree(codeFile).decode(new BitInputStream(fileName), filePrefix + ".new");
    }

    public static void compressAndExpand(String filePrefix) {
        compress(filePrefix + ".txt");
        expand(filePrefix + ".code", filePrefix + ".short");
    }

    public static void compressLargestJavaFile() {
        List<String> javaFiles = new LinkedList<>();
        recursiveHelper(fileName -> {
            if (fileName.endsWith(".java")) {
                javaFiles.add(fileName);
            }
        }, new File(System.getProperty("user.home") + "/Desktop"));
        System.out.println("Found " + javaFiles.size() + " files");
        List<String> sorted = javaFiles.stream().sorted(Comparator.comparingLong(fileName -> new File((String) fileName).length()).reversed()).toList();
        if (sorted.isEmpty()) {
            System.out.println("You are not a real CS student");
            return;
        }
        String fileName = sorted.get(1);
        System.out.println("Found " + fileName);
        compress(fileName);
        String filePrefix = getFilePrefix(fileName);
        expand(filePrefix + ".code", filePrefix + ".short");
    }
    private static void recursiveHelper(Consumer<String> function, File file) {
        if (file.isFile()) {
            function.accept(file.getAbsolutePath());
            return;
        }
        if (file.isDirectory()) {
            for (File children : file.listFiles()) {
                recursiveHelper(function, children);
            }
        }
    }

    public static void main(String[] args) {
        //compressLargestJavaFile();

        String filePrefix = "hamlet";
        compressAndExpand(filePrefix);

    }
}
