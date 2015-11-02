import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class HappyNos {
    public static void main(String[] args) {
        int n = 1;
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> map = new HashMap<>();
            int count = 0;
            Integer number = scanner.nextInt();
            if (number == 1) {
                System.out.println("happy " + count);
                continue;
            }
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
                } else if (map.get(sum) != null && map.get(sum) == 1) {
                    // if repeating
                    System.out.println("unhappy " + (count));
                    break;
                } else {
                    // continue on with next iteration
                    map.put(sum, 1);
                    number = sum;
                    // reset sum
                    sum = 0;
                }
            }
            System.out.println(map);
        }
    }
}
