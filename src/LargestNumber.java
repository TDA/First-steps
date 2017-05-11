import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by schandramouli on 5/30/17.
 */

public class LargestNumber {
    public static void main(String args[]){

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
