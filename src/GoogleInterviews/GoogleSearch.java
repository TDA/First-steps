package GoogleInterviews;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/16.
 */
public class GoogleSearch {
    public static void main(String[] args) {
        PatriciaTrie words = new PatriciaTrie<>();
        words.put("sai", new Integer(1));
        words.put("nancy", new Integer(2));
        words.put("nandu", new Integer(3));
        words.put("sanju", new Integer(4));
        words.put("sam", new Integer(5));
        words.put("sri", new Integer(6));
        words.put("lux", new Integer(7));
        words.put("namratha", new Integer(8));
        words.put("lucas", new Integer(9));
        words.put("luke", new Integer(10));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            if ("quit".equals(s)) {
                break;
            }
            System.out.println(words.prefixMap(s));
        }
    }
}
