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

    public Stack(int i) {
        stack = new ArrayList<>(i);
    }

    public void push(T data) {
        // O(1) for push
        // if there is at least one element in the stack
        if (top >= 0) {
            if (min != null && data.compareTo(min) < 0) {
                // data is smaller, so update min
                min = data;
            } else {
                // data is larger, do nothing
            }

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
            // check if min needs to be updated
            if(min != null) {
                if (x.compareTo(min) == 0) {
                    // if x is min, reset min, we can calculate min when someone actually calls getMin()
                    min = null;
                }
            }
            stack.remove(top);
            top--;
            return x;
        } else {
            // no elements, cant pop, choosing to not throw an exception
            return null;
        }
    }

    public T getMin() {
        // O(1) for getting min if not null
        if (min == null) {
            min = stack.get(0);
            for (int x = top; x > 0; x--) {
                 if (stack.get(x).compareTo(min) < 0) {
                     // element is lesser than min, so reassign
                     min = stack.get(x);
                 }
            }
        }
        return min;
    }

    public T peek() {
        // O(1) for getting top element
        if (stack.isEmpty()) {
            return null;
        }
        return stack.get(top);
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public int getLength() {
        // top is one less than length
        return top + 1;
    }

    public int search(T data) {
        return stack.indexOf(data);
    }

    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
