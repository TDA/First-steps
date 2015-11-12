import java.util.Scanner;

/**
 * Created by schandramouli on 11/11/15.
 */
public class ArmstrongNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
        for (int i = 0; i <= limit; i++) {
            int sum = 0;
            for (String c : Integer.toString(i).split("")) {
                sum += (int) Math.pow(Integer.parseInt(c), 3);
            }
            //System.out.println(sum);

            if (sum == i) {
                System.out.println("Found Armstrong number " + i);
            }
        }
    }
}
