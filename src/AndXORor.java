import java.util.*;
import java.util.Stack;

/**
 * Created by schandramouli on 9/22/15.
 */
public class AndXORor {
    public static void main(String[] args) {
        int[] array = {9, 6, 3, 5, 2};
        int a = 10;
        int b = 3;
        int c  = ((a&b)^(a|b))&(a^b);
        int d = a^b;
        System.out.println(c);
        System.out.println(d);
        int currentMax = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i : array) {
            stack.push(i);
        }

        while (! stack.isEmpty()) {
            int x = stack.pop();
            if((currentMax^x) > currentMax) {
                currentMax = currentMax ^ x;
            } else {
                // do nothing
            }
        }
    }

    public int findSmallest (int L, int R) {

    }
}
