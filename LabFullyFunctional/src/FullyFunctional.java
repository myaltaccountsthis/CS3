import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FullyFunctional {
    public static void main(String[] args) throws IOException {
        // 2
        System.out.println("Enter three names:");
        Scanner io = new Scanner(System.in);
        List<String> list = Stream.of(io.nextLine(), io.nextLine(), io.nextLine()).map(String::toUpperCase).collect(Collectors.toList());

        // 3
        System.out.println(list.stream().collect(Collectors.joining(", ")));

        // 4
        Integer[] arr = new Integer[] {6, 3, 7, 4, 1, 5, 9};
        Arrays.sort(arr, Comparator.reverseOrder());
        System.out.println(Arrays.toString(arr));

        // 5
        int n = 5;
        IntStream.range(0, n).forEach(i -> System.out.println("Hello!"));

        // 6
        Files.lines(Paths.get("funfile.txt")).forEach(System.out::println);

        // 7
        int a = 13, b = 15;
        System.out.println(a + " is " + (IntStream.range(2, a / 2).filter(i -> a % i == 0).count() > 0 ? "composite" : "prime"));
        System.out.println(b + " is " + (IntStream.range(2, b / 2).filter(i -> b % i == 0).count() > 0 ? "composite" : "prime"));

        // 8
        int m = 200;
        System.out.println(new Random().ints(0, 101).limit(m).boxed().collect(Collectors.toList()));

        // 9
        System.out.println(new Random().ints(0, 101).limit(m).distinct().boxed().collect(Collectors.toList()));

        // 10
        int[] ints = new int[] { 5, 3, 67, 24, 13, 43, 26, 37, 65, 62, 53 };
        System.out.println(Arrays.toString(Arrays.stream(ints).sorted().toArray()));

        // 11
        new Thread(() -> {
            while (true) {
                System.out.println("forever printing");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 12
        // int letterA = 'a';
        // return s.chars().filter(c -> c == letterA).count() * (n / s.length()) + s.substring(0, (int)(n % s.length())).chars().filter(c -> c == letterA).count();

        // 13
        String str = "Hi-There";
        // return str.chars().mapToObj(c -> (char) c).map(c -> "" + c + c).collect(Collectors.joining());

        // 14
        System.out.println(sumIf(Arrays.asList(1, 4, 5, 7, 4, 3, 2, 3, 5, 6), x -> x % 2 == 0));

        // 15
        str = "7abc11";
        // return Arrays.stream(str.split("\\D+")).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).sum();

        // 16
        Map<String, Integer> map = new HashMap<>();
        map.put("Computer", 2_000);
        map.put("Sandwich", 5);
        map.put("Coffee", 2);
        map.put("Car", 30_000);
        map.put("Pencil", 1);
        LinkedHashMap<String, Integer> linkedMap = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));
        System.out.println(linkedMap);
    }

    public static int sumIf(List<Integer> numbers, Predicate<Integer> condition) {
        return numbers.stream().filter(condition).mapToInt(i -> i).sum();
    }
}
