import java.awt.*;

public class PhotoMagic {
    public static Picture transform(Picture pic, LFSR lfsr) {
        Picture newPic = new Picture(pic.width(), pic.height());
        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                Color original = pic.get(i, j);
                int red = original.getRed() ^ lfsr.generate(8),
                        green = original.getGreen() ^ lfsr.generate(8),
                        blue = original.getBlue() ^ lfsr.generate(8);
                newPic.set(i, j, new Color(red, green, blue));
            }
        }
        return newPic;
    }

    public static void main(String[] args) {
        String fileName = "pipe.png";
        Picture pic = new Picture(fileName);
        pic.show();
        Picture transformed = transform(pic, new LFSR("01101000010100010000", 16));
        transformed.show();
        transformed.save("encrypted.png");
        Picture decrypted = transform(transformed, new LFSR("01101000010100010000", 16));
        decrypted.show();
        decrypted.save("decrypted.png");
    }
}
