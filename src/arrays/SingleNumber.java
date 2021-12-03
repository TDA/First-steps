package arrays;

public class SingleNumber {
    public int singleNumber(int[] nums) {
        if (nums.length == 0) return -1;
        int singleNumber = nums[0];
        for (int i = 1; i < nums.length; i++) {
            singleNumber ^= nums[i];
        }
        return singleNumber;
    }
}
