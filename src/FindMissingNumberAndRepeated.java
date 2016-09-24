import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by schandramouli on 9/22/16.
 */
public class FindMissingNumberAndRepeated {

    public static ArrayList<Integer> repeatedNumber2(final List<Integer> a) {
        // 1 to n
        int min = 1;
        int max = a.get(a.size() - 1);
        int x = 0, y = 0;
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < a.size(); i++) {
            // update max
            if (max < a.get(i)) {
                max = a.get(i);
            }
            if (!hm.containsKey(a.get(i))) {
                hm.put(a.get(i), 1);
            } else {
                // duplicate element
                x = a.get(i);
            }
        }

        for (int j = 1; j < max; j++) {
            if (!hm.containsKey(j)) {
                y = j;
            }
        }
        ArrayList<Integer> b = new ArrayList<>();
        b.add(x);
        b.add(y);
        return b;
    }

    public static ArrayList<Integer> repeatedNumber3(final List<Integer> a) {
        int min = 1;
        int max = a.get(a.size() - 1);
        int x = 0, y = 0;
        int actualSum = 0;
        long sqSum = 0;

        for (int i = 0; i < a.size(); i++) {
            // update min and max
            if (max < a.get(i)) {
                max = a.get(i);
            }
            actualSum = actualSum + a.get(i);

        }

        int expectedSum = max * (max + 1)/2;
        int diff = expectedSum - actualSum;
        if (diff > 0) {
            y = max - diff;
        } else {
            y = max;
        }

        ArrayList<Integer> b = new ArrayList<>();
        b.add(x);
        b.add(y);
        return b;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(5);
        System.out.println(repeatedNumber2(a));
    }
}
