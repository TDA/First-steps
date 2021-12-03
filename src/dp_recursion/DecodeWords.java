package dp_recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DecodeWords {
    Map<String, String> stringMap = new HashMap<>() {{
        put("1", "A");
        put("2", "B");
        put("3", "C");
        put("4", "D");
        put("5", "E");
        put("6", "F");
        put("7", "G");
        put("8", "H");
        put("9", "I");
        put("10", "J");
        put("11", "K");
        put("12", "L");
        put("13", "M");
        put("14", "N");
        put("15", "O");
        put("16", "P");
        put("17", "Q");
        put("18", "R");
        put("19", "S");
        put("20", "T");
        put("21", "U");
        put("22", "V");
        put("23", "W");
        put("24", "X");
        put("25", "Y");
        put("26", "Z");
    }};
    public int numDecodings(String s) {
        Map<Integer, Integer> cache = new HashMap<>();
        int helper = helper(s, 0, cache);
        System.out.println(cache);
        return helper;
    }

    private int helper(String s, int start, Map<Integer, Integer> cache) {
        if (cache.containsKey(start)) return cache.get(start);
        if (start > s.length()) return 0;
        if (s.length() == start) return 1;
        int count;
        char c = s.charAt(start);
        if (c == '0') count = 0;
        else if (c == '1') {
            // all following nums are valid, so calc next and next-next
            count = helper(s, start + 1, cache) + helper(s, start + 2, cache);
        } else if (c == '2') {
            // only 20-26 is allowed, but this letter is still valid for a combo
            count = helper(s, start + 1, cache);
            if ((start + 1) < s.length() && s.charAt(start + 1) >= '0' && s.charAt(start + 1) <= '6') {
                count += helper(s, start + 2, cache);
            }
        } else {
            count = helper(s, start + 1, cache);
        }
        cache.put(start, count);
        return count;
    }

    public int numDecodingsIterative(String s) {
        int[] dp = new int[s.length()];
        if (!isValid(s.substring(0,1))) return 0;
        dp[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            String substring = s.substring(i - 1, i + 1);
//            System.out.println(substring);
            if (isValid(substring)) dp[i] = Math.max(dp[i], dp[i - 1] + 1);
            if (isValid(substring.substring(0, 1))) dp[i] = Math.max(dp[i], dp[i - 1] + 1);
            if (isValid(substring.substring(1, 1))) dp[i] = Math.max(dp[i], dp[i - 1] + 1);
        }

        return Arrays.stream(dp).max().getAsInt();
    }

    boolean isValid(String s) {
        return stringMap.containsKey(s);
    }

    public static void main(String[] args){
        DecodeWords decodeWords = new DecodeWords();
        System.out.println(decodeWords.numDecodings("12"));
        System.out.println(decodeWords.numDecodingsIterative("12"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("226"));
        System.out.println(decodeWords.numDecodingsIterative("226"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("0"));
        System.out.println(decodeWords.numDecodingsIterative("0"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("06"));
        System.out.println(decodeWords.numDecodingsIterative("06"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("1201234"));
        System.out.println(decodeWords.numDecodingsIterative("1201234"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("123123"));
        System.out.println(decodeWords.numDecodingsIterative("123123"));
        System.out.println("----------------------------------------");
        System.out.println(decodeWords.numDecodings("1"));
        System.out.println(decodeWords.numDecodingsIterative("1"));
    }
}
