import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by schandramouli on 9/24/16.
 */
public class Factors {
    public static ArrayList<Integer> allFactors(int a) {
        ArrayList<Integer> factors = new ArrayList<>();
        if (a < 0) {
            System.out.println("Invalid Input");
            return factors;
        } else if (a == 0) {
            System.out.println("0");
            return factors;
        }
        factors.add(1);
        factors.add(a);
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (a % i == 0) {
                factors.add(i);
                int b = a / i;
                if (b != i) {
                    factors.add(b);
                }
            }
        }
        Collections.sort(factors);
        return factors;
    }

    public static void main(String[] args) {
        System.out.println(allFactors(85463));
    }
}
