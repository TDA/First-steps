package GoogleInterviews;

import java.util.ArrayList;

/**
 * Created by schandramouli on 12/2/15.
 */
public class FirstInterview {
    // given an iterator of iterators, implement next() and hasNext()
    // to get the iterators in alternating fashion
    // [0] -> a1, a2, a3
    // [1] -> b1, b2
    // [2] -> c1, c2, c3
    // => a1, b1, c1, a2, b2, c2 a3, c3 should be the output :)

    // tbh, now that i think about this, this is just a ragged array
    // so basically needs a row counter and a column counter
    // the m * n is obtained by the .size(), like so:
    // m = mainList.size()
    // n = subList.size()

    // so then, it would be a simple check whether the column counter (cc)
    // is <= n, and the row counter (rc) is <= m and retrieve the value.
    // if cc overflows n, then move on to the next rc, if rc overflows m,
    // reset back to 0, and move to next cc.

    // LoL stands for List of lists :P :D
    ArrayList<ArrayList<String>> LoL = new ArrayList<>();
    int nextList;
    int nextElement;

    // these will hold the biggest column and the last row
    // so we dont have to compute these every time.
    int rowCount;
    int longestColumnCount;

    public static void main(String[] args) {
        ArrayList<String> l1 = new ArrayList<>();
        ArrayList<String> l2 = new ArrayList<>();
        ArrayList<String> l3 = new ArrayList<>();
        l1.add("a1");l1.add("a2");l1.add("a3");l1.add("a4");l1.add("a5");
        l2.add("b1");l2.add("b2");l2.add("b3");
        l3.add("c1");l3.add("c2");l3.add("c3");l3.add("c4");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l3);
        FirstInterview f = new FirstInterview();
        f.addList(l1);
        f.addList(l2);
        f.addList(l3);
        System.out.println(f);
    }

    private void addList(ArrayList<String> l) {
        LoL.add(l);
        rowCount++;
        longestColumnCount = Math.max(l.size(), longestColumnCount);
    }

    public FirstInterview() {
        this.nextList = 0;
        this.nextElement = 0;
        this.longestColumnCount = 0;
        this.rowCount = 0;
    }

    public String next() {
        String result = "";
        if (this.hasNext()) {
            result = this.LoL.get(nextList).get(nextElement);
            nextList++;
        }
        return result;
    }

    public boolean hasNext() {
        while (nextElement < longestColumnCount) {
            // System.out.println(nextList + " " + nextElement);
            if (nextList >= rowCount) {
                // reset the nextList
                nextList = 0;
                nextElement++;
            }
            try {
                this.LoL.get(nextList).get(nextElement);
                return true;
            } catch (IndexOutOfBoundsException e) {
                nextList++;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        while (this.hasNext()) {
            s += this.next();
        }
        return s;
    }
}
