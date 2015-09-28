/**
 * Created by schandramouli on 9/28/15.
 */
public class TowersOfHanoi {
    public static final int DISKS = 3;

    public static void moveDisk(Stack<Integer> s, Stack<Integer> t) {
        int data = s.pop();
        System.out.println(data);
        //t.push();
    }

    public static void main(String[] args) {
        // 3 stacks for holding items
        Stack<Integer> stack1= new Stack<>();
        Stack<Integer> stack2= new Stack<>();
        Stack<Integer> stack3= new Stack<>();

        for (int i = DISKS; i > 0; i--) {
            // insert in reverse, cuz stack
            stack1.push(i);
        }
        System.out.println(stack1.getMin());
        // System.out.println(stack1);
        // if stack 3 isnt full
        while (stack3.size() < DISKS) {
            if(DISKS % 2 != 0) {
                // if disks are odd, first move is from 1 to 3, always
                moveDisk(stack1, stack3);
                // base case, if a stack is empty, move onto it
                if (stack2.peek() == null) {
                    //moveDisk(stack1, stack2);
                }
                // case when
                //if (stack1.peek() > stack2.peek()) {

                //}
            }
            System.out.println(stack1);
            System.out.println(stack2);
            System.out.println(stack3);
        }
    }
}
