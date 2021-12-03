package dp_recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LongestConsecutiveSubsequence {
    public int longestConsecutive(int[] nums) {
        if (nums.length < 2) return nums.length;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longestSequenceSoFar = 0;
        int currentLongSequence = 0;
        for (int next : set) {
            if (!set.contains(next - 1)) {
                // new seqeuence, check till it expires
                currentLongSequence = 1;
                while (set.contains(next + 1)) {
                    next++;
                    currentLongSequence++;
                    longestSequenceSoFar = Math.max(longestSequenceSoFar, currentLongSequence);
                }
                // expired, reset currentLong
                currentLongSequence = 0;
            }
            // skip this, there is an earlier num in sequence
        }
        return longestSequenceSoFar;
    }
    public int longestConsecutiveArray(int[] nums) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        if (nums.length < 2) return nums.length;
        int max = Arrays.stream(nums).max().getAsInt();
        int min = Arrays.stream(nums).min().getAsInt();

        for (int num : nums) {
            map.put(num, num);
        }
//        System.out.println(Arrays.toString(newArray));

        int longestSequenceSoFar = 0;
        int currentLongSequence = 0;
        for (int i = min; i <= max; i++) {
            if (map.containsKey(i)) {
//                System.out.println(newArray[i]);
                currentLongSequence++;
                longestSequenceSoFar = Math.max(longestSequenceSoFar, currentLongSequence);
            } else {
                // reset cuz no longer a continuous seq
                currentLongSequence = 0;
            }
        }
        System.out.println(map);
        return longestSequenceSoFar;
    }

    public static void main(String[] args){
        LongestConsecutiveSubsequence longestConsecutiveSubsequence = new LongestConsecutiveSubsequence();
        int[] nums = {100,4,200,1,3,2};
        System.out.println(longestConsecutiveSubsequence.longestConsecutive(nums));
    }
}
