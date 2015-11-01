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
        int value1 = 0;
        int value2 = 0;
        int currentMax = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int [] smallers = findSmallest(array, i, j);
                if ((smallers[0] ^ smallers[1]) > currentMax) {
                    System.out.println("L is " + i + " and R is " + j);
                    currentMax = smallers[0] ^ smallers[1];
                    value1 = smallers[0];
                    value2 = smallers[1];
                    System.out.println("Smallest two are: " + smallers[0] + " " + smallers[1] + ", XOR is " + currentMax);
                }
            }
        }
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(currentMax);

    }

    public static int[] findSmallest (int [] array, int L, int R) {
        int smallest = array[L], smallest2 = array[L];
        for (int i = L + 1; i <= R; i++) {
            if (array[i] < smallest) {
                smallest2 = smallest;
                smallest = array[i];
            }
        }
        return new int[]{smallest, smallest2};
    }
}
