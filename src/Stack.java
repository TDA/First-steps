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
        if (top >= 0) {
            min = (data.compareTo(min) > 0) ? min : data;
        } else {
            min = data;
        }
        stack.add(++top, data);
    }

    public T pop() {
        if (top > -1) {
            T x = stack.get(top);
            stack.remove(top);
            top--;
            return x;
        } else {
            return null;
        }
    }

    public T getMin() {
        return min;
    }

    public T peek() {
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
