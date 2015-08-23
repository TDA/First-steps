import java.util.Stack;

/**
 * Created by schandramouli on 8/23/15.
 */
public class MinCostPath {
    public static void main(String [] args) {
        int [][] cost = {
                            {1, 2, 3},
                            {4, 8, 2},
                            {1, 5, 3}
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
        System.out.println(minCostPath(DynCost, 0, 0, 2, 2));

    }
    public static String minCostPath(int [][] DynCost, int x, int y, int m, int n) {
        // calc the min path from (i,j) to (m,n) given
        // a shortest path matrix from i,j to every other point
        String s = (m) + "," + (n);
        Stack<String> stack = new Stack<>();
        stack.push(s);
        int i = m;
        int j = n;
        while (i > x && j > y) {
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
        stack.push((x) + "," + (y));
        s = "";
        while(! stack.isEmpty()) {
            s = s + " -> " + stack.pop();
        }
        return s;
    }
}
