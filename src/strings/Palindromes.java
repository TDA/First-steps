package strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Palindromes {
    public static void main(String[] args){
        Palindromes palindromes = new Palindromes();
        System.out.println(palindromes.isPalindrome("saisai"));
        System.out.println(palindromes.isPalindrome("iassai"));
        System.out.println(palindromes.isPalindrome("serserseriassaierddd"));
        System.out.println(palindromes.isPalindrome("lililili"));
        System.out.println(palindromes.isPalindrome("amanaplanalpanama"));
        System.out.println(palindromes.containsPalindrome("saisai"));
        System.out.println(palindromes.containsPalindrome("iassai"));
//        System.out.println(palindromes.containsPalindrome("serserseriassaierddd"));
//        System.out.println(palindromes.containsPalindrome("lililili"));
//        System.out.println(palindromes.containsPalindrome("amanaplanalpanama"));
        System.out.println(palindromes.isPalindromeWithSplChars("A man, a plan, a canal: Panama"));
    }

    private boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;
        int left = 0;
        int right = s.length() - 1;
        return s.charAt(left) == s.charAt(right) && isPalindrome(s.substring(left + 1, right));
    }

    public boolean isPalindromeWithSplChars(String s) {
        s = s.toLowerCase().replaceAll("\\W", "");
        System.out.println(s);
        return isPalindromeHelper(s) ;
    }

    private boolean isPalindromeHelper(String s) {
        if (s.length() <= 1) return true;
        int left = 0;
        int right = s.length() - 1;
        return s.charAt(left) == s.charAt(right) && isPalindromeHelper(s.substring(left + 1, right));
    }

    private boolean containsPalindrome(String s) {
        List<String> palindromes = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 1; i < s.length() - 1; i++) {
            int left = i;
            int right = i;

            boolean isStillPalindrome = true;
            while (isStillPalindrome) {
                // move right and count stuff, when count is 0 and left - right is not 1, we might have a Palindrome
                isStillPalindrome = s.charAt(left - 1) == s.charAt(right + 1);
                left--;
                right++;
            }
            palindromes.add(s.substring(left, right));
        }
        System.out.println(palindromes);
        return false;
    }

    private int countPalindromes(String s) {
        return 0;
    }
}
