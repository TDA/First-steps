package arrays;

import java.util.Arrays;

public class ArrayProduct {
    public int[] productExceptSelfExtraSpace(int[] nums) {
        int runningProduct = 1;
        int [] finalProducts = new int[nums.length];
        Arrays.fill(finalProducts, 1);

        for (int i = 1; i < nums.length; i++) {
            runningProduct = runningProduct * nums[i - 1];
            finalProducts[i] = runningProduct;
        }
        runningProduct = 1;
        for (int j = nums.length - 2; j >= 0; j--) {
            runningProduct = runningProduct * nums[j + 1];
            finalProducts[j] *= runningProduct;
        }
        return finalProducts;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        ArrayProduct arrayProduct = new ArrayProduct();
        System.out.println(Arrays.toString(arrayProduct.productExceptSelfExtraSpace(nums)));;
    }
}
