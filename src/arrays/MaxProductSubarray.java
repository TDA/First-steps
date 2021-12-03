package arrays;

public class MaxProductSubarray {
    // The positive nums dont matter
    // zeroes need to reset the product counter, well cuz
    // negatives are the only real problem
    //  -> if you have even num of negatives, again not a problem - similar to positives, take all
    //  -> for odd num of negatives, we need to choose either the first n or last n, so do a double pass.
    public int maxProduct(int[] nums) {
        int result = nums[0];
        int productArray = 1;

        for (int num : nums) {
            productArray *= num;
            result = Math.max(productArray, result);
            if (productArray == 0) {
                productArray = 1;
            }
        }
//        System.out.println(result);
        productArray = 1;
        for (int i = nums.length - 1; i >=0 ; i--) {
            productArray *= nums[i];
            result = Math.max(productArray, result);
            if (productArray == 0) {
                productArray = 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {2,3,-2,4};
        int[] nums2 = {-2,0,-1};
        // this needs to use the 5x multiplier and not 3x
        int[] nums3 = {-3, 4, -1, 2, -5};
        MaxProductSubarray arrayProduct = new MaxProductSubarray();
        System.out.println(arrayProduct.maxProduct(nums));;
        System.out.println(arrayProduct.maxProduct(nums2));;
        System.out.println(arrayProduct.maxProduct(nums3));;
    }
}
