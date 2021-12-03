package dp_recursion;

import java.util.Arrays;

public class LongestCommonSubsequence {
    private Integer[][] dp;

    public int longestCommonSubsequence(String text1, String text2) {
        dp = new Integer[text1.length()][text2.length()];
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();
        return longestCommonSubsequence(chars1, chars2, 0 , 0);
    }

    private int longestCommonSubsequence(char[] chars1, char[] chars2, int i, int j) {
        if (i == chars1.length || j == chars2.length) return 0; // nothing to traverse further
        if (dp[i][j] != null) return dp[i][j];
        if (chars1[i] == chars2[j]) {// match, move both
            dp[i][j] = 1 + longestCommonSubsequence(chars1, chars2, i + 1, j + 1);
            return dp[i][j];
        }
        // else, two situations, either move i or j => do both
        dp[i][j] = Math.max(longestCommonSubsequence(chars1, chars2, i, j + 1), longestCommonSubsequence(chars1, chars2, i + 1, j ));
        return dp[i][j];
    }

    public static void main(String[] args){
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        System.out.println(longestCommonSubsequence.longestCommonSubsequence("abc", "abc")); // 3
        System.out.println(longestCommonSubsequence.longestCommonSubsequence("abc", "def")); // 0
        System.out.println(longestCommonSubsequence.longestCommonSubsequence("xyabcde", "kace")); // 3
    }
}
