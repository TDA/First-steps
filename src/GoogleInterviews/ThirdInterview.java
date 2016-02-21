package GoogleInterviews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import GoogleInterviews.Range;

/**
 * Created by schandramouli on 12/3/15.
 */
public class ThirdInterview {
    // Given a list/set of input ranges, output only the ranges which do
    // not overlap with any other range. (i.e)
    // 0-10, 8-15, 17-20, 22-30, 28-40
    // => Output is 17-20 since it doesnt overlap with anything else
    // Everything else overlaps with something else, so can be discounted :)

    public static void main(String[] args) {

        Range r = new Range(0, 18);
        Range s = new Range("8-15");
        Range t = new Range("17-18");
        Range u = new Range("22-30");
        Range v = new Range("28-40");
        Range w = new Range("11-19");
        Range x = new Range("13-18");
        Range y = new Range("16-25");
        Range z = new Range("19-25");
        Range z1 = new Range("19-22");
        Range z2 = new Range("15-24");
        Range z3 = new Range("19-29");

        ArrayList<Range> ranges = new ArrayList<>();
        ranges.add(r);
        ranges.add(s);
        ranges.add(t);
        ranges.add(u);
        ranges.add(v);
//        ranges.add(w);
//        ranges.add(x);
//        ranges.add(y);
        ranges.add(z);
        ranges.add(z1);
//        ranges.add(z2);
        ranges.add(z3);
        System.out.println(ranges);
        // need to sort this according to the start times, and
        // then arrange them. Didnt know this was NP
        // what use is sorting? why do we need it?
        // Sorting might help us do fewer comparisons for overlap

        ranges = Range.sortRanges(ranges);
        System.out.println(ranges);

        int runs = 0;
        Set<Range> rangeSet = new HashSet<>();
        LinkedHashMap<Range, Integer> rangeIntegerHashMap = new LinkedHashMap<>();
        Range runningRange = new Range(0, 0);
        for (int i = 0; i < ranges.size(); i++) {
            // definitely not n^2, i dont even think this is n logn, it seems
            // to be much lesser
            int count = 0;
            int j = i + 1;
            Range range = ranges.get(i);
            // TODO: change this to the running total
            if (range.isOverlapping(runningRange)) {
                // WHY? why is it that we check only one back, while we check so many forward
                // cuz if we check one back, and its not overlapping, it can NEVER
                // be overlapping with anything before that. This is cuz, this start would
                // then have to greater than the end of the previous one, which by
                // definition is greater than the start of the previous one
                // this is also why the optimization part is SOOO
                // important to have a running total
                count++;
                runs++;
            }
            while (j < ranges.size() && range.isOverlapping(ranges.get(j))) {
                j++;
                count++;
                runs++;
            }

            // update the running range, we have seen until the end
            // of this range, and future ones should not have
            // an overlap with this. irrespective of an overlap, the
            // range needs to be updated, to the max of what is seen,
            // and what was seen now.
            runningRange.setEnd(Math.max(range.getEnd(), runningRange.getEnd()));

            // this is almost the solution I gave for Google
            rangeIntegerHashMap.put(range, count);
            if (count == 0) {
                // means no overlap, can put in
                rangeSet.add(range);
            }
            System.out.println(range);
            System.out.println(runningRange);
        }
        System.out.println(rangeIntegerHashMap);
        System.out.println(rangeSet);
        System.out.println(runs);
    }
}
