package fb_recent;

import java.util.PriorityQueue;

public class kthLargestItem {
//    Given an integer array and an integer number k. Return the k-th largest element in the array.
//
//    array = [5, -3, 9, 12]
//            * k = 0 => return: 9
//            * k = 1 => return: 5
//            * k = 3 => return: -3

// not sorted
// integer array, +ve, -ve
// k = 0 means first largest


    int kthLargestItem(int[] array, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // O(n)

        if (array.length == 0 || k > array.length) return Integer.MIN_VALUE;

        for (int i = 0 ; i< array.length; i++) {
            pq.add(array[i]); // 12, 9, 5 , -3
        }

        // kth largest, 0 indexed
        int i = 0;
        while (i < k) {
            pq.poll();
            i++;
        }
        return pq.poll(); // 9
    }

    public static void main(String[] args){
        kthLargestItem kthLargestItem = new kthLargestItem();

        int[] array = {5, -3, 9, 12};
        System.out.println(kthLargestItem.kthLargestItem(array, 0));
        System.out.println(kthLargestItem.kthLargestItem(array, 1));
        System.out.println(kthLargestItem.kthLargestItem(array, 3));
    }
}

