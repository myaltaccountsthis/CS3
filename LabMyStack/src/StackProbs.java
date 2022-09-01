import java.util.Stack;

public class StackProbs {
    public Stack<Integer> doubleUp(Stack<Integer> nums) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (!nums.empty())
            stack1.push(nums.pop());
        while (!stack1.empty()) {
            Integer num = stack1.pop();
            stack2.push(num);
            stack2.push(num);
        }
        return stack2;
    }

    public Stack<Integer> posAndNeg(Stack<Integer> nums) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        int size = nums.size();
        for (int i = 0; i < size; i++) {
            if (nums.peek() < 0)
                stack1.push(nums.pop());
            else
                stack2.push(nums.pop());
        }

        while (!stack2.empty())
            stack1.push(stack2.pop());

        return stack1;
    }

    public Stack<Integer> shiftByN(Stack<Integer> nums, int n) {
        // contains numbers to shift and loop back
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        int shiftBy = n % nums.size();
        if (shiftBy < 0)
            shiftBy += nums.size();

        int size = nums.size();
        for (int i = 0; i < size; i++) {
            // push {shiftBy} elements into stack1
            if (i >= size - shiftBy)
                stack1.push(nums.pop());
            else
                stack2.push(nums.pop());
        }

        // flush stack 1 then stack 2 into nums
        while (!stack2.empty())
            nums.push(stack2.pop());
        while (!stack1.empty())
            nums.push(stack1.pop());

        return nums;
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public String reverseString(String str) {
        Stack<String> words = new Stack<>();
        Stack<Character> vowels = new Stack<>();

        String wordSegment = "";
        for (int i = 0; i < str.length(); i++) {
            if (isVowel(str.charAt(i))) {
                words.push(wordSegment);
                wordSegment = "";
                vowels.push(str.charAt(i));
            }
            else
                wordSegment += str.charAt(i);
        }
        if (!wordSegment.isEmpty())
            words.push(wordSegment);

        Stack<String> temp = new Stack<>();
        while (!words.empty())
            temp.push(words.pop());
        words = temp;

        StringBuilder newStr = new StringBuilder();
        int size = vowels.size();
        for (int i = 0; i < size; i++) {
            newStr.append(words.pop()).append(vowels.pop());
        }
        // words.size() may be 0 or 1 greater than vowels.size()
        if (!words.empty())
            newStr.append(words.pop());

        return newStr.toString();
    }

    public boolean bracketBalance(String s) {
        Stack<Character> brackets = new Stack<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(', '[' -> brackets.push(c);
                case ')', ']' -> {
                    if (brackets.empty() || brackets.peek() != (c == ')' ? '(' : '[')) {
                        return false;
                    }
                    brackets.pop();
                }
            }
        }
        return brackets.empty();
    }
}
