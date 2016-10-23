import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by schandramouli on 10/23/16.
 */
public class MaxZeroes {
    public static void main(String[] args) {
        Integer[] array = {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0};
        Integer[] flippedArray = flip(array);
        System.out.println(Arrays.toString(flippedArray));
        int element = 0;
        int countZeroes = countOccurrences(array, 0, array.length, element);
        System.out.println(countZeroes);

        int maxZeroes = 0;
        int startIndex = 0;
        int endIndex = array.length;

        // means we got to find the window which has most zeroes
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length - 1; j++) {
                int onesInThisSlice = countOccurrences(array, i, j, 1);
                if (onesInThisSlice > maxZeroes) {
                    maxZeroes = onesInThisSlice;
                    startIndex = i;
                    endIndex = j;
                }
            }
        }

        int max_so_far = sum(array);
        System.out.println("The range is " + startIndex + " to " + endIndex);
    }

    private static int sum(Integer[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private static int countOccurrences(Integer[] array, int start, int end, int element) {
        int count = 0;
        for (int i = start; i < end; i++) {
            count += array[i] == element ? 1 : 0;
        }
        return count;
    }

    private static Integer[] flip(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] == 0 ? 1 : -1;
        }
        return array;
    }


}
