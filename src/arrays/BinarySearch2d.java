package arrays;

import java.util.Arrays;

public class BinarySearch2d {
    int[] search (int[][] matrix, int target) {
//        int row = 0, col = matrix[row].length - 1;
//
//        while (row < matrix.length && col >= 0) {
//            if (matrix[row][col] == target) return new int[]{row, col};
//            else if (matrix[row][col] > target) {
//                col--;
//            } else {
//                row++;
//            }
//        }
//        return new int[] {};
        return search(matrix, 0, matrix[0].length - 1, target);
    }

    int[] search (int[][] matrix, int row, int col, int target) {
        if (matrix[row][col] == target) {
            return new int[]{row, col};
        }

        if (matrix[row][col] > target) {
            col = col - 1;
        } else {
            row = row + 1;
        }

        return search(matrix, row, col, target);
    }

    public static void main(String[] args){
        BinarySearch2d binarySearch2D = new BinarySearch2d();
        int[][] matrix = {
                {15, 20, 40, 85},
                {20, 35, 80, 95},
                {30, 55, 95, 105},
                {40, 80, 100, 120}
        };
        System.out.println(Arrays.toString(binarySearch2D.search(matrix, 55)));
        System.out.println(Arrays.toString(binarySearch2D.search(matrix, 120)));
        System.out.println(Arrays.toString(binarySearch2D.search(matrix, 95)));
//        System.out.println(Arrays.toString(binarySearch2D.search(matrix, 40)));
    }
}
