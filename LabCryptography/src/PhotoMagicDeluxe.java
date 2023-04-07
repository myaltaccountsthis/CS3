import java.util.HashMap;

public class PhotoMagicDeluxe {
    /**
     * Bits per character
     */
    private static final int BITS = 6;
    private static HashMap<Character, int[]> encodings;

    static {
        encodings = new HashMap<>();
        String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        for (int i = 0; i < base64.length(); i++) {
            int[] encoding = new int[BITS];
            int n = i;
            for (int j = BITS - 1; j >= 0; j--) {
                encoding[j] = n & 1;
                // doesn't matter if >> or >>>
                n >>= 1;
            }
            encodings.put(base64.charAt(i), encoding);
        }
    }

    public static String passwordToSeed(String password) {
        if (!password.matches("[0-9A-Za-z+/]*"))
            throw new RuntimeException("Password uses non-base64 characters");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            int[] encoding = encodings.get(password.charAt(i));
            for (int x : encoding) {
                sb.append(x);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        System.out.println(passwordToSeed("AB"));
        String seed = passwordToSeed("OPENSESAME");
//        System.out.println(seed);
        Picture original = new Picture("mystery.png");
        Picture transformed = PhotoMagic.transform(original, new LFSR(seed, 58));
        original.show();
        transformed.show();
        transformed.save("mystery_decrypted.png");
    }
}
