import java.util.Scanner;

/**
 * Created by schandramouli on 10/23/15.
 */
public class NumberOnStone {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        for (int i = 0; i < testCases; i++) {
            int n = scanner.nextInt();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            // there can be n^2 final values, n stones, each stone can be a or b.
            int[] finalValues = new int[n * n];
            // starting stone is always zero
            int start = 0;
            int previousStoneValue = 0;
            // basically find all permutations of the values a and b for n=1, n=2 and so on.
            // So lets write a function for that
            for (int j = 1; j < n; j++) {
                // n stones, first stone is 0, so start from 1
                // this needs some form of dynamic programming so
                int[] permutedValues = findPermutations(a, b, previousStoneValue);
                for (int permutedValue : permutedValues) {
                    System.out.println(permutedValue);
                }
                for (int k = 0; k < permutedValues.length; k++) {
                    permutedValues = findPermutations(a, b, permutedValues[k]);
                    for (int permutedValue : permutedValues) {
                        System.out.println(permutedValue);
                    }
                }
            }


        }
    }

    public static int[] findPermutations(int a, int b, int previousStoneValue) {
        int[] permutedValues = new int[2];
        permutedValues[0] = previousStoneValue + a;
        permutedValues[1] = previousStoneValue + b;
        return permutedValues;
    }
}
