package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> solutions = new ArrayList<>();
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));

        for (int start = 0; start < nums.length - 2; start++) {
            int mid = start + 1, end = nums.length - 1;
            if (nums[start] > 0) break;
            while (mid < end) {
                int threesum = nums[start] + nums[mid] + nums[end];
                if (threesum == 0) {
                    List<Integer> sumNumbers = Arrays.asList(nums[start], nums[mid], nums[end]);
                    solutions.add(sumNumbers);
                    // move to the next last duplicated number
                    while (mid < end && nums[mid] == nums[mid+1]) mid++;
                    while (mid < end && nums[end] == nums[end-1]) end--;
                    while (start < end && nums[start] == nums[start+1]) start++;
                    // move pointers to make sure we check new numbers
                    mid++;end--;
                } else if (threesum > 0) {
                    end--;
                } else {
                    mid++;
                }
            }
        }
        return solutions;
    }

    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};

        ThreeSum threeSum = new ThreeSum();
        System.out.println(threeSum.threeSum(nums));
    }
}
