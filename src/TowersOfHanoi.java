/**
 * Created by schandramouli on 9/28/15.
 */
public class TowersOfHanoi {
    public static final int DISKS = 3;
    public static Stack<Integer> stack1= new Stack<>();
    public static Stack<Integer> stack2= new Stack<>();
    public static Stack<Integer> stack3= new Stack<>();

    public static void moveDisk(Stack<Integer> s, Stack<Integer> t) {
        t.push(s.pop());
    }

    public static void printStacks() {
        System.out.println("------------");
        System.out.println(stack1);
        System.out.println(stack2);
        System.out.println(stack3);
    }

    public static void func() {
        System.out.println();
    }

    public static boolean isLegalMove(Stack<Integer> s, Stack<Integer> t) {
        if (t.isEmpty()) {
            return true;
        }
        int a = s.peek();
        int b = t.peek();
        return (a < b);
    }
    public static void main(String[] args) {
        // 3 stacks for holding items

        for (int i = DISKS; i > 0; i--) {
            // insert in reverse, cuz stack
            stack1.push(i);
        }
        //System.out.println(stack1.getMin());
        // System.out.println(stack1);
        // if stack 3 isnt full
        int x = 0;
        /*while (x < 5) {
            if(DISKS % 2 != 0) {
                // if disks are odd, first move is from 1 to 3, always
                if (! stack1.isEmpty() && isLegalMove(stack1, stack3)) {
                    moveDisk(stack1, stack3);
                }
                printStacks();

                // then move from 1 to the stack which has no elements
                if (! stack1.isEmpty() && isLegalMove(stack1, stack2)) {
                    moveDisk(stack1, stack2);
                }
                printStacks();
                // Now the stacks are all at least holding 1 element
                // Move from the stack which has smallest to second smallest
                if (! stack3.isEmpty() && isLegalMove(stack3, stack2)) {
                    moveDisk(stack3, stack2);
                }
                printStacks();
                // Move larger elt to the empty stack
                moveDisk(stack1, stack3);
                //
                moveDisk(stack2, stack1);
                printStacks();
                moveDisk(stack2, stack3);
                printStacks();
                moveDisk(stack1, stack3);
                printStacks();

            }
            x++;

        }*/
        //solve(3, "1", "2", "3");
    }

    public static void solve(int n, String start, String auxiliary, String end) {
        // this is a solution from online, shame on you Sai.
        if (n == 1) {
            System.out.println(start + " -> " + end);
            //moveDisk(stack1, stack3);
        } else {
            solve(n - 1, start, end, auxiliary);
            System.out.println(start + " -> " + end);
            solve(n - 1, auxiliary, start, end);
        }
    }
}
