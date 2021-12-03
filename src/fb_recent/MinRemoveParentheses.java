package fb_recent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MinRemoveParentheses {
    public String minRemoveToMakeValid(String inputString) {
        Stack<Character> stack = new Stack<>();
        int mismatchedRight = 0;
        for (char c: inputString.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    mismatchedRight++;
                } else {
                    stack.pop();
                }
            }
        }

        int mismatchedLeft = stack.size();

        StringBuilder sb = new StringBuilder(inputString);

        while (mismatchedLeft > 0) {
            int i = sb.lastIndexOf("(");
            sb.deleteCharAt(i);
            mismatchedLeft--;
        }
        while (mismatchedRight > 0) {
            int i = sb.indexOf(")");
            sb.deleteCharAt(i);
            mismatchedRight--;
        }
        return sb.toString();
    }

    public List<String> removeInvalidParentheses(String inputString) {
        List<String> allStrings = new ArrayList<>();

        if (inputString == null) return allStrings;

        Queue<String> stringQueue = new ArrayDeque<>();
        Set<String> visitedStrings = new HashSet<>();
        stringQueue.add(inputString);
        visitedStrings.add(inputString);

        boolean found = false;

        while (!stringQueue.isEmpty()) {
            String polledString = stringQueue.poll();

            if (isValid(polledString)) {
                allStrings.add(polledString);
                found = true;
            }

            if (found) continue;

            // generate all possible states
            for (int i = 0; i < polledString.length(); i++) {
                // we only try to remove left or right paren
                if (polledString.charAt(i) != '(' && polledString.charAt(i) != ')') continue;

                String temp = polledString.substring(0, i) + polledString.substring(i + 1);

                if (visitedStrings.add(temp)) {
                    stringQueue.add(temp);
                }
            }
        }

        return allStrings;
    }

    boolean isValid(String inputString) {
        int count = 0;

        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '(') count++;
            if (c == ')' && count-- == 0) return false;
        }

        return count == 0;
    }

    public static void main(String[] args){
        MinRemoveParentheses minRemoveParentheses = new MinRemoveParentheses();
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("a)b(c)d"));
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("))(("));
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("(((((())"));
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("(a(b(c)d)"));
        System.out.println(minRemoveParentheses.minRemoveToMakeValid("())()((("));

        System.out.println(minRemoveParentheses.removeInvalidParentheses("()())()"));
        System.out.println(minRemoveParentheses.removeInvalidParentheses("(a)())()"));
        System.out.println(minRemoveParentheses.removeInvalidParentheses(")(").size());
    }
}
