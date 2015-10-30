import java.util.Stack;

/**
 * Created by schandramouli on 10/30/15.
 */
public class StackSorting {
    // Objective is to sort a stack (with biggest element on top)
    // Constrains: Cannot use more than 1 temp stack, no arrays or other DS
    public static void main(String[] args) {
        Stack<Integer> intStack = new Stack<>();
        // push them in reverse
        for (int i = 10; i > 0; i--) {
            intStack.push(i);
        }
        for (Integer i : intStack) {
            System.out.println(i);
        }
    }
}
