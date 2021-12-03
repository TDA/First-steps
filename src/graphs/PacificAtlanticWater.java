package graphs;

import static graphs.Utility.prettyMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacificAtlanticWater {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> paths = new ArrayList<>();
        if (heights == null || heights.length == 0 || heights[0].length == 0) return paths;

        boolean[][] toPacific = new boolean[heights.length][heights[0].length];
        boolean[][] toAtlantic = new boolean[heights.length][heights[0].length];

        // Now calc the paths from edges to other areas and mark as true if reachable
        for (int i = 0; i < heights.length; i++) {
            populatePaths(toPacific, heights, i, 0);
            populatePaths(toAtlantic, heights, i, heights[i].length - 1);
        }
//        prettyMatrix(toPacific);
        for (int j = 0; j < heights[0].length; j++){
            populatePaths(toPacific, heights, 0, j);
            populatePaths(toAtlantic, heights, heights.length - 1, j);
        }
//        prettyMatrix(toAtlantic);

        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
               if (toPacific[i][j] && toAtlantic[i][j]) {
                   paths.add(Arrays.asList(i, j));
               }
            }
        }
        return paths;
    }

    private void populatePaths(boolean[][] toPath, int[][] heights, int i, int j) {
        toPath[i][j] = true;
        // dfs
        for (Directions d : Directions.values()) {
            int iMove = i + d.x;
            int jMove = j + d.y;


            boolean withinBounds = (iMove < heights.length) && (iMove >= 0) &&
                    (jMove < heights[i].length) && (jMove >= 0);
            if (withinBounds) {
                boolean unvisited = !toPath[iMove][jMove];
                boolean higherGround = heights[iMove][jMove] >= heights[i][j];

                if (unvisited && higherGround) {
                    populatePaths(toPath, heights, iMove, jMove);
                }
            }
        }
    }

    public static void main(String[] args){
        PacificAtlanticWater pacificAtlanticWater = new PacificAtlanticWater();
        int[][] heights = {{1,2,2,3,5},
                            {3,2,3,4,4},
                            {2,4,5,3,1},
                            {6,7,1,4,5},
                            {5,1,1,2,4}};
        System.out.println(pacificAtlanticWater.pacificAtlantic(heights));
        int[][] edgeCase = {{1,2,3},
        {8,9,4},
        {7,6,5}};
        prettyMatrix(edgeCase);
        System.out.println(pacificAtlanticWater.pacificAtlantic(edgeCase));
    }
}
