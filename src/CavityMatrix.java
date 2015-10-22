import java.util.Scanner;

/**
 * Created by schandramouli on 10/21/15.
 */
public class CavityMatrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];
        String[][] finalMatrix = new String[n][n];
        String s = "";
        while (scanner.hasNext()) {
            s += scanner.next();
        }
        //System.out.println(s);
        int i = 0;
        int j = 0;
        for (String c: s.split("")) {
            //System.out.println(c);
            if (! "".equals(c)) {
                matrix[i][j] = Integer.parseInt(c);
                finalMatrix[i][j] = c;
                if (++j >= n) {
                    j = 0;
                    i++;
                }
            }
        }

        // only check for the inner elements
        for (i = 1; i < matrix.length - 1; i++) {
            for (j = 1; j < matrix[i].length - 1; j++) {
                if (matrix[i][j] > matrix[i - 1][j] &&
                        matrix[i][j] > matrix[i][j - 1] &&
                        matrix[i][j] > matrix[i + 1][j] &&
                        matrix[i][j] > matrix[i][j + 1]) {
                    // replace the spots in the finalMatrix
                    finalMatrix[i][j] = "X";
                }
            }
            //System.out.println();
        }

        for (i = 0; i < finalMatrix.length; i++) {
            for (j = 0; j < finalMatrix[i].length; j++) {
                 System.out.print(finalMatrix[i][j]);
            }
            System.out.println();
        }
    }
}
