import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutputStream {
    private final FileOutputStream output;
    private int numBits;
    private int data;
    private static final int DATA_SIZE = 8;
    private static final int DATA_SIZE_NUMBER = 1 << DATA_SIZE;

    public BitOutputStream(String file) {
        try {
            output = new FileOutputStream(file);
            numBits = 0;
            data = 0;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBit(int bit) {
        data <<= 1;
        data |= bit;
        numBits++;
        if (numBits == DATA_SIZE) {
            writeData();
        }
    }

    private void writeData() {
        try {
            output.write(data);
            data = 0;
            numBits = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (numBits > 0) {
                data <<= DATA_SIZE - numBits;
                writeData();
            }
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // test bit reader
        BitInputStream bitInputStream = new BitInputStream("happy hip hop.txt");
        BitOutputStream bitOutputStream = new BitOutputStream("happy hip hop output.txt");
        while (true) {
            int bit = bitInputStream.readBit();
            if (bit == -1) {
                break;
            }
            bitOutputStream.writeBit(bit);
        }
        bitInputStream.close();
        bitOutputStream.close();
    }
}
