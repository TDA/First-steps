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
        Integer a = s.peek();
        Integer b = t.peek();
        if (a.equals(null)) {
            return false;
        }
        return (a < b);
    }

    public static void makeLegalMove(Stack<Integer> s, Stack<Integer> t) {
        if (isLegalMove(s, t)) {
            moveDisk(s, t);
        } else if (isLegalMove(t, s)){
            moveDisk(t, s);
        }
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
        double x = Math.pow(2, DISKS);
        for (int i = 0; i < x; i++) {
            if(DISKS % 2 != 0) {
                // if disks are odd, first move is from 1 to 3, always


                // find the legal move between the two stacks,
                // and make that move
                switch (i%3) {
                    case 0: if (!stack1.isEmpty()) {
                        makeLegalMove(stack1, stack3);
                    }
                        break;
                    case 1: makeLegalMove(stack1, stack2);
                        break;
                    case 2: makeLegalMove(stack3, stack2);
                        break;
                }

                printStacks();
            }
        }
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
