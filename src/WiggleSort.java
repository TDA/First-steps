import java.util.Arrays;

/**
 * Created by schandramouli on 2/3/16.
 */
public class WiggleSort {
    public static void main(String[] args) {
        // given an array, sort the array, with
        // big small big small elements alternatively
        int[] array = {1, 7, 8, 4, 3, 9};
        int[] finalArray = new int[array.length];
        // n log n solution is to sort and
        // then use two pointers
        Arrays.sort(array);
        System.out.println(array);
        int l = 0;
        int r = array.length - 1;
        int i = 0;
        while (l < r) {
            l++;
            r--;
            finalArray[i] = array[l];
            finalArray[i + 1] = array[r];
            i = i + 2;
        }
        System.out.println(finalArray);
    }
}
