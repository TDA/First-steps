package fb_recent;

import java.util.Arrays;

public class RemoveElementInPlace {
    public int removeElement(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;

        int counter = 0;
        while (left < nums.length) {
            if (nums[left] == val) {
                counter++;
                // need to remove/replace with another value from end of array
                while (right > 0 && nums[right] == val) {
                    right--;
                }
                if (right >= 0) {
                    nums[left] = nums[right];
                    right--;
                }
            }
            left++;
        }
        return nums.length - counter;
    }

    public static void main(String[] args){
        RemoveElementInPlace removeElementInPlace = new RemoveElementInPlace();
        int[] nums = {3,2,2,3}; int val = 3;
        System.out.println(removeElementInPlace.removeElement(nums, val));
        System.out.println(Arrays.toString(nums));

        int[] nums2 = {2}; int val2 = 2;
        System.out.println(removeElementInPlace.removeElement(nums2, val2));
        System.out.println(Arrays.toString(nums2));
    }
}
