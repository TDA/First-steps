package GoogleInterviews;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by schandramouli on 2/13/16.
 */
public class Range implements Comparable {
    long start;
    long end;

    public Range() {
        this.start = 0;
        this.end = 0;
    }

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public Range(String s) {
        String[] times = s.split("-");
        this.start = Long.parseLong(times[0]);
        this.end = Long.parseLong(times[1]);
    }

    public boolean isOverlapping(Range s) {
        // if either a ends before b or b ends
        // before a, then they are NOT overlapping,
        // else they are :O
        return !(this.end <= s.start || this.start >= s.end);
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    // sorts based on start times
    public static ArrayList<Range> sortRanges(ArrayList<Range> ranges) {
        ArrayList<Range> ranges1 = ((ArrayList<Range>) ranges.clone());
        Collections.sort(ranges1);
        return ranges1;
    }

    @Override
    public int compareTo(Object range) {
        if (this.start == ((Range) range).start) {
            if (this.end == ((Range) range).end) {
                return 0;
            } else if (this.end > ((Range) range).end) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.start > ((Range) range).start) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "{ " + this.start + " - " + this.end + " }";
    }
}
