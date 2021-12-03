package fb_recent;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GoatLatin {
    Set<Character> vowels = new HashSet<>() {{
        add('a');
        add('e');
        add('i');
        add('o');
        add('u');
    }};
    public String toGoatLatin(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if (vowels.contains(s.toLowerCase(Locale.ROOT).charAt(0))) {
                s += "ma";
            } else {
                s = s.substring(1) + s.charAt(0) + "ma";
            }
            for (int j = 0; j < i + 1; j++) {
                s += "a";
            }
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    public static void main(String[] args){
        GoatLatin goatLatin = new GoatLatin();
        String sentence = "I speak Goat Latin";
        System.out.println(goatLatin.toGoatLatin(sentence));
    }
}
