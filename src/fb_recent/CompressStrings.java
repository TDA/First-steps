package fb_recent;

import java.util.Stack;

public class CompressStrings {

////    Input: "abbba"
////    Output: "" ("abbba" => "aa" => "")
////
////    Input: "ab"
////    Output: "ab"
////
////    Input: "a"
////    Output: "a"
////
////
////    Input: "abcba"
////    Output: "abcba"
////
////    Input: "aaaaaaaa"
////    Output: ""
////
////    Input: "aaaaaaaabbbbbbbb"
////    Output: ""
////
//
//
//
//    Input: "aaabbbcccdddeee..zzzyyyxxx...bbbaaa"
//    Output: ""

    String compressString(String input) {
        Stack<Character> stack = new Stack<>(); // WC: O(n), AC: O(1)
        if (input.length() <= 1) return input;

        char[] chars = input.toCharArray(); // O(n)

        stack.push(chars[0]); // a

        // validate for stack empty
        for (int i = 1; i < chars.length; ) { // O(n - k)
            char c = chars[i]; // b
            if (stack.isEmpty()) {
                stack.push(c);
                continue;
            }
            char mostRecentChar = stack.peek(); // a
            if (mostRecentChar == c) { // a == a
                // traverse till i find end of this seq, and then pop
                while (i < chars.length && mostRecentChar == chars[i]) { // O(k)
                    i++;
                }
                stack.pop(); //
            } else {
                stack.push(c); // a, b, c, b, a
                i++;
            }
        } // O(n)

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            char c  = stack.pop();
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args){
        CompressStrings compressStrings = new CompressStrings();
        System.out.println(compressStrings.compressString("abbba"));
        System.out.println(compressStrings.compressString("ab"));
        System.out.println(compressStrings.compressString("a"));
        System.out.println(compressStrings.compressString("abcba"));
        System.out.println(compressStrings.compressString("aaaaaaaa"));
        System.out.println(compressStrings.compressString("aaaaaaaabbbbbbbb"));
    }
}
