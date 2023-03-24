package Graphs;

public class Utility {
    public static void prettyMatrix (boolean[][] matrix) {
        System.out.println("----------------------------");
        for (boolean[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + "\t");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    };


    public static void prettyMatrix(int[][] matrix) {
        System.out.println("----------------------------");
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + "\t");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }


}
