import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 8/22/15.
 */
public class NonRepeatingChars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        HashMap<Character, Integer> hm = new HashMap<>();
        String alphabets = "abcdefghijklmnopqrstuvwxyz1234567890";
        for(char c : alphabets.toCharArray()) {
            hm.put(c, 0);
        }
        for (int i = 0; i < s.length(); i++) {
            if(hm.get(s.charAt(i)) > 0) {
                hm.put(s.charAt(i), hm.get(s.charAt(i)) + 1);
            } else {
                hm.put(s.charAt(i), 1);
            }
        }
        //System.out.println(hm);
        int first = s.length();
        for(Character c: hm.keySet()) {
            if (hm.get(c) == 1) {
                System.out.println(c);
                first = Math.min(first, s.indexOf(c));
            }
        }
        if(first < s.length()) {
            System.out.println("The first non repeating char is " + s.charAt(first));
        } else {
            System.out.println("No non repeating chars");
        }
    }
}
