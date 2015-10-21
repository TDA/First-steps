import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/15.
 */
public class AngryProfClassCancelled {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int testCases = s.nextInt();

        while (testCases > 0) {
            int negatives = 0;
            int i = 0;
            int n = s.nextInt();
            int k = s.nextInt();

            while (i < n) {
                int no = s.nextInt();
                if (no <= 0) {
                    // no of people who came in
                    negatives++;
                }
                i++;
            }
            if (negatives >= k) {
                // class does NOT get cancelled
                System.out.println("NO");
            } else {
                // class gets cancelled
                System.out.println("YES");
            }
            testCases--;
        }
    }
}
