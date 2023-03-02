import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitInputStream {
    private final FileInputStream input;
    private int numBits;
    private int data;
    private static final int DATA_SIZE = 8;
    private static final int DATA_SIZE_NUMBER = 1 << DATA_SIZE;

    public BitInputStream(String file) {
        try {
            input = new FileInputStream(file);
            numBits = 0;
            data = 0;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int readBit() {
        if (numBits == 0) {
            readData();
        }
        if (data == -1)
            return -1;
        data <<= 1;
        int bit = data & DATA_SIZE_NUMBER;
        numBits--;
        return bit >>> DATA_SIZE;
    }

    private void readData() {
        try {
            data = input.read();
            numBits = DATA_SIZE;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // test bit reader
        BitInputStream bitInputStream = new BitInputStream("happy hip hop.txt");
        while (true) {
            int bit = bitInputStream.readBit();
            System.out.println(bit);
            if (bit == -1) {
                break;
            }
        }
        bitInputStream.close();
    }
}
