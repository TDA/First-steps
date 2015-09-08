import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 9/7/15.
 */
public class FlamesGame {
    public static void main(String[] args) {
        String s1 = "";
        String s2 = "";
        Scanner scanner = new Scanner(System.in);
        s1 = scanner.nextLine();
        s2 = scanner.nextLine();
        HashMap<Character, Integer> hm = new HashMap<>();
        for (char c: s1.toCharArray()) {
            if (hm.get(c) > 0) {
                hm.put(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }
        System.out.println(hm);
    }
}
