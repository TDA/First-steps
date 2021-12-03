package fb_recent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        if (intervals.length <= 1) return intervals;
        List<Interval> intervalList = getIntervalList(intervals);
        List<Interval> mergedIntervalList = new ArrayList<>();

        Interval previousInterval = intervalList.get(0);
        int i = 1;
        while (i < intervalList.size()) {
            while (i < intervalList.size() && intervalList.get(i).start <= previousInterval.end) {
                // need to merge
                previousInterval.end = Math.max(intervalList.get(i).end, previousInterval.end);
                i++;
            }

            mergedIntervalList.add(previousInterval);
            if (i != intervalList.size())
                previousInterval = intervalList.get(i);

        }

        int[][] mergedIntervals = new int[mergedIntervalList.size()][2];
        int j = 0;
        for (Interval interval : mergedIntervalList) {
            mergedIntervals[j][0] = interval.start;
            mergedIntervals[j][1] = interval.end;
            j++;
        }
        return mergedIntervals;
    }

    private List<Interval> getIntervalList(int[][] intervals) {
        List<Interval> intervalsList = new ArrayList<>();
        for (int[] interval : intervals) {
            intervalsList.add(new Interval(interval[0], interval[1]));
        }
        return intervalsList;
    }

    public static class Interval {
        public int start;
        public int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" +
                    "" + start +
                    ", " + end +
                    ']';
        }
    }

    public static void main(String[] args){
        MergeIntervals mergeIntervals = new MergeIntervals();
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] Output = {{1,6},{8,10},{15,18}};
        System.out.println("Actual" + Arrays.deepToString(mergeIntervals.merge(intervals)));
        System.out.println("Expected" + Arrays.deepToString(Output));
        System.out.println("-------------------------------------");
        int[][] intervals2 = {{1,3},{4,5}, {5, 6}, {6, 7}, {100, 101}};
        int[][] Output2 = {{1,3},{4, 7}, {100, 101}};
        System.out.println("Actual" + Arrays.deepToString(mergeIntervals.merge(intervals2)));
        System.out.println("Expected" + Arrays.deepToString(Output2));
        System.out.println("-------------------------------------");
        int[][] intervals3 = {{1,4}, {5, 6}, {7, 9}};
        int[][] Output3 = {{1,7}};
        System.out.println("Actual" + Arrays.deepToString(mergeIntervals.merge(intervals3)));
        System.out.println("Expected" + Arrays.deepToString(Output3));

    }
}
