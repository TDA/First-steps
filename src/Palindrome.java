/**
 * Created by schandramouli on 11/1/15.
 */
public class Palindrome {
    public static void main(String[] args) {
        String s = "not a palindrome";
        // spaces count as characters, if they don't, then strip the string before
        //System.out.println(new StringBuilder(s).reverse().toString());
        if (isConcisePalindrome(s)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a Palindrome");
        }
        if (isPalindrome(s)) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a Palindrome");
        }
    }

    public static boolean isConcisePalindrome (String s) {
        return s.equals(
                new StringBuilder(s)
                        .reverse()
                        .toString()
        );
    }

    public static boolean isPalindrome (String s) {
        String temp = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            temp += s.charAt(i);
        }
        return s.equals(temp);
    }

}
