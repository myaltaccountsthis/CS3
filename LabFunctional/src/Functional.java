import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Functional {
    public static void main(String[] args) {
        // init
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<String> list = Arrays.asList("hello", "and", "goodbye");

        // 1
        System.out.println("Problem 1");
        nums.forEach(System.out::println);
        list.forEach(System.out::println);
        System.out.println();

        // 2
        System.out.println("Problem 2");
        nums.removeIf(n -> n % 2 == 0);
        System.out.println(nums);
        System.out.println();

        // 3
        nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.printf("Problem 3%n%d%n%n", nums.stream().filter(n -> n % 2 == 1).count());

        // 4
        System.out.println("Problem 4");
        list.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);
        System.out.println();

        // 5
        System.out.println("Problem 5");
        System.out.println(nums.stream().map(n -> n * 2).collect(Collectors.toList()));
        System.out.println();

        // 6
        // return nums.stream().map(n -> n * n + 10).filter(n -> !(n % 10 == 5 || n % 10 == 6)).collect(Collectors.toList());

        // 7
        List<Double> prices = Arrays.asList(11.25, 14.99, 13.29, 7.89, 3.49, 5.79);
        System.out.println("Problem 7");
        System.out.println(prices.stream().map(n -> n * 1.12).collect(Collectors.toList()));
        System.out.println();

        // 8
        System.out.println("Problem 8");
        System.out.println(Arrays.asList(1, 2, 3, 4, 5).stream().reduce((a, b) -> a + b).get());
        System.out.println();

        // 9
        List<Integer> costs = Arrays.asList(34, 5, 23, 4, 62, 2, 12, 36, 42, 18);
        System.out.println("Problem 9");
        System.out.println(costs.stream().map(n -> n * 1.12).mapToDouble(d -> d).sum());
        System.out.println();

        // 10
        System.out.println("Problem 10");
        System.out.println(costs.stream().max(Integer::compareTo).get());
        System.out.println();

        // 11
        JButton button = new JButton("Click here");
        JFrame frame = new JFrame("Button test"); //window to contain the button
        button.addActionListener(e -> System.out.println("Button Clicked!"));
        frame.setSize(200, 200); //set the size of the container window
        frame.add(button); //add button to the window
        frame.setVisible(true); //make frame visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 12
        List<Person> users = new ArrayList<>();
        users.add(new Person("Sarah",40));
        users.add(new Person("Peter",50));
        users.add(new Person("Lucy",60));
        users.add(new Person("Albert",20));
        users.add(new Person("Charlie",30));
        System.out.println("Problem 12");
        System.out.println(users.stream().mapToInt(p -> p.age).max().getAsInt());
    }

    static class Person
    {
        String name;
        int age;
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        @Override
        public String toString() {
            return this.name + ", " + this.age;
        }
        int getAge() { return this.age; }
    }
}
