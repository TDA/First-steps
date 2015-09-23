/**
 * Created by schandramouli on 9/22/15.
 */
public class Stack<T extends Comparable<T>> {
    private T data;
    private int top = 0;
    private T[] stack;
    private T min;

    public void push(T data) {
        stack[top++] = data;
        min = (min.compareTo(data) > 0) ? min : data;
    }

    public T pop() {
        return stack[top--];
    }

    public T getMin() {
        return min;
    }

    public T peek() {
        return stack[top];
    }

}
