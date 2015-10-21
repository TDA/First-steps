import java.util.Scanner;

/**
 * Created by schandramouli on 10/20/15.
 */
public class LargeAddition {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int i = 0;
        long sum = 0;
        while (s.hasNext()) {
            long no = s.nextLong();
            sum += no;
        }
        System.out.println(sum);
    }
}
