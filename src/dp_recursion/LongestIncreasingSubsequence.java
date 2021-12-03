package dp_recursion;

import java.util.Arrays;

public class LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        int[] dpCache = new int[nums.length];
        Arrays.fill(dpCache, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dpCache[i] = Math.max(dpCache[j] + 1, dpCache[i]);
                }
            }
        }
        return Arrays.stream(dpCache).max().getAsInt();
    }


    public static void main(String[] args){
        LongestIncreasingSubsequence longestIncreasingSubsequence = new LongestIncreasingSubsequence();
        int[] nums = {0,1,0,3,2,3};
        System.out.println(longestIncreasingSubsequence.lengthOfLIS(nums));

        int[] nums2 = {10,9,2,5,3,7,101,18};
        System.out.println(longestIncreasingSubsequence.lengthOfLIS(nums2));

        int[] nums3 = {7,7,7,7,7,7,7};
        System.out.println(longestIncreasingSubsequence.lengthOfLIS(nums3));

    }
}
