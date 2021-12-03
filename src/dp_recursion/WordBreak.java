package dp_recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {
    // Array is extremely fast compared to Hashmap.. but consumes way more space
    public boolean wordBreak(String s, List<String> wordDict) {
        Boolean[] booleans = new Boolean[s.length()];
        Set<String> wordSet = new HashSet<>(wordDict);
        return helper(s, 0, wordSet, booleans);
    }

    private boolean helper(String s, int index, Set<String> wordSet, Boolean[] canSplitAtIndex) {
        System.out.println(s.substring(index));
        if (index == s.length()) return true;
        if (canSplitAtIndex[index] != null) return canSplitAtIndex[index];

        boolean res = false;

        for (int i = index + 1; i <= s.length(); i++) {
            String substring = s.substring(index, i);
            if (!wordSet.contains(substring)) continue;
            if (helper(s, i, wordSet, canSplitAtIndex)) {
                res = true;
                break;
            }
        }
        canSplitAtIndex[index] = res;
        return canSplitAtIndex[index];
    }

    public boolean wordBreakWeak(String s, List<String> wordDict) {
        boolean isMatch = false;
        if (s.equals("")) return true;
        String substring;
        for (String word: wordDict) {
            if (s.startsWith(word)) {
                substring = s.substring(s.indexOf(word) + word.length());
                System.out.println(substring);
                isMatch = isMatch || wordBreakWeak(substring, wordDict);
            }
        }
        return isMatch;
    }

    public static void main(String[] args){
        WordBreak wordBreak = new WordBreak();
        System.out.println(wordBreak.wordBreak("leetcode", Arrays.asList("leet", "code")));
        System.out.println(wordBreak.wordBreak("applepenapple", Arrays.asList("apple","pen")));
        System.out.println(wordBreak.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
        System.out.println(wordBreak.wordBreak("cars", Arrays.asList("car","ca","rs")));
    }
}
