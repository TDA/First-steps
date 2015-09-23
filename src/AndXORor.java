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
        //System.out.println(c);
        //System.out.println(d);
        int currentMax = 0;
        int [] smallers = findSmallest(array, 0, 5);
        for (int smaller : smallers) {
            System.out.println(smaller);
        }
    }

    public static int[] findSmallest (int [] array, int L, int R) {
        int smallest = array[L], smallest2 = array[L];
        for (int i = L + 1; i < R; i++) {
            if (array[i] < smallest) {
                smallest2 = smallest;
                smallest = array[i];
            }
        }
        return new int[]{smallest, smallest2};
    }
}
