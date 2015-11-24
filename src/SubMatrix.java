import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by schandramouli on 11/24/15.
 */
public class SubMatrix {
    // find the largest submatrix inside a given matrix => find the sums of all submatrices and choose the biggest one
    // how to proceed? => first lets find only the largest element. intuitively, this should give us the largest submatrix
    // of size 1. now we can find the largest submatrix of size 2, by looking at the 3 elements closer to the largest elt.
    // however edge cases will definitely be there :) like the below one. so the largest element might not always figure
    // in the largest submatrix
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        int [][] matrix = {
                {-1, 2, -1},
                {-2, 6, -3},
                {-7, 8, -5}
        };

        // print all submatrices
        ArrayList<ArrayList<Integer>> submatrices = returnSubMatrices(matrix, 2);
        System.out.println("Submatrices are : " + submatrices);

        int maxValue = 0;
        int maxValueIndex = 0;
        int index = 0;
        for (ArrayList<Integer> integerArrayList : submatrices) {
            int sum = calcSumArrayList(integerArrayList);
            if (sum > maxValue) {
                // found a new big value, update
                maxValue = sum;
                maxValueIndex = index;
            }
            index++;
        }
        System.out.println("Max value was " + maxValue + " found at " + maxValueIndex);
        System.out.println("Corresponding submatrix was " + submatrices.get(maxValueIndex));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Duration : " + duration);
    }

    public static int calcSumArrayList (ArrayList<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer integer : integers) {
            ints[i] = integer;
            i++;
        }
        return BalancedSum.sum(ints);
    }

    public static ArrayList<ArrayList<Integer>> returnSubMatrices(int[][] matrix, int subMatrixSize) {
        int lastIndexLimit = matrix.length - subMatrixSize;
        // we need three loops here to control and contain each submatrix
        int currentSubMatrixIndex = 0;
        int currentIndexI = 0;
        int currentIndexJ = 0;
        ArrayList<ArrayList<Integer>> subMatrices = new ArrayList<>();
        while (currentSubMatrixIndex < lastIndexLimit) {
            // outer loop to know when all submatrices have been done
            // create an arraylist to hold each submatrix
            ArrayList<Integer> submatrix = new ArrayList<>();
            for (int i = currentIndexI; i < (currentIndexI + subMatrixSize); i++) {
                // this loop to iterate through the rows
                for (int j = currentIndexJ; j < (currentIndexJ + subMatrixSize); j++) {
                    // this for columns
                    //System.out.println(i + "," + j);
                    //System.out.println(matrix[i][j]);
                    submatrix.add(matrix[i][j]);
                }
                //System.out.println();
            }

            // add the submatrix
            subMatrices.add(submatrix);
            currentIndexJ++;
            // see if the columns for this row are done, if so switch to next row
            if (currentIndexJ > lastIndexLimit) {
                currentIndexJ = 0;
                currentIndexI++;
                // see if rows for this submatrix are done, if so, head to the next submatrix
                if (currentIndexI > lastIndexLimit) {
                    currentIndexI = 0;
                    currentSubMatrixIndex++;
                }
            }

        }
        return subMatrices;
    }
}
