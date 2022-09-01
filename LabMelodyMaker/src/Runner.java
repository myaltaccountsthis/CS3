import java.util.LinkedList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        QueueProbs queueProbs = new QueueProbs();

        System.out.println(queueProbs.evenFirst(new LinkedList<>(List.of(3, 5, 4, 17, 6, 83, 1, 84, 16, 37))));
        System.out.println(queueProbs.numPalindrome(new LinkedList<>(List.of(3, 8, 17, 9, 17, 8, 3))));
        System.out.println(queueProbs.numPalindrome(new LinkedList<>(List.of(3, 10, 17, 9, 17, 8, 3))));
        System.out.println(queueProbs.getPrimes(200));
    }
}
