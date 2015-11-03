import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/2/15.
 */
public class Anagrams {
    // two strings are passed in one string
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String a = scanner.nextLine();
            if (a.length() %2 != 0) {
                System.out.println(-1);
            } else {
                // read each string, store the chars in hashmap
                // hashmap ftw!
                String s1 = a.substring(0, a.length() / 2);
                String s2 = a.substring(a.length() / 2);
                //System.out.println(s1);
                //System.out.println(s2);
                System.out.println(findDifferenceBetweenStrings(s1, s2));
            }
        }

    }

    public static int findDifferenceBetweenStrings(String s1, String s2) {
        HashMap<Character, Integer> map1 = new HashMap<Character, Integer>();
        HashMap<Character, Integer> map2 = new HashMap<Character, Integer>();
        map1 = storeStringInMap(s1);
        map2 = storeStringInMap(s2);
        System.out.println(map1);
        System.out.println(map2);
        HashMap<Character, Integer> map3 = new HashMap<Character, Integer>();
        if (! map1.equals(map2)) {
            // since the strings arent anagrams, we need
            // to find the least no of chars to be removed
            // from s1 to get an anagram of s2
            // if a character is not in s2, but in s1, we need to change that

            for (Character c: map1.keySet()) {
                if (! map2.containsKey(c) ) {
                    map3.put(c, map1.get(c));
                } else {
                    // this part was not needed, as it will have overlap
                    // key is there in s2 as well, are they equal? if not, how much should they change?
                    int x = map1.get(c);
                    int y = map2.get(c);
                    map3.put(c, Math.abs(x - y));
                }
            }

            System.out.println(map3);

            int countOfLettersInBoth = 0;
            int countOfLettersNotInBoth = 0;
            // needed to remove overlaps
            for (Character c: map3.keySet()) {
                if (map1.containsKey(c) && map2.containsKey(c)) {
                    countOfLettersInBoth += map3.get(c);
                }
                else if (map1.containsKey(c)) {
                    countOfLettersNotInBoth += map3.get(c);
                }
            }
            //overlap
            countOfLettersInBoth = countOfLettersInBoth / 2;

            return countOfLettersInBoth + countOfLettersNotInBoth;
        }
        return 0;
    }

    public static HashMap<Character, Integer> storeStringInMap(String s) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (char key : s.toCharArray()) {
            if (map.containsKey(key)) {
                int x = map.get(key);
                map.put(key, ++x);
            } else {
                map.put(key, 1);
            }
        }
        return map;
    }

    public static int checkForAnagrams(String a) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char key : a.toCharArray()) {
            if (map.containsKey(key)) {
                int x = map.get(key);
                map.put(key, ++x);
            } else {
                map.put(key, 1);
            }
        }
        int noOfOddChars = 0;
        for (Integer integer : map.values()) {
            if (!(integer % 2 == 0)) {
                // check how many chars are odd occurences
                noOfOddChars++;
                // add all the pairs
            }
        }

        if (noOfOddChars > 1) {
            // Not a palindrome, and noOfOddChars - 1 is the min no of elements
            // we need to remove, a palindrome can have at most one odd occuring elt :)
            return (noOfOddChars - 1);
        } else {
            return 0;
        }
    }
}
