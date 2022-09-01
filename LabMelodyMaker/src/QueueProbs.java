import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QueueProbs {
    public Queue<Integer> evenFirst(Queue<Integer> nums) {
        Queue<Integer> queue = new LinkedList<>();

        int size = nums.size();
        for (int i = 0; i < size; i++) {
            int num = nums.poll();
            if (num % 2 == 0)
                queue.offer(num);
            else
                nums.offer(num);
        }
        while (!nums.isEmpty())
            queue.offer(nums.poll());
        return queue;
    }

    public boolean numPalindrome(Queue<Integer> nums) {
        Stack<Integer> stack = new Stack<>();
        int size = nums.size();
        for (int i = 0; i < size; i++) {
            Integer num = nums.poll();
            stack.push(num);
            nums.offer(num);
        }
        for (int i = 0; i < size; i++) {
            if (!stack.pop().equals(nums.poll()))
                return false;
        }
        return true;
    }

    public Stack<Integer> getPrimes(int n) {
        Stack<Integer> primes = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 2; i <= n; i++)
            queue.offer(i);
        while (!queue.isEmpty()) {
            Integer num = queue.poll();
            primes.push(num);
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                if (queue.peek() % num == 0)
                    queue.poll();
                else
                    queue.offer(queue.poll());
            }
        }
        return primes;
    }
}
