package arrays;

import java.util.Arrays;

public class InPlaceMatrixRotation {

    public static void main(String[] args){
        InPlaceMatrixRotation inPlaceMatrixRotation = new InPlaceMatrixRotation();
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(Arrays.deepToString(inPlaceMatrixRotation.rotate(matrix)));
        System.out.println(Arrays.deepToString(inPlaceMatrixRotation.inPlaceRotate(matrix)));
    }

    private int[][] rotate(int[][] matrix) {
        int[][] rotatedMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rotatedMatrix[j][i] = matrix[i][j];
            }
        }
        return rotatedMatrix;
    }

    private int[][] inPlaceRotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            // Remember you dont have to rotate for every value, only after the diagonals
            for (int j = i + 1; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        return matrix;
    }
}
