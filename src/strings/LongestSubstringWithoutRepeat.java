package strings;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeat {
    public int lengthOfLongestSubstringV2(String s) {
        int max = 0;
        int left = 0, right = 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            char c = s.charAt(right);
            if (!set.contains(c)) {
                set.add(c);
            } else {
                max = Math.max(max, right-left);
                while (s.charAt(left) != c) {
                    set.remove(s.charAt(left));
                    left++;
                }
                left++; // no need to remove s.charAt(left)
            }
            right++;
        }
        return Math.max(max, right-left);
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        int leftPointer = 0, rightPointer = 0;
        Set<Character> set = new HashSet<>();
        int longest = 0;
        while (rightPointer < s.length()) {
            char c = s.charAt(rightPointer);
            if (!set.contains(c)) {
                set.add(c);
            } else {
                longest = Math.max(longest, rightPointer - leftPointer);
                while (s.charAt(leftPointer) != c) {
                    set.remove(s.charAt(leftPointer));
                    leftPointer++;
                }
                leftPointer++;
            }
            // move right pointer
            rightPointer++;
        }
        return Math.max(longest, rightPointer - leftPointer);
    }

    public static void main(String[] args){
        LongestSubstringWithoutRepeat longestSubstringWithoutRepeat = new LongestSubstringWithoutRepeat();
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("bbbbb"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("pwwkew"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring(""));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring(" "));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("au"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("dvdf"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("xdvdf"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstring("aab"));
        System.out.println("---------------------------------");
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("abcabcbb"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("bbbbb"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("pwwkew"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2(""));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2(" "));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("au"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("dvdf"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("xdvdf"));
        System.out.println(longestSubstringWithoutRepeat.lengthOfLongestSubstringV2("aab"));
    }
}
