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
        System.out.println(rS);
    }
}
