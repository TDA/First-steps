import java.util.Arrays;

/**
 * Created by schandramouli on 9/6/16.
 */
public class WordReversal {
    public static void main(String[] args) {
        String s = "Sai is a boy";
        // ex output is "iaS si a yob"
        String[] words = s.split(" ");
        String[] wordsRev = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            wordsRev[i] = reverse(words[i]);
        }
        System.out.println(String.join(" ", wordsRev));
    }

    public static String reverse(String w) {
        StringBuilder builder = new StringBuilder(w);
        return builder.reverse().toString();
    }
}
