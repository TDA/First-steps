import java.util.ArrayList;

/**
 * Created by schandramouli on 9/22/15.
 */
public class Stack<T extends Comparable<T>> {
    private T data;
    private int top = 0;
    private ArrayList<T> stack;
    private T min;

    public void push(T data) {
        stack.add(top++, data);
        min = (min.compareTo(data) > 0) ? min : data;
    }

    public T pop() {
        if (top != 0) {
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
        return (top == 0);
    }

}
