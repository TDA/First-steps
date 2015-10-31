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

        for (Integer i : intStack) {
            System.out.println(i);
        }
        // lets try with one stack, using the temp stack when needed,
        // bubble sort maybe?
        while (! intStack.isEmpty()){
            int temp = intStack.pop();
            // check if lesser than next elt
            if (intStack.peek() != null && intStack.peek() > temp) {
                // we need to switch these
                tempStack.push(temp);
                tempStack.push(intStack.pop());
            }
            /*if (tempStack.isEmpty()) {
                tempStack.push(intStack.pop());
            } else {

            }*/
        }
        for (Integer i : tempStack) {
            System.out.println(i);
        }
    }
}
