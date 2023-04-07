public class Tester {
    private static int hash(int key) {
        return ((key << 2) / 19) % 11;
    }

    private static void test() {
        boolean[] arr = new boolean[11];
        int collisions = 0;
        for (int x : new int[] { 10, 100, 32, 45, 58, 126, 1, 29, 200, 400, 15 }) {
            int index = hash(x);
            if (!arr[index])
                arr[index] = true;
            else
                collisions++;
        }
        System.out.println(collisions); // results in 2 collisions
        // 2. O(1)
        // 3. O(n)
    }

    public static void main(String[] args) {
        test();
        /*
        long start = System.nanoTime();
        EmployeeDatabase database = new EmployeeDatabase();
        for (int i = 0; i < 30; i++) {
            database.put(i, new Employee(i + " "));
        }
        for (int i = 100; i < 200; i++) {
            database.put(i, new Employee(i * 100 + " "));
        }
        for (int i = 5; i < 100; i += 10) {
            database.put(i, new Employee(i * 2 + ""));
        }
        for (int i = 0; i < 100; i++) {
            // beyond 29 should print null
            System.out.println(database.get(i));
        }
        System.out.println((System.nanoTime() - start) / 1e6);
         */
    }
}
