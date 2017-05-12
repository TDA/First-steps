import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by schandramouli on 5/30/17.
 */

public class LargestNumber {
    public static void main(String args[]){
        int[] input = {3, 30, 34, 5, 9};
        HashMap<Integer, ArrayList> bucket = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            int number = input[i];
            String s = String.valueOf(number);
            System.out.println(s);
            Integer firstDigit = getFirstDigit(number);

            if (bucket.get(firstDigit) != null) {
                // already exists
                ArrayList<Integer> existingList = bucket.get(firstDigit);
                existingList.add(number);
                bucket.put(firstDigit, existingList);
            } else {
                ArrayList<Integer> existingList = new ArrayList<>();
                existingList.add(number);
                bucket.put(firstDigit, existingList);
            }
        }
        System.out.println(bucket);

    }

    private static int getFirstDigit(int number) {
        int digit = number;
        while (number > 0) {
            digit = number % 10;
            number = number / 10;
        }
        return digit;
    }
}
