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
            // basically find all permutations of the values a and b for n=1, n=2 and so on.
            // So lets write a function for that

            int permutedValue = findPermutations(a, b, previousStoneValue);
        }
    }

    public int findPermutations(int a, int b, int previousStoneValue) {
        return 1;
    }
}
