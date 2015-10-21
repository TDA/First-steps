import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by schandramouli on 10/21/15.
 */
public class FindDigits {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int no = scanner.nextInt();
            int count = 0;
            int[] digits = new int[Integer.toString(no).length()];
            int j = 0;
            for (String c: Integer.toString(no).split("")) {
                if (! "".equals(c)) {
                    digits[j] = Integer.parseInt(c);
                    j++;
                }
            }
            for (int k = 0; k < digits.length; k++) {
                int digit = digits[k];
                if (digit != 0 && no % digit == 0) {
                    count++;
                }
            }
            System.out.println(count);
        }

    }
}
