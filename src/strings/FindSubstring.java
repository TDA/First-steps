package strings;

public class FindSubstring {
    public int strStr(String haystack, String needle) {
        int haystackLength = haystack.length();
        int needleLength = needle.length();
        if (needleLength == 0) return 0;
        if (haystackLength == 0) return -1;
        int i = 0,
            j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (i >= haystack.length()) {
            if (j < needleLength) {
                return  -1;
            }
        }
        return i - needleLength;
    }

    public int strStrKarp(String haystack, String needle) {
        int haystackLength = haystack.length();
        int needleLength = needle.length();
        if (needleLength == 0) return 0;
        if (haystackLength == 0) return -1;
        int needleHash = getHashValue(needle);
        int windowStart = 0;
        int runningHashValue = 0;
        for (int i = 0; i < needleLength; i++) {
            // add first 3 chars
            runningHashValue += haystack.charAt(i);
        }
        // too many edge cases for Karp impl, don't attempt in interview
        while (windowStart < haystackLength) {
            if (runningHashValue != needleHash) {
                runningHashValue -= haystack.charAt(windowStart);
                if (windowStart + needleLength < haystackLength)
                    runningHashValue += haystack.charAt(windowStart + needleLength);
                windowStart++;
                continue;
            }
            if (haystack.substring(windowStart, windowStart + needleLength).equals(needle)) return windowStart;
            else windowStart++;
        }

        return -1;
    }

    int getHashValue(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new FindSubstring().strStr("sadbutsad", "sad"));
        System.out.println(new FindSubstring().strStrKarp("sadbutsad", "sad"));
        System.out.println(new FindSubstring().strStr("leetcode", "leeto"));
        System.out.println(new FindSubstring().strStrKarp("leetcode", "leeto"));
        System.out.println(new FindSubstring().strStr("abc", "c"));
        System.out.println(new FindSubstring().strStrKarp("abc", "c"));
        System.out.println(new FindSubstring().strStr("a", "a"));
        System.out.println(new FindSubstring().strStrKarp("a", "a"));
        System.out.println(new FindSubstring().strStr("mississippi", "pi"));
        System.out.println(new FindSubstring().strStrKarp("mississippi", "pi"));
    }
}
