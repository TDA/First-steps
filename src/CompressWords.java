import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 8/25/15.
 */
public class CompressWords {
    public static void main(String [] args) {

        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String a = compress(s);
        System.out.println(a);
        String b = compressWithHM(s);
        System.out.println(b);
    }
    public static String compress(String s) {
        char currentLetter = s.charAt(0);
        int letterCount = 1;
        String finalString = "";
        for (int i = 1; i < s.length(); i++) {
            char newLetter = s.charAt(i);
            if(newLetter == currentLetter) {
                letterCount++;
            } else {
                finalString += currentLetter + "";
                if (letterCount > 1)
                    finalString += letterCount;
                letterCount = 1;
                currentLetter = newLetter;
            }
        }
        finalString += currentLetter + "";
        if (letterCount > 1)
            finalString += letterCount;
        return finalString;
    }

    public static String compressWithHM(String s) {
        // Apparently LinkedHashMap maintains insertion order yay!
        LinkedHashMap<Character, Integer> hm = new LinkedHashMap<>();
        String finalString = "";
        for (int i = 0; i < s.length(); i++) {
            if(hm.containsKey(s.charAt(i))) {
                hm.put(s.charAt(i), hm.get(s.charAt(i)) + 1);
            } else {
                hm.put(s.charAt(i), 1);
            }
        }
        System.out.println(hm);
        for (Character character : hm.keySet()) {
            finalString += character;
            if(hm.get(character) > 1) {
                finalString += hm.get(character);
            }
        }
        return finalString;
    }
}
