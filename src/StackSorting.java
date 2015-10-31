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
        intStack.addAll(Arrays.asList(4, 1, 5, 2, 3, 6 , 2));
        for (Integer i : intStack) {
            System.out.println(i);
        }
        // lets try with one stack, using the temp stack when needed,
        // bubble sort maybe?
        System.out.println("-------------------------------------");
        int size = intStack.size();
        //System.out.println(size);
        // for loop to cover each element, so basically insertion sort here
        while (! intStack.empty()) {
            //pop  an element
            int temp = intStack.pop();
            // compare the popped element and existing elements,
            // insert popped elt at right pos in tempstack
            while (! tempStack.empty() && temp < tempStack.peek()) {
                // < cuz we need bigger elts on top of stack
                // change to > if u want opposite
                intStack.push(tempStack.pop());
            }
            // insert the popped elt into tempstack
            tempStack.push(temp);
        }
        for (Integer i : tempStack) {
            System.out.println(i);
        }

        //System.out.println(tempStack.pop());
    }
}
