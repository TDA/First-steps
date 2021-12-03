package fb_recent;

public class Subsequence {
    public boolean isSubsequence(String s, String t) {

        if (s.length() > t.length()) return false;
        int i = 0;
        int j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                // mismatch, s not found in t, move t
                j++;
            }
        }
        return i == s.length();
    }
}
