import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/12/15.
 */
public class ExactSumThreeNumbers {
    // given an array and a sum, find the three instead of two elements which result in the sum
    public static void main(String[] args) {
        System.out.println("Enter the number of elements in the array");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.println("Enter the sum to be found");
        int sum = scanner.nextInt();

        Arrays.sort(array);
        int forwardPointer = 0;
        int middlePointer = array.length - 2;
        int backwardPointer = array.length - 1;
        int tempSum = array[forwardPointer] + array[middlePointer] + array[backwardPointer];
        while (! isEqual(sum, tempSum)) {
            //System.out.println(array[forwardPointer]);
            //System.out.println(array[backwardPointer]);
            if (sum > tempSum) {
                // means we need to add bigger numbers
                forwardPointer++;
            } else {
                // means we have to add smaller numbers
                if (forwardPointer >= middlePointer) {
                    // if the other pointers have been exhausted, move the back pointer
                    backwardPointer--;
                } else {
                    // else move the middle pointer
                    middlePointer--;
                }
            }

            if (forwardPointer >= middlePointer || forwardPointer >= backwardPointer) {
                // means we couldnt find anything
                System.out.println("Couldnt find the numbers in the given array");
                break;
            }
            tempSum = array[forwardPointer] + array[middlePointer] + array[backwardPointer];
        }

        if (forwardPointer < backwardPointer && forwardPointer <= middlePointer) {
            System.out.printf("The numbers are %d, %d and %d\n", array[forwardPointer], array[middlePointer], array[backwardPointer]);
        }

    }

    public static int sum (int a, int b) {
        return a + b;
    }

    public static boolean isEqual (int a, int b) {
        return a == b;
    }

}
