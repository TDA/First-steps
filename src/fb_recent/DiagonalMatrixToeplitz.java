package fb_recent;

import java.util.Stack;

public class DiagonalMatrixToeplitz {
//    C1: bob@yahoo.com, bob@gmail.com
//    C2: mary@facebook.com
//    C3: bob@gmail.com, bob_1@hotmail.com
//    C4: bob_1@hotmail.com
//    C5: mary@facebook.com
//    C6: mark@gmail.com


//    ((C1, C3, C4), (C2, C5), (C6))

//    Given a matrix in the form (int *m, int width, int height), determine if the matrix is Toeplitz (each diagonal has constant elements) or not.
//    i == j
//        0, 1 == 1, 2 == 2, 3
//                0, 2 == 1, 3 == 2, 4
//                ...
//
//                1, 0 == 2, 1 == 3, 2
//                2, 0 == 3, 1
//

    boolean isToeplitzMatrix(int [][] matrix, int width, int height) {
        // SC = O(1)
        // TC = O(n^2)
        for (int i = 0; i < height - 2; i++) { // 0
            for (int j = 0; j < width - 2; j++) { // 1
                // leading diagonal
                if (!(matrix[i][j] == matrix[i + 1][j + 1])) return false;  // 0,1 == 1,2 true
                // if (!(matrix[i][j + 1] == matrix[i + 1][j + 2])) return false; // 1, 2 == 2, 3 true
                // if (!(matrix[i + 1][j] == matrix[i + 2][j + 1])) return false; // 2, 1 == 3, 2 true
            }
        }

        return true;
    }

    public static void main(String[] args){
        DiagonalMatrixToeplitz diagonalMatrixToeplitz = new DiagonalMatrixToeplitz();
        int[][] matrix = {
                {4, 5, 6, 7, 2}, // 0
                {9, 4, 5, 6, 7}, // 1
                {8, 9, 4, 5, 6}, // 2
                {3, 8, 9, 4, 5}, // 3
        };
        System.out.println(diagonalMatrixToeplitz.isToeplitzMatrix(matrix, 4, 5));
    }
}
