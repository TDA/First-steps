package fb_recent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagonalMatrixOrder {
    public int[] findDiagonalOrder(int[][] mat) {
        if (mat.length == 0 || mat[0].length == 0) return new int[0];
        // 0.0  = 0
        // 01 10  = 1
        // 02 20 11 = 2
        // 12 21 = 3
        // 2.2 = 4
        Map<Integer, List<Integer>> diagonalData = new HashMap<>();

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                int diagPosn = i + j;
                if (diagonalData.containsKey(diagPosn)) {
                    diagonalData.get(diagPosn).add(mat[i][j]);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(mat[i][j]);
                    diagonalData.put(diagPosn, list);
                }
            }
        }

        System.out.println(diagonalData);
        List<Integer> finalList = new ArrayList<>();
        for (Integer level : diagonalData.keySet()) {
            List<Integer> list = diagonalData.get(level);
            if (level % 2 == 0) {
                Collections.reverse(list);
            }
            finalList.addAll(list);
        }

        int[] nums = new int[finalList.size()];
        int i = 0;
        for (Integer item : finalList) {
            nums[i++]  = item;
        }
        return nums;
    }

    public static void main(String[] args){
        DiagonalMatrixOrder diagonalMatrixOrder = new DiagonalMatrixOrder();
        int[][] mat = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(Arrays.toString(diagonalMatrixOrder.findDiagonalOrder(mat)));
    }
}
