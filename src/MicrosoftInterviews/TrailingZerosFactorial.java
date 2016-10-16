package MicrosoftInterviews;

/**
 * Created by schandramouli on 10/15/16.
 */
public class TrailingZerosFactorial {
    public static int trailingZeroes(int a) {
        if (a <= 4) {
            return 0;
        }
        // idea is that we can count only the numbers whose multiples which will result in a 0
        // but logarithmic complexity? might be tough
        // to avoid this, we need to avoid computing the factorial
        int countFives = 0;
        for (int i = 1; Math.pow(5, i) <= a; i++) {
            int multipleFives = ((int) Math.pow(5, i));
            countFives += a / multipleFives;
        }
        return countFives;
    }

    public static long factorial(int a) {
        long factorial = 1;
        for (int i = 2; i <= a; i++) {
            factorial *= i;
        }
        return factorial;
    }

    public static void main(String[] args) {
        System.out.println(trailingZeroes(9247));
    }
}
