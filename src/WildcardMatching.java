public class WildcardMatching {
    public boolean isMatch(String s, String p) {
        if (p.length() == 0 || s.length() == 0 || s.length() != p.length()) {
            if (!p.contains("*")) return false;
        }
        if (p.equals("*")) return true;

        int i = 0, j = 0;
//        if (p.startsWith("*")) {
//            // wind forward to first match
//            while (i < s.length() && s.charAt(i) != p.charAt(j + 1)) i++;
//            j++;
//        }
        while (i < s.length() && j < p.length()) {
            char sLetter = s.charAt(i);
            char pLetter = p.charAt(j);
            if (sLetter == pLetter || pLetter == '?' || pLetter == '*') {
                // matched one letter, need to move
                i++;
                if (pLetter == '*') {
                    // found a wildcard, need to keep moving till we find next letter match
                    if (j == p.length() - 1) return true;
                    else {
                        // skip all wildcards
                        char nextLetter = p.charAt(j + 1);
                        while (i < s.length() && s.charAt(i) != nextLetter) {
                            i++;
                        }
                    }
                }
                j++;
            } else {
                break;
            }
        }
        if (i == s.length()) {
            // if we either matched everything, we are done
            if (j == p.length()) return true;
            // if the only items left in j are *, then we are done as well
            while (p.charAt(j) == '*') j++;
            return j == p.length();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new WildcardMatching().isMatch("aa", "?a"));
        System.out.println(new WildcardMatching().isMatch("aa", "*"));
        System.out.println(new WildcardMatching().isMatch("aa", "?c"));
        System.out.println(new WildcardMatching().isMatch("adceb", "a*e*b*"));
        System.out.println(new WildcardMatching().isMatch("adceb", "*a*b"));
        System.out.println(new WildcardMatching().isMatch("acdcb", "a*c?b"));
    }
}
