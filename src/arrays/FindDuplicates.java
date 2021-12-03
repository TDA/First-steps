package arrays;

import java.util.Arrays;

public class FindDuplicates {
    public boolean containsDuplicate(int[] nums) {
        long count = Arrays.stream(nums).distinct().count();
        return count != nums.length;
    }
}
