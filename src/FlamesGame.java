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
        s1 = scanner.nextLine().toLowerCase().replaceAll("\\s", "");
        s2 = scanner.nextLine().toLowerCase().replaceAll("\\s", "");

        // disregard duplicates --> One s removes all s and so on.
        // need to change that to take into account dupes in the same name,
        // as well as the second name :)
        HashMap<Character, Integer> hm = new HashMap<>();
        for (char c: s1.toCharArray()) {
            if (hm.get(c) != null) {
                hm.put(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }

        for (char c: s2.toCharArray()) {
            if (hm.get(c) != null && hm.get(c) > 0) {
                hm.put(c, hm.get(c) - 1);
            }
        }
        int matches = hm.size();
        System.out.println(matches);
        StringBuilder sb1 = new StringBuilder();
        sb1.append("flames");
        int i = 0;
        while (sb1.length() > 1) {
            i = i + matches - 1; // set the length of chars to skip
            while (i >= sb1.length()) {
                // reset i if its over the length
                // helps to wrap around
                i = i - sb1.length();
            }
            sb1.deleteCharAt(i);
        }

        System.out.println(sb1);
    }
}
