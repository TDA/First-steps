package fb_recent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertIntervals {
    public static void main(String[] args){
        InsertIntervals insertIntervals = new InsertIntervals();
        int[][] intervals = {{1,3}, {6,9}};
        int[] newInterval = {2,5};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals, newInterval)));
        System.out.println(Arrays.deepToString(new int[][]{{1, 5}, {6, 9}}));
        System.out.println("-----------------------------------------------");
        int [][] intervals2 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] newInterval2 = {4,8};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals2, newInterval2)));
        System.out.println(Arrays.deepToString(new int[][]{{1, 2}, {3, 10}, {12, 16}}));
        System.out.println("-----------------------------------------------");
        int [][] intervals3 = {};
        int[] newInterval3 = {5,7};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals3, newInterval3)));
        System.out.println(Arrays.deepToString(new int[][]{{5, 7}}));
        System.out.println("-----------------------------------------------");
        int [][] intervals4 = {{1,5}};
        int[] newInterval4 = {2,3};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals4, newInterval4)));
        System.out.println(Arrays.deepToString(new int[][]{{1, 5}}));
        System.out.println("-----------------------------------------------");
        int [][] intervals5 = {{1,5}};
        int[] newInterval5 = {2,7};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals5, newInterval5)));
        System.out.println(Arrays.deepToString(new int[][]{{1, 7}}));
        int [][] intervals6 = {{1,5}};
        int[] newInterval6 = {1,7};
        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals6, newInterval6)));
        System.out.println(Arrays.deepToString(new int[][]{{1, 7}}));
        System.out.println("-----------------------------------------------");
//        int [][] intervals6 = {{3,5}};
//        int[] newInterval6 = {2,3};
//        System.out.println(Arrays.deepToString(insertIntervals.insert(intervals6, newInterval6)));

    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<MergeIntervals.Interval> intervalList = new ArrayList<>();
        // if intervals is empty, simply insert and return
        assert newInterval.length == 2;
        if (intervals.length == 0) {
            return new int[][] { newInterval };
        }
        // Intervals is sorted by start time, but not end time
        // Traverse the intervals , find the insertion point by start time
        int startTime = newInterval[0];
        int endTime = newInterval[1];
        int index = 0;
        while (index < intervals.length && startTime > intervals[index][1]) {
            intervalList.add(new MergeIntervals.Interval(intervals[index][0], intervals[index][1]));
            index++;
        }

        // now find the end of insertion point by end time, if overlapped, then update the interval
        while(index < intervals.length && intervals[index][0] <= endTime) {
            newInterval[0] = Math.min(intervals[index][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[index][1], newInterval[1]);
            index++;
        }

        intervalList.add(new MergeIntervals.Interval(newInterval[0], newInterval[1]));

        while(index < intervals.length) {
            int[] interval = intervals[index++];
            intervalList.add(new MergeIntervals.Interval(interval[0], interval[1]));
        }

        int[][] finalIntervals = new int[intervalList.size()][2];
        int i = 0;
        for (MergeIntervals.Interval interval: intervalList) {
            finalIntervals[i][0] = interval.start;
            finalIntervals[i][1] = interval.end;
            i++;
        }

        return finalIntervals;
    }
}
