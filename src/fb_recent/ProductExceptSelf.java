package fb_recent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int[] leftProduct = new int[nums.length];
        Arrays.fill(leftProduct, 1);
        int[] rightProduct = new int[nums.length];
        Arrays.fill(rightProduct, 1);

        int runningProduct = 1;
        for (int i = 1; i < nums.length; i++) {
            runningProduct = runningProduct * nums[i - 1];
            leftProduct[i] = runningProduct;
        }
        System.out.println(Arrays.toString(leftProduct));

        runningProduct = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            runningProduct = runningProduct * nums[i + 1];
            rightProduct[i] = runningProduct;
        }

        for (int i = 0; i < leftProduct.length; i++) {
            rightProduct[i] = leftProduct[i] * rightProduct[i];
        }


        return rightProduct;
    }

    public static void main(String[] args){
        ProductExceptSelf productExceptSelf = new ProductExceptSelf();
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf.productExceptSelf(nums)));
        int[] nums1 = {-1,1,0,-3,3};
        System.out.println(Arrays.toString(productExceptSelf.productExceptSelf(nums1)));

        Set<Integer> integers = new HashSet<>(List.of(1, 5, 6, 4, 3));

//        Integer[] integersL= (Integer[])integers.toArray();

        Stream<Integer> stream = integers.stream().sorted();
        String join = stream.map(Object::toString).collect(Collectors.joining(","));
        System.out.println(join);
    }
}
