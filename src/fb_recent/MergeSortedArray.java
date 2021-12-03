package fb_recent;

import java.util.Arrays;

public class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1;

        System.out.println(Arrays.toString(nums1));
        while (i >= 0 && j >= 0) {
            // copy the larger one
            if (nums1[i] >= nums2[j]) {
                nums1[j + i + 1] = nums1[i];
                i--;
            } else {
                nums1[j + i + 1] = nums2[j];
                j--;
            }
        }

        while (i >= 0) {
            nums1[j + i + 1] = nums1[i];
            i--;
        }
        while (j >= 0) {
            nums1[j + i + 1] = nums2[j];
            j--;
        }

        System.out.println(Arrays.toString(nums1));
    }

    public static void main(String[] args){
        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        int []nums1 = {1,2,3,0,0,0}, nums2 = {2,5,6};
        int m = 3, n = 3;
        mergeSortedArray.merge(nums1, m, nums2, n);

        int []nums3 = {0}, nums4 = {1};
        int m1 = 0, n1 = 1;
        mergeSortedArray.merge(nums3, m1, nums4, n1);
    }
}
