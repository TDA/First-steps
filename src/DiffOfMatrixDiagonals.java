import java.util.Scanner;

/**
 * Created by schandramouli on 10/20/15.
 */
public class DiffOfMatrixDiagonals {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = s.nextInt();
            }
        }

        int diagonal1 = 0;
        for (int i = 0; i < matrix.length; i++) {
            diagonal1 += matrix[i][i];
        }

        int diagonal2 = 0;
        for (int i = 0; i < matrix.length; i++) {
            diagonal2 += matrix[i][n - i - 1];
        }

        //System.out.println(diagonal1 + " " + diagonal2);
        int diff = Math.abs(diagonal1 - diagonal2);
        System.out.println(diff);
    }
}
