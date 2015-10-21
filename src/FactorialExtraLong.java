import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by schandramouli on 10/20/15.
 */
public class FactorialExtraLong {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        BigInteger b = factorial(n);
        System.out.println(b);
    }

    public static BigInteger factorial(int n) {
        if (n <= 1) {
            return new BigInteger("1");
        }
        return factorial(n - 1).multiply(new BigInteger("" + n));
    }
}
