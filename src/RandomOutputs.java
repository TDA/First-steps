import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 1/14/16.
 */
public class RandomOutputs {
    public static void main(String[] args) {
        // this will return random letters using a hashmap
        HashMap<Integer, Integer> defaultHashMap = new HashMap<>();
        for (int i = 1; i < 27; i++) {
            // cast to int, generate a random hashmap
            defaultHashMap.put(i, (int) Math.floor(Math.random() * 27));
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
            stringBuilder.append(alphabets[(int) defaultHashMap.get(x) - 1]);
        }

        // encryption? encrypted string here by pure randomization
        System.out.println(stringBuilder);

        // can we decrypt this? or is it a one way thing?
        for (char c: stringBuilder.toString().toCharArray()) {
            // get the position of the char in the alphabets array
            int index = Arrays.binarySearch(alphabets, c);
            // get the key corresponding to the value
            for (Map.Entry entry : defaultHashMap.entrySet()) {
                if ((int) entry.getValue() == index) {

                }
            }
        }

    }
}
