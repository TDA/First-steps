package arrays;

public class MaxSubArray {
    public int maxSubArray(int[] nums) {
        int result = nums[0];
        int sumArray=nums[0];
        for (int i = 1; i < nums.length; i++) {
            sumArray=Math.max(nums[i]+sumArray,nums[i]);
            result=Math.max(sumArray,result);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int[] nums2 = {5,4,-1,7,8};
        MaxSubArray arrayProduct = new MaxSubArray();
        System.out.println(arrayProduct.maxSubArray(nums));;
        System.out.println(arrayProduct.maxSubArray(nums2));;
    }
}
