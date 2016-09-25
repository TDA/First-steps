import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by schandramouli on 9/24/16.
 */
public class PrimeNumbers {
    public static int isPrime(int a) {
        // 1 if prime, 0 if not
        if (a <= 1) {
            return 0;
        }
        if (a == 2) {
            return 1;
        }
        if (a % 2 == 0) {
            return 0;
        }
        boolean isPrime = true;
        for (int i = 3; i <= Math.sqrt(a); i+=2) {
            if (a % i == 0 && a != i) {
                isPrime = false;
                break;
            }
            isPrime = true;
        }
        return isPrime? 1 : 0;
    }

    public static ArrayList<Integer> sieve(int a) {
        if (a <= 1) {
            return null;
        }
        int[] primes = new int[a + 1];
        Arrays.fill(primes, 1);
        primes[0] = primes[1] = 0;
        ArrayList<Integer> primesList =  new ArrayList<>();
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (isPrime(i) == 1) {
                for (int j = 2; i * j <= a; j++) {
                    primes[i * j] = 0;
                }
            }
        }

        for (int i = 2; i < primes.length; i++) {
            if (primes[i] == 1) {
                primesList.add(i);
            }
        }

        return primesList;
    }

    public static String getBinaryString(int a) {
        return Integer.toBinaryString(a);
    }

    public static void main(String[] args) {
//        System.out.println(isPrime(3));
//        System.out.println(sieve(39601));
//        System.out.println(sieve(10));
        System.out.println(getBinaryString(10));
    }
}
