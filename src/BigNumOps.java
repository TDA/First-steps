import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/21/15.
 */
public class BigNumOps {
    char[] charArray;

    public BigNumOps(char[] charArray) {
        this.charArray = charArray;
    }

    public BigNumOps(String number) {
        this.charArray = number.toCharArray();
    }

    public static void main(String[] args) {
        // add two arbitrarily large numbers
        Scanner scanner = new Scanner(System.in);

        // store them as strings, or as char arrays
        String firstNumber = scanner.nextLine();
        String secondNumber = scanner.nextLine();

        char[] firstNumberCharArray = firstNumber.toCharArray();
        BigNumOps bigNum = new BigNumOps(firstNumber);
        char[] secondNumberCharArray = secondNumber.toCharArray();

        // first we need to check what length to set for our sum array, whether it needs to be same
        // as the bigger array, or if arrays are equal in length, will they result in a carry?

        char[] sumArray;
        // if they are equal doesnt matter which ones length was assigned, so.
        int length = (firstNumberCharArray.length > secondNumberCharArray.length)? firstNumberCharArray.length : secondNumberCharArray.length;
        if ((firstNumberCharArray.length == secondNumberCharArray.length)) {
            // lets check
            if (bigNum.intValue(firstNumberCharArray[0]) + bigNum.intValue(secondNumberCharArray[0]) + 1 >= 10) {
                // might be overflow, safer to assign extra
                sumArray = new char[length + 1];
            } else {
                // no way for an overflow, so assign
                sumArray = new char[length];
            }
        } else {
            // assign larger array
            sumArray = new char[length];
        }

        //
        // convert to char array and add
        // i was right about this here, and possibly the Google guy actually was mistaken :O, storing them as char array
        // would perform char add/sub like so, 1 + 2 would be 99, not 3, we would have to subtract two zeroes or (48+48),
        // so preprocess the arrays to hold the numbers actual value => 0 to 0 instead of 48, or do on the fly
        int carry = 0;
        int shift = (length == sumArray.length) ? 0 : 1;
        // start from right, propagate carry through
        for (int i = firstNumberCharArray.length - 1; i >= 0 ; i--) {
            // carry would be zero from previous if no carry, and 1 if there is carry
            int digit = (bigNum.intValue(firstNumberCharArray[i]) + bigNum.intValue(secondNumberCharArray[i])) + carry;
            if (digit >= 10) {
                // we have a carry
                carry = 1;
                digit = digit - 10;
            } else {
                // reset carry
                carry = 0;
            }
            System.out.println(firstNumberCharArray[i] + " and " + secondNumberCharArray[i] + " is " + digit + " and carry " + carry);
            sumArray[i + shift] = (char) (digit + '0');
        }
        // special case, might be an overflow
        if (shift == 1) {
            sumArray[0] = bigNum.charValue(carry);
        }
        BigNumOps sum = new BigNumOps(sumArray);
        System.out.println(sum);
    }

    public int intValue(char c) {
        return c - '0';
    }

    public char charValue(int x) {
        return (char) (x + 48);
    }

    @Override
    public String toString() {
        return new String(charArray);
    }
}
