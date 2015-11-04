import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/2/15.
 */
public class GreedyBurgers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.parseInt(scanner.nextLine());
        Integer[] keyArray = new Integer[T];
        Integer[] valueArray = new Integer[T];
        for (int i = 1; i <= T; i++) {
            // for 1 to T
            String s = scanner.nextLine();
            int orderTime = Integer.parseInt(s.split(" ")[0]);
            int processTime = Integer.parseInt(s.split(" ")[1]);
            int totalTime = orderTime + processTime;
            keyArray[i - 1] = i;
            valueArray[i - 1] = totalTime;
        }

        // sort the values array
        for (int i = 0; i < valueArray.length; i++) {
            for (int j = i + 1; j < valueArray.length; j++) {
                if (valueArray[i] > valueArray[j]) {
                    //swap the nos and their pos
                    // while sorting, break ties by
                    // earlier fan ==> keyarray plays a part too
                    int temp = valueArray[i];
                    valueArray[i] = valueArray[j];
                    valueArray[j] = temp;
                    //swap pos
                    temp = keyArray[i];
                    keyArray[i] = keyArray[j];
                    keyArray[j] = temp;

                } else if (valueArray[i].equals(valueArray[j])) {
                    //System.out.println("ZOMG EVERYTHING SIS EQUAL" + valueArray[i] + " " + valueArray[j]);
                    // now we need to sort by keys
                    if (keyArray[i] > keyArray[j]) {
                        //swap pos alone, values are already equal
                        int temp = keyArray[i];
                        keyArray[i] = keyArray[j];
                        keyArray[j] = temp;

                    }
                }
            }
        }
        //System.out.println();
        for (int x = 0; x < keyArray.length - 1; x++) {
            System.out.print(keyArray[x] + " ");
        }
        System.out.print(keyArray[keyArray.length - 1]);

    }
}
