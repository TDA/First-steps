package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AverageSubArrays {
    static class Subarray {
        public Subarray(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int start;
        public int end;

        @Override
        public String toString() {
            return "[" +
                    start +
                    ", " + end +
                    ']';
        }
    }

    boolean isHigherAverageSubarray(int[] arr, int length, int currentSum, int totalSum) {
        double segmentCount = length + 1;
        System.out.println("segmentCount " + segmentCount);

        double remainingCount = (arr.length == (length + 1)) ? 1 : (arr.length - (length + 1));
        System.out.println("remainingCount " + remainingCount);

        double avgSegment = currentSum == 0 ? 0 : currentSum / segmentCount;
        double avgRemaining = (totalSum - currentSum) / remainingCount;

        System.out.println("currentSum " + currentSum);
        System.out.println("avgSegment " + avgSegment);
        System.out.println("avgRemaining " + avgRemaining);

        System.out.println("-------------------------------");

        return avgSegment > avgRemaining;
    }

    List<Subarray> aboveAverageSubarrays(int[] A) {
        List<Subarray> subarrayList = new ArrayList<>();

        int totalSum = 0;

        for (int k : A) {
            totalSum += k;
        }

        for (int i = 0; i < A.length; i++) {
            int sumCurrent = 0;
            for (int j = i; j < A.length; j++) {
                sumCurrent += A[j];
                System.out.println("start, end " + i + " -- " + j);
                if (isHigherAverageSubarray(A, j - i, sumCurrent, totalSum))
                    subarrayList.add(new Subarray(i + 1, j + 1));
            }
        }

        return subarrayList;
    }

    public static void main(String[] args){
        AverageSubArrays averageSubArrays = new AverageSubArrays();
        int[] A = {3, 4, 2};
        int[][] output = {{1, 2}, {1, 3}, {2, 2}};
//        The above-average subarrays are [3, 4], [3, 4, 2], and [4].
        System.out.println(averageSubArrays.aboveAverageSubarrays(A));
        System.out.println(Arrays.deepToString(output));
    }
}
