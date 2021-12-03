package fb_recent;

import java.util.HashMap;
import java.util.Map;

public class AlienDictionary {
    public boolean isAlienSorted(String[] words, String order) {
        Map<Character, Integer> alienOrderMap = new HashMap<>();
        char[] charArray = order.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            Character c = charArray[i];
            alienOrderMap.put(c, i);
        }
        alienOrderMap.put(' ', -1000);

        for (int i = 0; i < words.length - 1; i++) {
            String firstWord = words[i];
            String secondWord = words[i + 1];
            if (!inOrder(firstWord, secondWord, alienOrderMap)) {
                return false;
            }
        }
        return true;
    }

    private boolean inOrder(String firstWord, String secondWord, Map<Character, Integer> alienOrderMap) {
        int maxLength = Math.max(firstWord.length(), secondWord.length());
        firstWord = String.format("%-" + maxLength+ "s", firstWord);
        secondWord = String.format("%-" + maxLength+ "s", secondWord);

        System.out.printf("comparing `%s`  and `%s`\n", firstWord, secondWord);

        for (int i = 0; i < secondWord.length(); i++) {
            char f1 = firstWord.charAt(i);
            char s1 = secondWord.charAt(i);

            Integer firstWordValue = alienOrderMap.get(f1);
            Integer secondWordValue = alienOrderMap.get(s1);
            if (firstWordValue < secondWordValue) return true;
            if (firstWordValue > secondWordValue) return false;
        }
        return true;
    }

    public static void main(String[] args){
        AlienDictionary alienDictionary = new AlienDictionary();
        String[] words = {"hello","leetcode"};
        String order = "hlabcdefgijkmnopqrstuvwxyz";
        System.out.println(alienDictionary.isAlienSorted(words, order));

        String[] words2 = {"word","world","row"};
        String order2 = "worldabcefghijkmnpqstuvxyz";
        System.out.println(alienDictionary.isAlienSorted(words2, order2));

        String[] words3 = {"apple","app"};
        String order3 = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(alienDictionary.isAlienSorted(words3, order3));
    }
}
