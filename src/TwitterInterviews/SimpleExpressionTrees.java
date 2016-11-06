package TwitterInterviews;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by schandramouli on 11/5/16.
 */
public class SimpleExpressionTrees {
    public static void main(String[] args) {
        String s = "(AB) C((D E)F)";
        s = s.replaceAll("\\s+", "");
        System.out.println(s);
        System.out.println(reverse1(s));
        System.out.println(reverse2(s));
    }

    public static String reverse1(String s) {
        Stack<Character> charStack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                c = ')';
            } else if (c == ')') {
                c = '(';
            }
            charStack.push(c);
        }
        String rS = "";
        for (int i = 0; i < s.length(); i++) {
            char c = charStack.pop();
            rS += c;
        }
        return rS;
    }

    public static String reverse2(String s) {
        String rS2 = "";
        for (int i = s.length() - 1; i > -1; i--) {
            // reversing made easier?
            char c = s.charAt(i);
            if (c == '(') {
                c = ')';
            } else if (c == ')') {
                c = '(';
            }
            rS2 += c;
        }
        return rS2;
    }
}
