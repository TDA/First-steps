package dp_recursion;

import java.util.Arrays;

public class HouseRobber2 {
//    public int rob(int[] nums) {
//        if (nums.length < 3) {
//            return Arrays.stream(nums).max().getAsInt();
//        }
//        int[] nums1 = Arrays.copyOfRange(nums, 0, nums.length - 1);
//        int[] nums2 = Arrays.copyOfRange(nums, 1, nums.length);
//        return Math.max(robThisRange(nums2, nums2.length), robThisRange(nums1, nums1.length));
//    }

    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];

        int[] startFromFirstHouse = new int[nums.length + 1];
        int[] startFromSecondHouse = new int[nums.length + 1];

        startFromFirstHouse[0]  = 0;
        startFromFirstHouse[1]  = nums[0];
        startFromSecondHouse[0] = 0;
        startFromSecondHouse[1] = 0;

        for (int i = 2; i <= nums.length; i++) {
            startFromFirstHouse[i] = Math.max(startFromFirstHouse[i - 1], startFromFirstHouse[i - 2] + nums[i-1]);
            startFromSecondHouse[i] = Math.max(startFromSecondHouse[i - 1], startFromSecondHouse[i - 2] + nums[i-1]);
        }
        System.out.println(Arrays.toString(startFromFirstHouse));
        System.out.println(Arrays.toString(startFromSecondHouse));

        return Math.max(startFromFirstHouse[nums.length - 1], startFromSecondHouse[nums.length]);
    }

    int robThisRange(int[] nums, int end) {
        int[] maxValues = new int[nums.length];
        maxValues[0] = nums[0];
        maxValues[1] = nums[1];
        for (int i = 2; i < end; i++) {
            for (int j = 0; j < i - 1; j++) {
                maxValues[i] = Math.max(maxValues[i], maxValues[j] + nums[i]);
            }
        }

        System.out.println(Arrays.toString(maxValues));
        return Arrays.stream(maxValues).max().getAsInt();
    }

    public static void main(String[] args){
        HouseRobber2 houseRobber = new HouseRobber2();
        int[] nums = {1,2,3,1};
        System.out.println(houseRobber.rob(nums));
        int[] nums2 = {200,3,140,20,10};
        System.out.println(Arrays.toString(nums2));
        System.out.println(houseRobber.rob(nums2));
        int[] nums3 = {2,3,2};
        System.out.println(houseRobber.rob(nums3));
        int[] nums4 = {6,6,4,8,4,3,3,10};
        System.out.println(Arrays.toString(nums4));
        System.out.println(houseRobber.rob(nums4));
        int[] nums5 = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(nums5));
        System.out.println(houseRobber.rob(nums5));
    }
}
