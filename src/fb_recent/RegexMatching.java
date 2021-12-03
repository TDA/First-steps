package fb_recent;

import java.util.regex.Pattern;

public class RegexMatching {
    public boolean isMatch(String s, String p) {
        return isMatchHelper(s, p);
    }

    private boolean isMatchHelper(String s, String p) {
        if (0 == p.length()) { // reached ends of both
            return s.length() == 0;
        }
        if (!p.contains("*") && !p.contains(".")) {
            return s.equals(p);
        } else {
            if (p.length() > 1 && p.charAt(1) == '*') {
                if (isMatchHelper(s, p.substring(2))) { // if we count zero of p[0], does it still work?
                    return true;
                }
                if (isLetterMatch(s, p)) {
                    return isMatchHelper(s.substring(1), p); // we want 1 or more occurrences of p[0]
                }
            } else {
                if (isLetterMatch(s, p)) {
                    return isMatchHelper(s.substring(1), p.substring(1));
                }
            }
            return false;
        }
    }

    private boolean isLetterMatch(String s, String p) {
        return s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0));
    }

    public boolean isMatchRealWorld(String s, String p) {
        Pattern pattern = Pattern.compile(p);
        return pattern.matcher(s).matches();
    }

    public static void main(String[] args){
        RegexMatching regexMatching = new RegexMatching();
        System.out.println(regexMatching.isMatch("aa", "a"));
        System.out.println(regexMatching.isMatch("aa", "a*"));
        System.out.println(regexMatching.isMatch("aab", "c*a*b"));
        System.out.println(regexMatching.isMatch("mississippi", "mis*is*p*."));
    }
}
