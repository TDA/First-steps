import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 9/7/15.
 */
public class FlamesGame {
    public static final HashMap<Character, String> DICTIONARY;
    static
    {
        DICTIONARY = new HashMap<Character, String>();
        DICTIONARY.put('f', "Friendship");
        DICTIONARY.put('l', "Love");
        DICTIONARY.put('a', "Affection");
        DICTIONARY.put('m', "Marriage");
        DICTIONARY.put('e', "Enemity");
        DICTIONARY.put('s', "Sister");
        System.out.println("And this is how you do it Sujata ;)");
    }


    public int count (HashMap<Character, Integer> hm) {
        int n = 0;
        for(int value : hm.values()) {
            n = n + value;
        }
        return n;
    }

    public static String cleanseString (String s) {
      return s.toLowerCase().replaceAll("\\s", "");
    }

    public void populateHashMapWithCharacters (HashMap<Character, Integer> hm, String s) {
        for (char c: s.toCharArray()) {
            if (hm.get(c) != null) {
                hm.put(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }
    }

    public void xorHashMapWithNewCharacters (HashMap<Character, Integer> hm, String s2) {
        for (char c: s2.toCharArray()) {
            if (hm.get(c) != null && hm.get(c) > 0) {
                hm.put(c, hm.get(c) - 1);
            } else {
                hm.put(c, 1);
            }
        }
    }

    public void prettyPrintHashMap (HashMap<Character, Integer> hm) {
        System.out.println("{");
        for (Character c : hm.keySet()) {
            System.out.println("\t" + c + " : " + hm.get(c));
        }
        System.out.println("}");
    }

    public static void main(String[] args) {

        String s1 = "";
        String s2 = "";
        Scanner scanner = new Scanner(System.in);
        s1 = cleanseString(scanner.nextLine());
        s2 = cleanseString(scanner.nextLine());

        FlamesGame flames = new FlamesGame();
        HashMap<Character, Integer> hm = new HashMap<>();

        flames.populateHashMapWithCharacters(hm, s1);

        flames.prettyPrintHashMap(hm);
        flames.xorHashMapWithNewCharacters(hm, s2);
        flames.prettyPrintHashMap(hm);

        int matches = flames.count(hm);
        //System.out.println(matches);

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

        System.out.println(DICTIONARY.get(sb1.charAt(0)));
    }
}
