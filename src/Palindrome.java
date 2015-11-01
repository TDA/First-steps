/**
 * Created by schandramouli on 11/1/15.
 */
public class Palindrome {
    public static void main(String[] args) {
        String s = "is a si";
        // spaces count as characters, if they don't, then strip the string before
        System.out.println(new StringBuilder(s).reverse().toString());
        if (s.equals(new StringBuilder(s).reverse().toString())) {
            System.out.println("Palindrome");
        } else {
            System.out.println("Not a Palindrome");
        }
    }
}
