package GoogleInterviews;

/**
 * Created by schandramouli on 10/14/16.
 */
public class GCD {
    public static int findGCD(int m, int n) {
        if (n < 0 || m < 0) {
            return 0;
        }
        if (n == 0) {
            return m;
        }
        System.out.println("Received " + m + " " + n);
        if (n > m) {
            // switch if lesser
            int temp = m;
            m = n;
            n = temp;
        }
        int r = m % n;
        System.out.println(r);
        if (r != 0) {
            m = n;
            n = r;
            return findGCD(m, n);
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println(findGCD(198, 99));
    }
}
