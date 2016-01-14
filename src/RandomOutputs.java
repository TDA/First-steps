import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 1/14/16.
 */
public class RandomOutputs {
    // basically a one-time pad :D
    // so will still have to think of key transmission
    // not ideal, but hey, this was a fun push :D
    // already done 8 pushes to private repos today :P
    public static void main(String[] args) {
        // this will return random letters using a hashmap
        // nice use for the default hashmaps :D good going prusse
        DefaultHashMap<Integer, Integer> defaultHashMap = new DefaultHashMap<>(1111);
        for (int i = 0; i < 26; i++) {
            // cast to int, generate a random hashmap
            // TODO: avoid duplicates, and this will be a beautiful hash function
            defaultHashMap.put(i, (int) Math.floor(Math.random() * 26));
        }

        System.out.println(defaultHashMap);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a word");


        char[] alphabets = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        String input = scanner.nextLine();

        StringBuilder stringBuilder = new StringBuilder();
        for (char c: input.toLowerCase().toCharArray()) {
            // get the random maps, and print those instead
            int x = ((int) c) - 97;
            System.out.println(x);
            stringBuilder.append(alphabets[(int) defaultHashMap.get(x)]);
        }

        // encryption? encrypted string here by pure randomization
        System.out.println(stringBuilder);

        StringBuilder originalString = new StringBuilder();
        // can we decrypt this? or is it a one way thing?
        for (char c: stringBuilder.toString().toCharArray()) {
            // get the position of the char in the alphabets array
            int index = Arrays.binarySearch(alphabets, c);
            System.out.println("Index: " + index);
            // get the key corresponding to the value
            int key = 0;
            for (Map.Entry<Integer, Integer> entry : defaultHashMap.entrySet()) {
                if ((int) entry.getValue() == index) {
                    key = (int) entry.getKey();
                    break;
                }
            }
            // now use the key to get the original character
            System.out.println("Key: " + key);
            char f = (char) (key + 97);
            originalString.append(f);
        }

        System.out.println(originalString);
    }
}
