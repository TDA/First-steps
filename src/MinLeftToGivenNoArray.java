import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/15.
 */
public class MinLeftToGivenNoArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();

        }
        int max = array[0];
        int[] count = new int[n];
        count[0] = 0;
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                count[i] = i;
                maxIndex = i;
                max = array[i];
            } else {
                count[i] = 0;
            }
        }
        System.out.println(maxIndex);

    }
}
