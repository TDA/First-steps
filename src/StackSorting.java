import java.util.Arrays;
import java.util.Stack;

/**
 * Created by schandramouli on 10/30/15.
 */
public class StackSorting {
    // Objective is to sort a stack (with biggest element on top)
    // Constrains: Cannot use more than 1 temp stack, no arrays or other DS
    // temp variables allowed :O
    public static void main(String[] args) {
        Stack<Integer> intStack = new Stack<>();
        Stack<Integer> tempStack = new Stack<>();
        // push them in reverse
        /*for (int i = 10; i > 0; i--) {
            intStack.push(i);
        }*/
        intStack.addAll(Arrays.asList(4, 1, 5, 2, 3));
        for (Integer i : intStack) {
            System.out.println(i);
        }
        // lets try with one stack, using the temp stack when needed,
        // bubble sort maybe?
        System.out.println("-------------------------------------");
        int size = intStack.size();
        //System.out.println(size);
        // for loop to cover each element, so basically bubble sort here
        for (int k = 0; k < size; k++) {
            while (!intStack.isEmpty()) {
                int temp;
                if (tempStack.isEmpty()) {
                    temp = intStack.pop();
                    if (!intStack.isEmpty() && intStack.peek() > temp) {
                        // we need to switch these
                        tempStack.push(intStack.pop());
                        tempStack.push(temp);
                    } else if (!intStack.isEmpty() && intStack.peek() <= temp) {
                        // push in order?
                        tempStack.push(temp);
                        tempStack.push(intStack.pop());
                    } else {
                        tempStack.push(temp);
                    }
                } else {
                    // compare with tempstack, and swap them if needed.
                    temp = tempStack.peek();
                    if (!intStack.isEmpty() && intStack.peek() > temp) {
                        // we need to swap these
                        tempStack.pop();
                        tempStack.push(intStack.pop());
                        tempStack.push(temp);
                    } else if (!intStack.isEmpty() && intStack.peek() <= temp) {
                        // push in order?
                        tempStack.push(intStack.pop());
                    }
                }
            }
            // copy tempstack to intstack, and reset tempstack
            intStack = (Stack) tempStack.clone();
            tempStack.clear();
        }
        for (Integer i : intStack) {
            System.out.println(i);
        }
    }
}
