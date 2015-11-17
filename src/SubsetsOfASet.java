import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by schandramouli on 11/16/15.
 */
public class SubsetsOfASet {
    static Set<ArrayList> hashSetSet = new HashSet<>();
    static int recCount;
    public static void main(String[] args) {

        ArrayList<Integer> integerSet = new ArrayList<>();

        integerSet.add(1);
        integerSet.add(2);
        integerSet.add(3);
        integerSet.add(4);
        integerSet.add(5);
        //integerSet.add(6);
        //System.out.println(integerSet);
        //System.out.println(calcNumberOfPowerSet(integerSet));
        hashSetSet.add(integerSet);
        hashSetSet.add(new ArrayList());
        findPowerSet(integerSet);
        System.out.println(hashSetSet);
        System.out.println(recCount);
        System.out.println(hashSetSet.size());
    }

    static int calcNumberOfPowerSet(ArrayList<Integer> integerSet) {
        System.out.println(integerSet);
        if (integerSet.isEmpty()) {
            // if empty, only one subset, the empty set
            return 1;
        }
        if (integerSet.size() == 1) {
            // if of size 1, empty set and itself are the subsets
            return 2;
        }
        int total = 0;
        // basically remove any one element and multiply that by 2
        integerSet.remove(0);
        total = 2 * calcNumberOfPowerSet(integerSet);
        return total;
    }

    static void findPowerSet(ArrayList<Integer> integerSet) {
        recCount++;
        System.out.println("Set at" + integerSet);
        // lets add the sets to the static Hashset
        if (integerSet.isEmpty()) {
            // if empty, only one subset, the empty set
            hashSetSet.add(new ArrayList());
            return;
        }
        if (integerSet.size() == 1) {
            // if of size 1, empty set and itself are the subsets
            hashSetSet.add(integerSet);
            return;
        }
        // else we need to add the subsets to this, so call it for each subset, aka,
        // remove one element each time, and call recursively
        for (Integer i : integerSet) {
            ArrayList<Integer> clonedSet = (ArrayList<Integer>) integerSet.clone();
            clonedSet.remove(i);
            // save computing it again
            if (hashSetSet.contains(clonedSet)) {
                continue;
            }
            hashSetSet.add(clonedSet);
            findPowerSet(clonedSet);
        }

    }
}
