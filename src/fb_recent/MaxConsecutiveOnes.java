package fb_recent;

import java.util.List;
import java.util.stream.Stream;

public class MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int longestSoFar = 0;
        int mostRecentZero = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                // reset index
                mostRecentZero = i;
            }
            longestSoFar = Math.max(longestSoFar, i - mostRecentZero);
        }
        return longestSoFar;
    }

    public int longestOnes(int[] nums, int k) {
        int longestSoFar = 0;
        int startZeroes = 0;
        int replacedZeroes = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                replacedZeroes++;
            }
            if (replacedZeroes > k) {
                if(nums[startZeroes] == 0) replacedZeroes--;
                startZeroes++;
            }
            longestSoFar = Math.max(longestSoFar, i - startZeroes + 1);
        }
        return longestSoFar;
    }

    public static void main(String[] args){
        MaxConsecutiveOnes maxConsecutiveOnes = new MaxConsecutiveOnes();
        int[] array = Stream.of(1, 1, 0, 1, 1, 1).mapToInt(i -> i).toArray();
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(array));
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(Stream.of(1,0,1,1,0,1).mapToInt(i -> i).toArray()));
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(Stream.of(1,1,0,1).mapToInt(i -> i).toArray()));
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(Stream.of(1).mapToInt(i -> i).toArray()));
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(Stream.of(0).mapToInt(i -> i).toArray()));
        System.out.println(maxConsecutiveOnes.findMaxConsecutiveOnes(Stream.of(0, 0, 0, 0, 1).mapToInt(i -> i).toArray()));

        System.out.println("----------------------------------");
        System.out.println(maxConsecutiveOnes.longestOnes(Stream.of(1,1,1,0,0,0,1,1,1,1,0).mapToInt(i -> i).toArray(), 2));
    }
}
