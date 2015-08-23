import java.util.Stack;

/**
 * Created by schandramouli on 8/23/15.
 */
public class MinCostPath {
    public static void main(String [] args) {
        int [][] cost = {
                            {1, 2, 3, 11},
                            {4, 8, 2, 12},
                            {1, 5, 3, 13}
                        };
        int rows = cost.length;
        int cols = cost[0].length;
        int [][] DynCost = new int[rows][cols];
        DynCost[0][0] = cost[0][0];
        int i;
        int j;
        for (i = 1; i < rows; i++) {
            DynCost[i][0] = DynCost[i - 1][0] + cost[i][0];
        }
        for (j = 1; j < cols; j++) {
            DynCost[0][j] = DynCost[0][j - 1] + cost[0][j];
        }

        for (i = 1; i < rows; i++) {
            for (j = 1; j < cols; j++) {
                // dynamic programming, use the pre-computed values of
                // previous levels to compute this level
                // better than recursion
                DynCost[i][j] = Integer.min(DynCost[i - 1][j - 1] + cost[i][j],
                                            Integer.min(DynCost[i - 1][j] + cost[i][j],
                                                        DynCost[i][j - 1] + cost[i][j]
                                                        )
                                            );
            }
        }

        for (i = 0; i < rows; i++) {
            for(j = 0 ; j < cols; j++) {
                System.out.print(DynCost[i][j] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println(minCostPath(DynCost, 0, 0, 1, 1));
        System.out.println("The minimum cost from 0,0 to 2,2 is " + findMinLength(DynCost, 1, 1));

    }
    public static String minCostPath(int [][] DynCost, int x, int y, int m, int n) {
        // calc the min path from (i,j) to (m,n) given
        // a shortest path matrix from i,j to every other point
        // dont create a string for every push, just reuse this
        String s = (m) + "," + (n);
        Stack<String> stack = new Stack<>();
        stack.push(s);
        int i = m;
        int j = n;
        while (i >= x && j >= y) {
            // edge cases, when you are horizontally or vertically
            // on the same row/column as source. Now traverse only
            // in that direction.

            if (i == x && j == y) {
                break;
            }
            if (i == x) {
                s = (i) + "," + (j - 1);
                stack.push(s);
                j--;
                continue;
            }
            if (j == y) {
                s = (i - 1) + "," + (j);
                stack.push(s);
                i--;
                continue;
            }

            if (DynCost[i - 1][j] < DynCost[i][j - 1]) {
                if (DynCost[i - 1][j] < DynCost[i - 1][j - 1]) {
                    s = (i - 1) + "," + (j);
                    stack.push(s);
                    i--;
                } else {
                    s = (i - 1) + "," + (j - 1);
                    stack.push(s);
                    i--;
                    j--;
                }
            } else {
                if (DynCost[i][j - 1] < DynCost[i - 1][j - 1]) {
                    s = (i) + "," + (j - 1);
                    stack.push(s);
                    j--;
                } else {
                    s = (i - 1) + "," + (j - 1);
                    stack.push(s);
                    i--;
                    j--;
                }
            }
        }
        s = "";
        while(! stack.isEmpty()) {
            s = s + " -> " + stack.pop();
        }
        return s;
    }

    public static int findMinLength(int [][] DynCost, int x, int y) {
        return DynCost[x][y];
    }
}
