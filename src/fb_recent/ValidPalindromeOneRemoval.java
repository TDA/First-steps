package fb_recent;

public class ValidPalindromeOneRemoval {
    public boolean validPalindrome(String s) {
        return validPalindromeHelper(s, 0);
    }
    public boolean validPalindromeHelper(String s, int mismatches) {
//        System.out.println("Check " + s);
        if (mismatches > 1) return false;
        if (s.length() <= 1) return true;

        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            if (s.charAt(start) == s.charAt(end)) {
                start++;
                end--;
            } else {
                return validPalindromeHelper(s.substring(start, end), mismatches + 1)
                        || validPalindromeHelper(s.substring(start + 1, end + 1), mismatches + 1);
            }
        }
        return true;
    }

    public static void main(String[] args){
        ValidPalindromeOneRemoval validPalindromeOneRemoval = new ValidPalindromeOneRemoval();
        System.out.println(validPalindromeOneRemoval.validPalindrome("aba"));
        System.out.println(validPalindromeOneRemoval.validPalindrome("abca"));
        System.out.println(validPalindromeOneRemoval.validPalindrome("abcfa"));
        System.out.println(validPalindromeOneRemoval.validPalindrome("deeeee"));
        System.out.println(validPalindromeOneRemoval.validPalindrome("eeeeed"));
    }
}
