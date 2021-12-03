package dp_recursion;

import java.util.Arrays;

public class HouseRobber {
    public int rob(int[] nums) {
        if (nums.length < 2) {
            return Arrays.stream(nums).max().getAsInt();
        }
        int[] maxValues = new int[nums.length];
        maxValues[0] = nums[0];
        maxValues[1] = nums[1];
        for (int i = 2; i < nums.length; i++) {
            for (int j = 0; j < (i - 1); j++) {
                maxValues[i] = Math.max(maxValues[i], maxValues[j] + nums[i]);
            }
        }

        System.out.println(Arrays.toString(maxValues));
        return Arrays.stream(maxValues).max().getAsInt();
    }

    public static void main(String[] args){
        HouseRobber houseRobber = new HouseRobber();
        int[] nums = {1,2,3,1};
        System.out.println(houseRobber.rob(nums));
        int[] nums2 = {2,7,9,3,1};
        System.out.println(houseRobber.rob(nums2));
        int[] nums3 = {2,1,1,2};
        System.out.println(houseRobber.rob(nums3));
    }
}
