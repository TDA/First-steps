import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class HappyNos {
    public static void main(String[] args) {
        int n = 1;
        HashMap<Integer, Integer> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        //for (int i = 0; i < n; i++) {
            int count = 0;
            Integer number = scanner.nextInt();
            int sum = 0;
            while (true) {
                System.out.println(number);
                for (String s : (number + "").split("")) {
                    int digit = Integer.parseInt(s);
                    //System.out.println(digit);
                    sum += digit * digit;
                }
                //System.out.println();
                count++;
                if (sum == 1) {
                    // means a happy number
                    System.out.println("happy " + count);
                    break;
                } else {
                    // continue on with next iteration
                    number = sum;
                    sum = 0;
                }
            }
        //}
    }
}
