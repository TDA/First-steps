import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/15.
 */
public class FindSquaresBetweenNos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            int count = 0;
            double sqrt = Math.sqrt(start);
            int k = (int) Math.floor(sqrt);
            int m = (int) Math.floor(Math.sqrt(end));
            for (int j = k; j <= m; j++) {
                int j2 = j * j;
                //System.out.println(j2);
                if(j2 >= start && j2 <= end) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}
