import java.util.HashMap;

/**
 * Created by schandramouli on 3/22/16.
 */
public class MissingNumbers {
    public static void main(String[] args) {
        int[] array = {0, 1, 2, 3, 4, 5, 5, 7, 8, 9, 10};
        int min = array[0];
        int max = array[array.length - 1];
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            // update min and max
            if (min > array[i]) {
                min = array[i];
            }
            if (max < array[i]) {
                max = array[i];
            }
            if (!hm.containsKey(array[i])) {
                hm.put(array[i], 1);
            } else {
                // duplicate element
                System.out.println("Dupes: " + array[i]);
            }
        }

        for (int j = min; j < max; j++) {
            if (!hm.containsKey(j)) {
                System.out.println("Missing: " + j);
            }
        }

//        System.out.println(actualSum - sum);

    }
}
