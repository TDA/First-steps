/**
 * Created by schandramouli on 9/22/15.
 */
public class Stack<T> {
    private T data;
    private int top = 0;
    private T[] stack;

    public void push(T data) {
        top++;
        stack[top] = data;
    }

    public T pop() {
        return stack[top--];
    }
}
