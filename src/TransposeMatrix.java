import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by schandramouli on 8/26/15.
 */
public class TransposeMatrix {
    public static void main(String[] args) {
        int [][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
            };
        int [][] transpose = transposeMatrix(matrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Transpose: ");
        printMatrix(transpose);

        System.out.println("Reversed: ");
        int [][] reverse = reverseMatrix(matrix, true, true);
        printMatrix(reverse);

        System.out.println("Rotated Matrix 90deg: ");
        int [][] rotate = rotateMatrix(matrix, 90);
        printMatrix(rotate);

        System.out.println("Rotated Matrix 180deg: ");
        rotate = rotateMatrix(matrix, 180);
        printMatrix(rotate);

        System.out.println("Rotated Matrix 270deg: ");
        rotate = rotateMatrix(matrix, 270);
        printMatrix(rotate);


    }

    public static int[][] rotateMatrix(int [][] matrix , int degrees) {
        if (degrees == 180) {
            return reverseMatrix(matrix, true, true);
        } else if (degrees == 90) {
            int [][] transpose = transposeMatrix(matrix);
            return reverseMatrix(transpose, true, false);
        } else if (degrees == 270) {
            // this is rotation in opposite direction
            int [][] transpose = transposeMatrix(matrix);
            return reverseMatrix(transpose, false, true);
        }
        // any other degree return original matrix
        // 0 or 360 anyway the same, all other degrees dont matter
        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public static int[] reverseArray(int[] ints) {
        // create temp array, do not modify original one
        int [] temp = new int[ints.length];
        // do we even need a stack here?
        Stack<Integer> stack = new Stack<>();
        for(int i : ints) {
            stack.push(i);
        }
        for (int j = 0; j < ints.length; j++) {
            temp[j] = stack.pop();
        }
        return temp;
    }

    public static int[][] reverseMatrix(int [][] matrix, boolean rows, boolean cols) {
        // reverse both columns and rows if both are true
        int [][] reverse = new int[matrix.length][matrix[0].length];
        // reverse rows
        if (rows) {
            for (int i = 0; i < matrix.length; i++) {
                int[] ints = matrix[i];
                ints = reverseArray(ints);
                reverse[i] = ints;
            }
        }

        if (! rows && cols) {
            reverse = matrix;
        }
        // reverse columns
        if (cols) {
            for (int i = 0; i < reverse.length; i++) {
                int[] ints = new int[reverse[i].length];
                for (int j = 0; j < reverse[i].length; j++) {
                    ints[j] = reverse[j][i];
                }
                ints = reverseArray(ints);
                for (int j = 0; j < reverse[i].length; j++) {
                    reverse[j][i] = ints[j];
                }
            }
        }

        return reverse;
    }
    public static int[][] transposeMatrix(int [][] matrix) {
        // none of these functions affect the original matrix,
        // always create a temp matrix.
        int [][] transpose = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }

    public static int[][] topdownMatrix( int [][] matrix) {
        int [][] reverse = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            int[] ints = matrix[i];
            ints = reverseArray(ints);
            reverse[i] = ints;
        }
        return reverse;
    }
}
