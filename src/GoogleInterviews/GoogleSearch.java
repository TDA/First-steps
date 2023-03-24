package GoogleInterviews;

import fb_recent.Trie;

import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/16.
 */
public class GoogleSearch {
    public static void main(String[] args) {
        Trie words = new Trie();
        words.insert("sai");
        words.insert("nancy");
        words.insert("nandu");
        words.insert("sanju");
        words.insert("sam");
        words.insert("sri");
        words.insert("lux");
        words.insert("namratha");
        words.insert("lucas");
        words.insert("luke");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            if ("quit".equals(s)) {
                break;
            }
            System.out.println(words.suffixes(s));
        }
    }
}
