import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by schandramouli on 5/8/17.
 */

public class LargestNumber {
    public static void main(String args[]){
        int[] input = {3, 30, 34, 5, 9};
        HashMap<Integer, ArrayList> bucket = new HashMap<>();
        for (int number: input) {
            Integer firstDigit = getFirstDigit(number);

            ArrayList<Integer> existingList;
            if (bucket.get(firstDigit) != null) {
                // already exists
                existingList = bucket.get(firstDigit);
                existingList.add(number);
            } else {
                existingList = new ArrayList<>();
                existingList.add(number);
            }
            bucket.put(firstDigit, existingList);
        }

        String ss = "";
        for (int i = 9; i >= 0 ; i--) {
            ArrayList list = bucket.get(i);
            if (list != null) {
                Collections.reverse(list);
                ss += list.toString();
            }
        }
        System.out.println(ss);
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
