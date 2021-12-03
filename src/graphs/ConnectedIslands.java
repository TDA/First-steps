package graphs;

import static graphs.Utility.prettyMatrix;

public class ConnectedIslands {
    public int numIslands(char[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    visit(visited, grid, i, j);
                    count++;
                    System.out.println();
                }
            }
        }
        prettyMatrix(visited);
        return count;
    }

    void visit(boolean[][] visited, char[][] grid, int i, int j) {
        visited[i][j] = true;
        System.out.print(grid[i][j] + " ");
        for (Directions d : Directions.values()) {
            int iMove = i + d.x;
            int jMove = j + d.y;

            boolean withinBounds = (iMove < grid.length) && (iMove >= 0) &&
                    (jMove < grid[i].length) && (jMove >= 0);
            if (withinBounds) {
                boolean unvisited = !visited[iMove][jMove];
                boolean isConnected = grid[iMove][jMove] == '1';
                if (unvisited && isConnected) {
                    visit(visited, grid, iMove, jMove);
                }
            }
        }
    }

    public static void main(String[] args){
        ConnectedIslands connectedIslands = new ConnectedIslands();
        char[][] grid = {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        };
        System.out.println(connectedIslands.numIslands(grid));

        char[][] grid2 = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        System.out.println(connectedIslands.numIslands(grid2));

        char[][] grid3 = {
                {1,0,0,1},
                {0,1,1,0},
                {0,1,1,1},
                {1,0,1,1}
        };
        System.out.println(connectedIslands.numIslands(grid3));
    }
}
