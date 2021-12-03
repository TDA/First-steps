package fb_recent;

import java.util.Arrays;
import java.util.List;

class BinaryMatrix {
    int[][] matrix;

    public BinaryMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int get(int row, int col) {
      return matrix[row][col];
    }
    public List<Integer> dimensions() {
        return List.of(matrix.length, matrix[0].length);
    }

    @Override
    public String toString() {
        return "BinaryMatrix{" +
                "matrix=" + Arrays.deepToString(matrix) +
                '}';
    }
};
public class MatrixLeftmostWithOne {
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> dimensions = binaryMatrix.dimensions();
        int rows = dimensions.get(0);
        int cols = dimensions.get(1);
        int leftmost = cols;
        // we need to find the first col that has a 1
        // each row is sorted, so we can binary search each row at best
        for (int i = 0; i < rows; i++) {
            leftmost = Math.min(mbs(binaryMatrix, i, leftmost), leftmost);
        }
        if (leftmost >= cols) return -1;
        return leftmost;
    }

    private int mbs(BinaryMatrix binaryMatrix, int i, int cols) {
        int right = cols - 1;
        int left = 0;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            int query = binaryMatrix.get(i, mid);
            System.out.printf("got query %s at %d and %d \n", query, i, mid);
            if (query < 1) {
                // move right
                left = mid + 1;
            } else {
                // move left
                right = mid;
            }

        }
        if (binaryMatrix.get(i, left) == 1) return left;
        return 1000;
    }

    public static void main(String[] args){
        MatrixLeftmostWithOne matrixLeftmostWithOne = new MatrixLeftmostWithOne();
        int[][] mat = {{0,0},{1,1}};
        int[][] mat2 = {{0,0},{0,1}};
        int[][] mat3 = {{0,0},{0,0}};
        int[][] mat4 = {{0,0,0,1},{0,0,1,1},{0,1,1,1}};


        System.out.println(matrixLeftmostWithOne.leftMostColumnWithOne(new BinaryMatrix(mat)));
        System.out.println(matrixLeftmostWithOne.leftMostColumnWithOne(new BinaryMatrix(mat2)));
        System.out.println(matrixLeftmostWithOne.leftMostColumnWithOne(new BinaryMatrix(mat3)));
        System.out.println(matrixLeftmostWithOne.leftMostColumnWithOne(new BinaryMatrix(mat4)));
    }
}
