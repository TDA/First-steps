package fb_recent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubarraySum {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> occurrencesSumMap = new HashMap<>();
        int counter = 0;
        int runningSum = 0;
        occurrencesSumMap.put(0, 1);
        for (int num : nums) {
            runningSum += num;
            if (occurrencesSumMap.containsKey(runningSum - k)) {
                // we have seen a sum of this number before, so counter++
                counter += occurrencesSumMap.get(runningSum - k);
            }
            occurrencesSumMap.put(runningSum,occurrencesSumMap.getOrDefault(runningSum, 0) + 1);

        }
        System.out.println(occurrencesSumMap);
        return counter;
    }

    public int subarraySumN2(int[] nums, int k) {
        int counter = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) counter++;
            int j = i + 1;
            int runningSum = nums[i];
            while (j < nums.length) {
                runningSum += nums[j];
                if (runningSum == k) {
                    counter++;
                }
                j++;
            }
        }

        return counter;
    }

    public static void main(String[] args){
        SubarraySum subarraySum = new SubarraySum();
        System.out.println(subarraySum.subarraySum(new int[] {1, 1, 1}, 2));
        System.out.println(subarraySum.subarraySum(new int[] {1, 2, 3}, 3));
        System.out.println(subarraySum.subarraySum(new int[] {1, 2, 0, 0, 3}, 3));
        System.out.println(subarraySum.subarraySum(new int[] {1, -1, 0}, 0));
    }
}
