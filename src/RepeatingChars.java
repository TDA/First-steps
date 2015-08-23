import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 8/22/15.
 */
public class RepeatingChars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        HashMap<Character, Integer> hm = new HashMap<>();
        String alphabets = "abcdefghijklmnopqrstuvwxyz1234567890";
        for(char c : alphabets.toCharArray()) {
            hm.put(c, 0);
        }
        System.out.println(hm);
        for (int i = 0; i < s.length(); i++) {
            if(hm.get(s.charAt(i)) > 0) {
                System.out.println("First repeating char is :" + s.charAt(i));
                break;
            } else {
                hm.put(s.charAt(i), 1);
            }
        }
    }
}
