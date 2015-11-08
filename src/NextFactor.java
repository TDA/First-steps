import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/8/15.
 */
public class NextFactor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[] primeFactorArray = factorize(num);
        System.out.println(Arrays.toString(primeFactorArray));
        // set an upper bound on the next number
        int upperBound = num * primeFactorArray[0];
        // im sure this can be simplified as follows:
        // find lcm of the factors, and add it to the number :\
        // i think i over complicated this
        System.out.printf("Next number divisible by these factors should be between %s and %s\n", num, upperBound);
//        int computations = 0;
//        int i;
//        for (i = num + primeFactorArray[0]; i <= upperBound; i += primeFactorArray[0]) {
//            int count = 0;
//            for (int j = 0; j < primeFactorArray.length; j++) {
//                computations++;
//                if (i % primeFactorArray[j] == 0) {
//                    count++;
//                }
//            }
//            if (count == primeFactorArray.length) {
//                break;
//            }
//        }
        // was right, super simple
        int lcm = 1;
        for (Integer i : primeFactorArray) {
            lcm *= i;
        }
        System.out.printf("Next number is %s\n", num + lcm);
//        System.out.printf("Total computations is %s", computations);
    }
    public static int[] factorize(int number) {
        ArrayList<Integer> primeFactorArrayList = new ArrayList<>();
        if (number % 2 == 0) {
            primeFactorArrayList.add(2);
        }
        for (int i = 3; i < Math.sqrt(number); i = i + 2) {
            if (number % i == 0) {
                primeFactorArrayList.add(i);
            }
        }
        int[] array = new int[primeFactorArrayList.size()];
        int i = 0;
        for (Integer integer : primeFactorArrayList) {
            array[i] = integer;
            i++;
        }
        return array;
    }
}
