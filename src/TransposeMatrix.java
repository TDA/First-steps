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
        for (int i = 0; i < transpose.length; i++) {
            for (int j = 0; j < transpose[i].length; j++) {
                System.out.print(transpose[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Reversed: ");
        int [][] reverse = reverseMatrix(matrix);
        for (int i = 0; i < reverse.length; i++) {
            for (int j = 0; j < reverse[i].length; j++) {
                System.out.print(reverse[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Rotated Matrix 90deg: ");
        int [][] rotate = rotateMatrix(matrix, 0);
        for (int i = 0; i < rotate.length; i++) {
            for (int j = 0; j < rotate[i].length; j++) {
                System.out.print(rotate[i][j] + "\t");
            }
            System.out.println();
        }


    }

    public static int[][] rotateMatrix(int [][] matrix , int degrees) {
        if (degrees == 180) {
            return reverseMatrix(matrix);
        } else if (degrees == 90) {
            int [][] transpose = new int[matrix.length][matrix[0].length];
            transpose = transposeMatrix(matrix);
            return reverseMatrix(transpose);
        }
        // any other degree return original matrix
        // 0 or 360 anyway the same, all other degrees dont matter
        return matrix;
    }

    public static int[] reverseArray(int[] ints) {
        // create temp array, do not modify original one
        int [] temp = new int[ints.length];
        Stack<Integer> stack = new Stack<>();
        for(int i : ints) {
            stack.push(i);
        }
        for (int j = 0; j < ints.length; j++) {
            temp[j] = stack.pop();
        }
        return temp;
    }

    public static int[][] reverseMatrix(int [][] matrix) {
        int [][] reverse = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int[] ints = matrix[i];
            ints = reverseArray(ints);
            reverse[i] = ints;
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
