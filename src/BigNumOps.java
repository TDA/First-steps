import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/21/15.
 */
public class BigNumOps {
    public static void main(String[] args) {
        // add two arbitrarily large numbers
        Scanner scanner = new Scanner(System.in);

        // store them as strings, or as char arrays
        String firstNumber = scanner.nextLine();
        String secondNumber = scanner.nextLine();

        char[] firstNumberCharArray = firstNumber.toCharArray();
        char[] secondNumberCharArray = secondNumber.toCharArray();
        char[] sumArray = new char[firstNumberCharArray.length];


        //int length = (firstNumberCharArray.length > secondNumberCharArray.length)? secondNumberCharArray.length : firstNumberCharArray.length;
        // convert to char array and add
        // i was right here, and possibly the Google guy actually was mistaken :O, storing them as char array
        // would perform char add/sub like so, 1 + 2 would be 99, not 3, we would have to subtract two zeroes or (48+48),
        // so preprocess the arrays to hold the numbers actual value => 0 to 0 instead of 48, or do on the fly
        int carry = 0;
        // start from right, propagate carry through
        for (int i = firstNumberCharArray.length - 1; i >= 0 ; i--) {
            // carry would be zero from previous if no carry, and 1 if there is carry
            int digit = (firstNumberCharArray[i] + secondNumberCharArray[i] - (2 * '0')) + carry;
            if (digit > 10) {
                // we have a carry
                carry = 1;
                digit = digit - 10;
            } else {
                // reset carry
                carry = 0;
            }
            System.out.println(firstNumberCharArray[i] + " and " + secondNumberCharArray[i] + " is " + digit + " and carry " + carry);
            sumArray[i] = (char) (digit + '0');
        }
        String sum = new String(sumArray);
        System.out.println(sum);
    }
}
