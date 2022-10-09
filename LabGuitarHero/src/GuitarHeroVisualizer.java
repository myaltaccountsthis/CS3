import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GuitarHeroVisualizer {

    public static void main(String[] args) {
        HashMap<Character, GuitarString> strings = new HashMap<>();
        char[] chars = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ".toCharArray();
        for (int i = 0; i < chars.length; i++) {
            strings.put(chars[i], new GuitarString(110 * Math.pow(2, i / 12.0)));
        }

        final int interval = 2500;

        StdDraw.setPenRadius(1.0 / interval);
        StdDraw.setCanvasSize(1075, 512);
        StdDraw.enableDoubleBuffering();

        double[] samples = new double[interval];
        int n = 0;
        boolean mouseDown = false;
        int prevMouseIndex = -1;
        // the main input loop
        while (true) {

            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {

                // the user types this character
                char key = StdDraw.nextKeyTyped();

                if (strings.containsKey(key))
                    strings.get(key).pluck();
            }
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                int index = (int) ((x * 1075 - 4) / 28.8);
                if (!mouseDown || index != prevMouseIndex) {
                    mouseDown = true;
                    if (index >= 0 && index < chars.length)
                        strings.get(chars[index]).pluck();
                    prevMouseIndex = index;
                }
            }
            else
                mouseDown = false;

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
                    StdDraw.line((double) i / interval, .35 + samples[i] * .35, (double) (i + 1) / interval, .35 + samples[i + 1] * .35);
                    //StdDraw.point((double) i / interval, .35 + samples[i] * .7);
                }
                StdDraw.picture(0.5, 1 - (150 / 512.0 / 2), "keyboard.png", 1, 150 / 512.0);
                StdDraw.show();
                n = 0;
            }

            // send the result to standard audio
            StdAudio.play(sample);
        }
    }

}