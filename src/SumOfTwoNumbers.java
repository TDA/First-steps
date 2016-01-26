import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by schandramouli on 1/25/16.
 */
public class SumOfTwoNumbers {
    public static void main(String[] args) {
        int[] array = {1, 5, 8, 3, 10, 12, 6};
        // this is O(n)
        int[] copy = Arrays.copyOf(array, array.length);

        // this is O(n log n)
        Arrays.sort(array);

        HashMap<Integer, Integer> hashMap = new HashMap<>();


        for (int i = 0; i < array.length; i++) {
            // this is O(log n) for searching done n times => n O(log n)
            System.out.println("two: " + i + " " + Arrays.binarySearch(array, copy[i]));
            // map the new indices to original indices
            hashMap.put(Arrays.binarySearch(array, copy[i]), i);
        }
        // System.out.println(Arrays.toString(array));
        // System.out.println(Arrays.toString(copy));
        // System.out.println(hashMap);
        int sum = 22;

        int l = 0;
        int r = array.length - 1;

        // this is O(n) worst case
        while (l < r && (array[l] + array[r] != sum)) {
            if ((array[l] + array[r] < sum)) {
                l++;
            } else {
                r--;
            }
        }
        int firstElement = array[l];
        int secondElement = array[r];

        // these are the elements that add to the given sum
        System.out.println(firstElement);
        System.out.println(secondElement);

        // these are the positions from the original array
        System.out.println(hashMap.get(l) + 1);
        System.out.println(hashMap.get(r) + 1);
    }

}