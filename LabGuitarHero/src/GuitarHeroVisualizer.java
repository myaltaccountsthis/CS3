import java.util.HashMap;

public class GuitarHeroVisualizer {

    public static void main(String[] args) {
        HashMap<Character, GuitarString> strings = new HashMap<>();
        char[] chars = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ".toCharArray();
        for (int i = 0; i < chars.length; i++) {
            strings.put(chars[i], new GuitarString(110 * Math.pow(2, i / 12.0)));
        }

        final int interval = 1000;

        StdDraw.setPenRadius(1.0 / interval);
        StdDraw.enableDoubleBuffering();

        double[] samples = new double[interval];
        int n = 0;
        // the main input loop
        while (true) {

            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {

                // the user types this character
                char key = StdDraw.nextKeyTyped();

                if (strings.containsKey(key))
                    strings.get(key).pluck();
            }

            // compute the superposition of the samples
            double sample = 0.0;
            for (GuitarString string : strings.values()) {
                sample += string.sample();
                string.tic();
            }

            samples[n] = sample;
            n++;
            if (n == interval) {
                StdDraw.clear();
                for (int i = 0; i < samples.length; i++) {
                    StdDraw.point((double) i / interval, .5 + samples[i] * 1);
                }
                StdDraw.show();
                n = 0;
            }

            // send the result to standard audio
            StdAudio.play(sample);
        }
    }

}