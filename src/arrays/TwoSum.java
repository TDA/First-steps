package arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] solutions = new int[2];
        for (int i = 0; i < nums.length -1 ; i++) {
            int firstNum = nums[i];
            for (int j = i + 1; j< nums.length ; j++) {
                int secondNum = nums[j];
                if (firstNum + secondNum == target) {
                    solutions[0] = i;
                    solutions[1] = j;
                    return solutions;
                }
            }
        }
        return solutions;
    }

    public int[] twoSumLinear(int[] nums, int target) {
        int[] solutions = new int[2];
        Map<Integer, Integer> sumMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (sumMap.containsKey(target - num)) {
                return new int[]{sumMap.get(target - num), i};
            }
            sumMap.put(num, i);
        }
        return solutions;
    }

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int[] soln = twoSum.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(soln));
        int[] solns = twoSum.twoSumLinear(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(solns));
    }
}
