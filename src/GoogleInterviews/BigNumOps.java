package GoogleInterviews;

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

    public BigNumOps(int number) {
        this.charArray = (number + "").toCharArray();
    }

    public BigNumOps() {
        // initialize to all zeroes
    }

    public int length() {
        return charArray.length;
    }

    public static void main(String[] args) {
        // add two arbitrarily large numbers
        Scanner scanner = new Scanner(System.in);

        // store them as strings, or as char arrays
        String firstNumber = scanner.nextLine();
        String secondNumber = scanner.nextLine();

        BigNumOps bigNum1 = new BigNumOps(firstNumber);
        //System.out.println(bigNum.length());
        BigNumOps bigNum2 = new BigNumOps(secondNumber);
        System.out.println("Sum is " + bigNum1.add(bigNum2));
        // sum is here
        System.out.println("Product is " + bigNum1.multiply(bigNum2));


    }

    public BigNumOps add (BigNumOps bigNum2) {
        // first we need to check what length to set for our sum array, whether it needs to be same
        // as the bigger array, or if arrays are equal in length, will they result in a carry?

        char[] sumArray;
        // if they are equal doesnt matter which ones length was assigned, so.
        int sizeDifference = Math.abs(this.length() - bigNum2.length() );
        int length = (this.length() > bigNum2.length())? this.length() : bigNum2.length();

        // now pad the smaller array to match larger one, padding wont really occur if they are equal
        if (this.length() > bigNum2.length()) {
            // pad bignum2
            bigNum2.padWithZeroes(sizeDifference);
        } else {
            this.padWithZeroes(sizeDifference);
        }

        if (this.intValue(this.charArray[0]) + bigNum2.intValue(bigNum2.charArray[0]) + 1 >= 9) {
            // might be overflow, safer to assign extra
            sumArray = new char[length + 1];
        } else {
            // no way for an overflow, so assign
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
        for (int i = this.length() - 1; i >= 0 ; i--) {
            // carry would be zero from previous if no carry, and 1 if there is carry
            int digit = (this.intValue(this.charArray[i]) + bigNum2.intValue(bigNum2.charArray[i])) + carry;
            if (digit >= 10) {
                // we have a carry
                carry = 1;
                digit = digit - 10;
            } else {
                // reset carry
                carry = 0;
            }
            //System.out.println(this.charArray[i] + " and " + bigNum2.charArray[i] + " is " + digit + " and carry " + carry);
            sumArray[i + shift] = (char) (digit + '0');
        }
        // special case, might be an overflow
        if (shift == 1) {
            sumArray[0] = this.charValue(carry);
        }
        return new BigNumOps(sumArray);
    }


    public BigNumOps multiply (BigNumOps bigNum2) {
        // initialize the product
        BigNumOps product = new BigNumOps("0");
        System.out.println(product);

        int BN2Length = bigNum2.length();
        for (int i = BN2Length - 1; i >= 0; i--) {
            int times = bigNum2.intValue(bigNum2.charArray[i]);
            if (times == 0) {
                continue;
            }
            BigNumOps temp2 = new BigNumOps(this.charArray);
            while (times > 1) {
                temp2 = this.add(temp2);
                times--;
            }
            int pad = BN2Length - 1 - i;
            temp2.padWithZeroes(-pad);
            product = product.add(temp2);
        }
        return product;
    }

    public int intValue(char c) {
        return c - '0';
    }

    public char charValue(int x) {
        return (char) (x + 48);
    }

    @Override
    public String toString() {
        String s = new String(charArray);
        // strip leading zeroes
        while (s.startsWith("0")) {
            s = s.substring(1);
        }
        return s;
    }

    public void padWithZeroes(int padding) {
        if (padding == 0) {
            return;
        }
        int currentLength = this.charArray.length;
        int newLength = currentLength + Math.abs(padding);
        char[] tempArray = new char[newLength];

        for (int i = 0; i < newLength; i++) {
            // fill with all zeroes
            tempArray[i] = this.charValue(0);
        }
        if (padding > 0) {
            System.arraycopy(this.charArray, 0, tempArray, padding, currentLength);
        } else {
            System.arraycopy(this.charArray,0, tempArray, 0, currentLength);
        }
        this.charArray = tempArray;
    }

}
