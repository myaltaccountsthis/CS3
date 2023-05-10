import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Thievery {
    private int[] weights;
    private int[] values;

    public Thievery(int[] weights, int[] values) {
        this.weights = weights;
        this.values = values;
    }

    public int solve(int weightLimit) {
        // cache: [weightLeft] = (value)
        Map<State, Integer> cache = new HashMap<>();
        int n = solveHelper(cache, weightLimit, 0);
        return n;
    }
    private int solveHelper(Map<State, Integer> cache, int weightLeft, int i) {
        if (weightLeft < 0)
            return -1;
        if (i >= weights.length || weightLeft == 0)
            return 0;
        State currentState = new State(weightLeft, i);
        if (cache.containsKey(currentState)) {
            return cache.get(currentState);
        }
        int a = solveHelper(cache, weightLeft, i + 1), b = solveHelper(cache, weightLeft - weights[i], i + 1);
        if (b == -1) {
            cache.put(currentState, a);
            return a;
        }
        int max = Math.max(a, b + values[i]);
        cache.put(currentState, max);
        return max;
    }

    public static int thievery(int[] weights, int[] values, int weightLimit) {
        long t = System.nanoTime();
        int n = new Thievery(weights, values).solve(weightLimit);
        System.out.println((System.nanoTime() - t) / 1e6 + "ms");
        return n;
    }

    public static void main(String[] args) {
        int[] weights = {6, 1, 2, 5, 4, 3};
        int[] values = {10, 5, 7, 12, 8, 6};
        int weightLimit = 10;
        for (int i = 1; i < weightLimit; i++) {
            int finalI = i;
            System.out.println(thievery(weights, values, finalI));
        }
        System.out.println(thievery(weights, values, weightLimit));
    }

    static class State {
        final int weightLeft;
        final int i;

        public State(int weightLeft, int i) {
            this.weightLeft = weightLeft;
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return weightLeft == state.weightLeft && i == state.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(weightLeft, i);
        }

        @Override
        public String toString() {
            return "State{" +
                    "weightLeft=" + weightLeft +
                    ", i=" + i +
                    '}';
        }
    }
}
