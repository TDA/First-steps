import java.util.Arrays;

/**
 * Created by schandramouli on 1/25/16.
 */
public class SumOfTwoNumbers {
    public static void main(String[] args) {
        int[] array = {1, 5, 8, 3, 10, 12, 1, 6};
        int[] copyArray = Arrays.copyOf(array, array.length);
        int sum = 8;
        Arrays.sort(array);
        int l = 0;
        int r = array.length - 1;
        while (l < r && (array[l] + array[r] != sum)) {
            if ((array[l] + array[r] < sum)) {
                l++;
            } else {
                r--;
            }
        }
        int firstElement = array[l];
        int secondElement = array[r];

        System.out.println(firstElement);
        System.out.println(secondElement);

    }
}
