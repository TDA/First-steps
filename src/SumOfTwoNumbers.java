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
            // System.out.println("two: " + i + " " + Arrays.binarySearch(array, copy[i]));
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
        System.out.println("First element is " + firstElement);
        System.out.println("Second element is " + secondElement);

        // these are the positions from the original array,
        // we add one cuz the output format is such
        System.out.println("First element is in ordinal " + (hashMap.get(l) + 1));
        System.out.println("Second element is in ordinal " + (hashMap.get(r) + 1));

        // So overall complexity for this is something like:
        // O(n) + O(n log n) + n O(log n) ==> Amortized to O(n log n)
        // is that even right? Im not sure, but its definitely
        // lesser than O(n^2) haha :D

        System.out.println();

        int[] pair = findPairOptimized(copy, sum);
        System.out.println("First element is " + copy[pair[0]]);
        System.out.println("Second element is " + copy[pair[1]]);

        // we add one cuz the output format is such
        System.out.println("First element is in ordinal " + (pair[0] + 1));
        System.out.println("Second element is in ordinal " + (pair[1] + 1));

    }

    public static int[] findPairOptimized(int[] array, int sum) {
        int[] pair = new int[2];
        // A faster way is as below: you use the hashmap to
        // hold the differences, and check if the number is present
        // this is O(n)! wooohoo! Thanks to Rajat for this:
        HashMap<Integer, Integer> differenceMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (differenceMap.containsKey(array[i])) {
                // means we found the needed number wooohooo!
                // return i, and also the value already stored
                // in the hashmap
                pair[0] = differenceMap.get(array[i]);
                pair[1] = i;
                break;
            } else {
                // add the DIFFERENCE to the map
                differenceMap.put(sum - array[i], i);
            }
        }
        return pair;
    }

}
