package dp_recursion;

import java.util.Arrays;

public class RoboTraversal {
    public static void main(String[] args){
        RoboTraversal roboTraversal = new RoboTraversal();
        System.out.println(roboTraversal.traverse(51, 9));
    }

    private int traverse(int m, int n) {
        int[][] cache = new int[m][n];
        for (int[] row : cache) {
            Arrays.fill(row, -1);
        }
        return traverse(cache, m - 1, n - 1);
    }

    private int traverse(int[][] cache, int i, int j) {
//        System.out.printf("%d, %d\n", i , j);
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) return 1;
        if (cache[i][j] < 0) {
            cache[i][j] = traverse(cache,i - 1, j) + traverse(cache, i, j - 1);
        }
        return cache[i][j];
    }
}
