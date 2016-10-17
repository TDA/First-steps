import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Permutations {
    static Set<String> stringSet = new HashSet<>();
    static int recCount;
    public static void main(String[] args) {
        String s = "abcdef";
        System.out.println(findPermutations(s));
        System.out.println(recCount);
    }

    static ArrayList<String> findPermutations(String string) {
        // recCount++;
        ArrayList<String> stringArrayList = new ArrayList<>();
        // just a letter, so return it
        if (string.length() == 1) {
            stringArrayList.add(string);
            return stringArrayList;
        }
        // else get the first character, and
        // find the permutations of remaining characters,
        // and insert the first character into each of these permutations
        char first = string.charAt(0);
        String remainderString = string.substring(1);
        // find perms of remaining ones, and insert first char into each of those
        ArrayList<String> tempStringArrayList = findPermutations(remainderString);
        for (String s : tempStringArrayList) {
            for (int i = 0; i <= s.length(); i++) {
                String newString = insertCharAt(s, first, i);
                stringArrayList.add(newString);
            }
        }
        return stringArrayList;
    }

    static String insertCharAt(String s, char letter, int position) {
        return s.substring(0, position) + letter + s.substring(position, s.length());
    }
}
