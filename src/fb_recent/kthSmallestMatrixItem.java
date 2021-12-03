package fb_recent;

import java.util.Comparator;
import java.util.PriorityQueue;

public class kthSmallestMatrixItem {
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparingInt(a -> a));

        for (int[] ints : matrix) {
            for (int anInt : ints) {
                q.add(anInt);
            }
        }

        System.out.println(q);

        int item = -1;
        while (k > 0 && !q.isEmpty()) {
            item = q.poll();
            k--;
        }

        return item;
    }

    public static void main(String[] args){
        kthSmallestMatrixItem kthSmallestMatrixItem = new kthSmallestMatrixItem();
        int[][] matrix = {{1,5,9},{10,11,13},{12,13,15}}; int k = 8;
        System.out.println(kthSmallestMatrixItem.kthSmallest(matrix, k));
    }
}
