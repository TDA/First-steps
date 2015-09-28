import java.util.ArrayList;
import java.util.Set;

/**
 * Created by schandramouli on 9/27/15.
 */

public class SetOfStacks {
    int capacity = 10; // hold only ten items in any stack
    ArrayList<Stack> setOfStacks = new ArrayList<>();

    public SetOfStacks() {
        setOfStacks.add(0, new Stack<Integer>(capacity));
    }

    public void push() {

    }

    @Override
    public String toString() {
        return setOfStacks.toString();
    }

    public static void main(String[] args) {
        SetOfStacks SOS = new SetOfStacks();
        SOS.setOfStacks.get(0).push(21);
        System.out.println(SOS);
    }
}
