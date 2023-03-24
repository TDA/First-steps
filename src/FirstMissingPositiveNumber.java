public class FirstMissingPositiveNumber {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // mark outside range numbers as useless
            if (nums[i] <= 0 || nums[i] > n) nums[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < n; i++) {
            // create an in-place map cuz array indices are keys, so mark the found numbers with special tags
            // number -> -number (we retain the number cuz 1-sweep
            int indexToUpdate = Math.abs(nums[i]) - 1;
            if (indexToUpdate == Integer.MAX_VALUE - 1) continue;

            if (nums[indexToUpdate] > 0) // negate the ones
                nums[indexToUpdate] = -nums[indexToUpdate];
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] >= 0)
                return i + 1;
        }
        return n + 1;
    }

    public static void main(String[] args) {
        System.out.println(new FirstMissingPositiveNumber().firstMissingPositive(new int[]{1,2,0}));
        System.out.println(new FirstMissingPositiveNumber().firstMissingPositive(new int[]{3,4,-1,1}));
        System.out.println(new FirstMissingPositiveNumber().firstMissingPositive(new int[]{7,8,9,11,12}));
    }
}
