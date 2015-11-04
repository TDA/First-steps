import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/3/15.
 */
public class bigFactorial {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigInteger a = BigInteger.valueOf(scanner.nextInt());
        BigInteger b = BigInteger.valueOf(scanner.nextInt());
        BigInteger c = BigInteger.ZERO;
        int n = scanner.nextInt();
        //System.out.println(n);
        for (int i = 2; i < n; i++) {
            //System.out.println(a);
            //System.out.println(b);

            c =  a.add(b.multiply(b));
            a = b;
            b = c;
        }
        System.out.println(c);
    }
}
