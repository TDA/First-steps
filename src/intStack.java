/**
 * Created by schandramouli on 9/23/15.
 */
public class intStack {
    public static void main(String[] args) {
        Stack<Integer> s1 = new Stack<>();
        s1.push(13);
        s1.push(12);
        s1.push(11);
        System.out.println(s1.peek());
        System.out.println(s1.pop());
        System.out.println(s1.getMin());
        System.out.println(s1.pop());
        System.out.println(s1.getMin());
        System.out.println(s1.search(13));
        System.out.println(s1);
        System.out.println(s1.isEmpty());


        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(12);
        stack.push(134);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());

    }
}
