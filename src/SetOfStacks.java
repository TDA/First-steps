import java.util.ArrayList;

/**
 * Created by schandramouli on 9/27/15.
 */

public class SetOfStacks {
    // This is only an integer implementation
    // Will eventually make it a Generic one
    int capacity = 10; // hold only ten items in any stack
    int currentCapacity = 0;
    ArrayList<Stack<Integer>> setOfStacks = new ArrayList<Stack<Integer>>();

    public SetOfStacks() {
        setOfStacks.add(0, new Stack<Integer>(capacity));
    }

    public void push(int data) {
        if (currentCapacity >= capacity) {
            // we need to create a new stack and push onto it
            setOfStacks.add(new Stack<Integer>(capacity));
            // reset current capacity
            currentCapacity = 0;
        }
        setOfStacks.get(setOfStacks.size() - 1)
                    .push(data);
        // update the current capacity
        currentCapacity++;
    }

    public int pop() {
        int data;
        if(currentCapacity <= 0) {
            // remove the current stack
            if(! setOfStacks.isEmpty()){
                setOfStacks.remove(setOfStacks.size() - 1);
            } else {
                return -10000;
            }
            // reset the current capacity to capacity of previous stack - 2 ~ 8
            currentCapacity = capacity;
        }
        // pop the right stack
        // I know im repeating a condition here, will
        // have to figure another way
        if (! setOfStacks.isEmpty()) {
            data = setOfStacks.get(setOfStacks.size() - 1)
                    .pop();
        } else {
            // the stacks are empty, do nothing
            System.out.println("\nError: The stack is empty, no way to pop.");
            data = -10000;
        }
        // update the current capacity
        currentCapacity--;
        return data;
    }

    @Override
    public String toString() {
        return setOfStacks.toString();
    }

    public static void main(String[] args) {
        // driver program
        SetOfStacks SOS = new SetOfStacks();
        int i = 0;
        // check if pushing is done properly across stacks
        while (i < 21) {
            SOS.push(i);
            i++;
        }
        System.out.println(SOS);

        // check if popping across stacks is ok
        while (i > 9) {
            System.out.print(" " + SOS.pop());
            i--;
        }

        // check if pushing after popping a stack with only one element
        // (resulting in removal of a stack) works as intended
        SOS.push(10);
        SOS.push(11);

        // check if extra popping happens
        while (i > -3) {
            System.out.print(" " + SOS.pop());
            i--;
        }

        System.out.println(SOS);
    }
}
