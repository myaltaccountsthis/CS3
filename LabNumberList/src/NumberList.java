import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class NumberList {
    private Integer[] list;
    private int size;

    public static void main(String[] args) {
        // advanced
        { // a, find the longest sum of consecutive primes under 1 million
            boolean[] primes = new boolean[1000000];
            Arrays.fill(primes, true);
            // sieve of eratosthenes
            int index;
            for (int i = 2; i < (int) Math.sqrt(primes.length); i++) {
                if (!primes[i])
                    continue;
                for (int j = 0; (index = i * (i + j)) < primes.length; j++) {
                    primes[index] = false;
                }
            }
            System.out.println("Found primes");

            NumberList list = new NumberList();
            int sum = 0;
            int longest = 0, longestSum = 0;
            NumberList longestList = new NumberList();
            index = 2;
            while (index < primes.length) {
                while (index < primes.length && !primes[index])
                    index++;
                list.add(index);
                sum += index;
                while (sum >= 1000000) {
                    sum -= list.remove(0);
                }
                if (list.size() < longest)
                    break;
                if (primes[sum]) {
                    longest = Math.max(list.size(), longest);
                    longestSum = sum;
                    while (longestList.size() > 0)
                        longestList.remove(0);
                    for (int i = 0; i < list.size(); i++)
                        longestList.add(list.get(i));
                }
                index++;
            }
            System.out.println(longest + ", " + longestSum + ", " + longestList);
        }
        { // b, find sum of all positive ints which cannot be written as the sum of two abundant numbers (sum(factors) > number)
            // numbers above 28213 can be written as sum of 2 abundant numbers
            NumberList abundantNumbers = new NumberList();
            int[] sumFactors = new int[28214];
            // start at 1 bc every number has 1 as a factor
            for (int factor = 1; factor < sumFactors.length / 2; factor++) {
                for (int i = 1; i < sumFactors.length; i++)
                    // numbers cannot include themselves as a factor
                    if (i % factor == 0 && i > factor)
                        sumFactors[i] += factor;
            }

            for (int i = 1; i < sumFactors.length; i++) {
                if (sumFactors[i] > i) {
                    abundantNumbers.add(i);
                }
            }

            boolean[] nums = new boolean[sumFactors.length];
            Arrays.fill(nums, true);
            for (int i = 0; i < abundantNumbers.size(); i++) {
                for (int j = i; j < abundantNumbers.size(); j++) {
                    if (abundantNumbers.get(i) + abundantNumbers.get(j) <= 28213) {
                        //if (nums[abundantNumbers.get(i) + abundantNumbers.get(j)])
                            //writer.println(abundantNumbers.get(i) + abundantNumbers.get(j));
                        nums[abundantNumbers.get(i) + abundantNumbers.get(j)] = false;
                    }
                    else
                        break;
                }
            }

            int sum = 0;
            NumberList list = new NumberList();
            for (int i = 1; i < sumFactors.length; i++) {
                if (nums[i]) {
                    sum += i;
                    list.add(i);
                }
            }
            System.out.println(sum + ", " + list);
        }
    }

    public NumberList() {
        this.list = new Integer[2];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(list[i]);
        }
        builder.append("]");
        return builder.toString();
    }

    private void doubleCapacity() {
        this.list = Arrays.copyOf(list, list.length * 2);
    }

    public void add(int index, Integer val) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        if (this.list.length == this.size) {
            doubleCapacity();
        }
        for (int i = this.size; i > index; i--) {
            this.list[i] = this.list[i - 1];
        }
        this.list[index] = val;
        this.size++;
    }

    public boolean add(Integer val) {
        this.add(this.size, val);
        return true;
    }

    public Integer get(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        return this.list[index];
    }

    public Integer set(int index, Integer val) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        Integer old = this.list[index];
        this.list[index] = val;
        return old;
    }

    public Integer remove(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();
        Integer old = this.list[index];
        for (int i = index; i < size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }
        this.size--;
        return old;
    }
}
