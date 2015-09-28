import java.util.ArrayList;

/**
 * Created by schandramouli on 9/27/15.
 */

public class SetOfStacks {
    // This is only an integer implementation
    // Will eventually make it a Generic one
    int capacity = 10; // hold only ten items in any stack
    int currentCapacity = 0;
    ArrayList<Stack> setOfStacks = new ArrayList<>();

    public SetOfStacks() {
        setOfStacks.add(0, new Stack<Integer>(capacity));
    }

    public void push(int data) {
        currentCapacity++;
        if (currentCapacity >= capacity) {
            // we need to create a new stack and push onto it
            setOfStacks.add(new Stack<Integer>(capacity));
            // reset current capacity
            currentCapacity = 0;
        }
        setOfStacks.get(setOfStacks.size() - 1)
                    .push(data);
    }

    @Override
    public String toString() {
        return setOfStacks.toString();
    }

    public static void main(String[] args) {
        SetOfStacks SOS = new SetOfStacks();
        int i = 0;
        while (i < 21) {
            SOS.push(21);
            i++;
        }
        System.out.println(SOS);
    }
}
