import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class Pangrams {
    public static void main(String[] args) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine().toLowerCase();
        // hashmap ftw!
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        for(char c : alphabets.toCharArray()) {
            map.put(c, 0);
        }

        System.out.println(checkIfPangram(map, a));
    }

    public static String checkIfPangram(Map<Character, Integer> map, String a) {
        // store each letter in hashmap, if all letters are present, then pangram
        for (int i = 0; i < a.length(); i++) {
            char key = a.charAt(i);
            // ascii for space
            if (key == 32)
                continue;
            // map has to have key if its alphabet
            int x = map.get(key);
            map.put(key, ++x);

        }

        for (int k : map.values()) {
            if (k == 0) {
                return "not pangram";
            }
        }
        return "pangram";
    }
}
