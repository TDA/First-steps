import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 9/7/15.
 */
public class FlamesGame {
    public static final HashMap<Character, String> DICTIONARY;
    static
    {
        // Lets initialize the Dictionary here, not like its gonna change
        DICTIONARY = new HashMap<Character, String>();
        DICTIONARY.put('f', "Friendship");
        DICTIONARY.put('l', "Love");
        DICTIONARY.put('a', "Affection");
        DICTIONARY.put('m', "Marriage");
        DICTIONARY.put('e', "Enemity");
        DICTIONARY.put('s', "Sister");

        // Message for Sujata, everyone else, kindly disregard.
        System.out.println("And this is how you do it Sujata ;)");
    }

    // Have to make this generic someday, as in, any HashMap key, but integer value.
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

    // This is one hella useful function, good going prusse :) Basically can be
    // reused with anything that requires a HashMap based solution including Phonebook,
    // finding repeated/unrepeated chars, unique chars etc
    public void populateHashMapWithCharacters (HashMap<Character, Integer> hm, String s) {
        for (char c: s.toCharArray()) {
            if (hm.get(c) != null) {
                hm.put(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }
    }

    // aha, now all we need to do is find a use for this xor :D may be a greatly useful thingy :D
    public void xorHashMapWithNewCharacters (HashMap<Character, Integer> hm, String s2) {
        for (char c: s2.toCharArray()) {
            // need to change this check > 0 for removing the bug that occurs
            // when names are interchanged.
            if (hm.get(c) != null) {
                hm.put(c, hm.get(c) - 1);
            } else {
                // theres a bug here, right here. If the second name has more than 2 copies of a single letter,
                // say `i`, and the first name didnt have it even once, the count would get screwed. Need to
                // change this. eg: sai and saippp, actual count should be 3, but will be 1 (abs of -1).
                hm.put(c, 1);
            }
        }
    }

    public void absoluteValuesHashMap (HashMap<Character, Integer> hm) {
        for (Character c : hm.keySet()) {
            if (hm.get(c) < 0) {
                // absolute value
                hm.put(c, -hm.get(c));
            }
        }
    }

    // not really useful, but, but.. i wrote it :O :'(
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

        // lets get some OOPS
        FlamesGame flames = new FlamesGame();
        // lets not oops this, cuz then we would have to type flames.hm EVERY frigging time :(
        // or not pass the hm at all, and use it straight in the methods, sounds ok.
        // Nah, not really. Lets leave it `as is` prusse.
        HashMap<Character, Integer> hm = new HashMap<>();

        flames.populateHashMapWithCharacters(hm, s1);

        flames.prettyPrintHashMap(hm);
        flames.xorHashMapWithNewCharacters(hm, s2);
        flames.absoluteValuesHashMap(hm);
        flames.prettyPrintHashMap(hm);

        int matches = flames.count(hm);
        System.out.println(matches);

        StringBuilder sb1 = new StringBuilder();

        // lets keep the flames thing in the DICTIONARY and reuse that here
        // ergo less readable, but easier to change if i choose to have `flamemasterdragonkiller` or something
        // nvm, hashmaps dont return keys in order, lets just use a literal.
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
