package GoogleInterviews;

import java.util.ArrayList;

/**
 * Created by schandramouli on 12/2/15.
 */
public class FirstInterview {
    // given an iterator of iterators, implement next() and hasNext()
    // to get the iterators in alternating fashion
//    [0] -> a1, a2, a3
//    [1] -> b1, b2
//    [2] -> c1, c3
//    => a1, b1, c1, a2, b2, a3, c3 should be the output :)

    // LoL stands for List of lists :P :D
    ArrayList<ArrayList<String>> LoL = new ArrayList<>();

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
        f.LoL.add(l1);
        f.LoL.add(l2);
        f.LoL.add(l3);
    }

    public String next() {
        System.out.println();
        return "";
    }

    public boolean hasNext() {
        System.out.println();
        return true;
    }

}
