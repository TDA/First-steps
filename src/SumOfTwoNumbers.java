import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by schandramouli on 1/25/16.
 */
public class SumOfTwoNumbers {
    public static void main(String[] args) {
        int[] array = {1, 5, 8, 3, 10, 12, 1, 6};
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(array);

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            hashMap.put(i, Arrays.binarySearch(array, copy[i]));
        }
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(copy));
        System.out.println(hashMap);
        int sum = 8;

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
