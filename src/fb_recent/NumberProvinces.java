package fb_recent;

import static Graphs.Utility.prettyMatrix;

import Graphs.Directions;

public class NumberProvinces {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected.length == 0) return 0;
        if (isConnected[0].length == 0) return 0;
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < isConnected.length; i++) {
            for (int j = 0; j < isConnected[i].length; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.count();
    }

    public static void main(String[] args){
        NumberProvinces numberProvinces = new NumberProvinces();
        int[][] matrix = {
            {1,1,0},
            {1,1,0},
            {0,0,1}
        };
        System.out.println(numberProvinces.findCircleNum(matrix));

        int[][] grid2 = {
                {1,0,0,1},
                {0,1,1,0},
                {0,1,1,1},
                {1,0,1,1}
        };

        System.out.println(numberProvinces.findCircleNum(grid2));
    }
}

