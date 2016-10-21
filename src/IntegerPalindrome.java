/**
 * Created by schandramouli on 10/19/16.
 */
public class IntegerPalindrome {
    public static void main(String[] args) {
        int a = 14744741;
        int b = 123456;
        System.out.println(isIntegerPalindrome(a));
        System.out.println(isIntegerToStringPalindrome(a));
        System.out.println(isIntegerPalindrome(b));
        System.out.println(isIntegerToStringPalindrome(b));
    }

    public static boolean isIntegerPalindrome(int a) {
        int reversedInt = 0;
        int regInt = a;
        while (a > 0) {
            System.out.println(a + "   " + reversedInt);
            reversedInt += (a % 10);
            a = a / 10;
            if (a != 0) {
                reversedInt *= 10;
            }
        }
        System.out.println(reversedInt);
        System.out.println(regInt);
        return reversedInt == regInt;
    }

    public static boolean isIntegerToStringPalindrome(int a) {
        String s = String.valueOf(a);
        return new StringBuffer(s).reverse().toString().equals(s);
    }
}
