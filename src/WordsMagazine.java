import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/23/15.
 */
public class WordsMagazine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String magazine = scanner.nextLine();
        String message = scanner.nextLine();
        HashMap<String, Integer> magazineWords = new HashMap<>();


        for (String word : magazine.split(" ")) {
            // split the words and store them in the hashmap
            if (magazineWords.containsKey(word)) {
                // already added, update
                magazineWords.put(word, magazineWords.get(word) + 1);
            } else {
                // hasnt been added yet
                magazineWords.put(word, 1);
            }
        }

        //System.out.println(magazineWords);
        if (isMessagePossible(message, magazineWords)) {
            System.out.println("Yay! Possible");
        }

    }

    public static boolean isMessagePossible(String message, HashMap<String, Integer> magazineWords) {
        for (String word : message.split(" ")) {
            // check if each word is possible
            if (magazineWords.containsKey(word) && magazineWords.get(word) > 0) {
                magazineWords.put(word, magazineWords.get(word) - 1);
            } else {
                // doesnt exist, cant form message
                // or contains word, but not more than 0
                System.out.println("Not possible to form the message, missing " + word);
                return false;
            }
        }
        return true;
    }
}
