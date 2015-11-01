import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class HourglassArrays {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] hourglassMatrix= new int[6][6];

        // populate matrix
        for (int i = 0; i < hourglassMatrix.length; i++) {
            for (int j = 0; j < hourglassMatrix.length; j++) {
                hourglassMatrix[i][j] = scanner.nextInt();
            }
        }
        // least value is -9, and sum of 7 elements so
        int maxSum = -9 * 7;
        // we only need to calculate hourglass for inner elts, so
        for (int i = 1; i < hourglassMatrix.length - 1; i++) {
            for (int j = 1; j < hourglassMatrix.length - 1; j++) {
                // for 1,1 we need (0,0), (0,1), (0,2), (1,1), (2,0), (2,1), (2,2)
                int sum = hourglassMatrix[i - 1][j - 1] + hourglassMatrix[i - 1][j] + hourglassMatrix[i - 1][j + 1] +
                        hourglassMatrix[i][j] +
                        hourglassMatrix[i + 1][j - 1] + hourglassMatrix[i + 1][j] + hourglassMatrix[i + 1][j + 1];

                maxSum = Math.max(sum, maxSum);
            }
        }
        System.out.println(maxSum);

    }
}
