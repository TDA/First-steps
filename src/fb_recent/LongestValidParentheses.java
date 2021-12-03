package fb_recent;

public class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        if (s == null) return 0;
        int open = 0, closed = 0;
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else if (s.charAt(i) == ')') {
                closed++;
            }
            if (open == closed) {
                // balanced, calc maxLength
                maxLength = Math.max(maxLength, closed + open);
            }
            if (closed > open) {
                // too much open, not enough closed, reset
                open = 0; closed = 0;
            }
        }
        open = closed = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                open++;
            } else if (s.charAt(i) == ')') {
                closed++;
            }
            if (open == closed) {
                // balanced, calc maxLength
                maxLength = Math.max(maxLength, closed + open);
            }
            if (open > closed) {
                // too much open, not enough closed, reset
                open = 0; closed = 0;
            }
        }
        return maxLength;
    }

    public static void main(String[] args){
        LongestValidParentheses longestValidParentheses = new LongestValidParentheses();
        System.out.println(longestValidParentheses.longestValidParentheses("(()"));
        System.out.println(longestValidParentheses.longestValidParentheses(")()())"));
        System.out.println(longestValidParentheses.longestValidParentheses(")()())((())))"));
        System.out.println(longestValidParentheses.longestValidParentheses(""));
    }
}
