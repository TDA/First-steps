import java.util.ArrayList;

/**
 * Created by schandramouli on 9/22/15.
 */
public class Stack<T extends Comparable<T>> {
    private T data;
    // top points to the current top, NOT the next insertable position
    private int top = -1;

    private ArrayList<T> stack;
    private T min;

    public Stack() {
        stack = new ArrayList<>();
    }

    public void push(T data) {
        // O(1) for push
        // if there is at least one element in the stack
        if (top >= 0) {
            min = (data.compareTo(min) > 0) ? min : data;
        } else {
            // no elements, so assign the element to be min
            min = data;
        }
        // update top AND THEN insert element
        stack.add(++top, data);
    }

    public T pop() {
        // O(1) for pop
        // if there is at least one element in the stack
        if (top > -1) {
            // can we combine these somehow?
            T x = stack.get(top);
            stack.remove(top);
            top--;
            return x;
        } else {
            // no elements, cant pop, choosing to not throw an exception
            return null;
        }
    }

    public T getMin() {
        // O(1) for getting min
        return min;
    }

    public T peek() {
        // O(1) for getting top element
        return stack.get(top);
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public int getLength() {
        // top is one less than length
        return top + 1;
    }

}
