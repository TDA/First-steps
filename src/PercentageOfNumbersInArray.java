import java.util.Scanner;

/**
 * Created by schandramouli on 10/20/15.
 */
public class PercentageOfNumbersInArray {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int zeroes = 0;
        int positives = 0;
        int negatives = 0;
        while (s.hasNextInt()) {
            int no = s.nextInt();
            //System.out.println(no);
            if (no == 0) {
                zeroes++;
            }  else if (no > 0) {
                positives++;
            } else {
                negatives++;
            }
        }
        System.out.println((float) positives / n);
        System.out.println((float) negatives / n);
        System.out.println((float) zeroes / n);

    }
}
