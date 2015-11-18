import java.util.HashMap;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Sorter {
    public static void main(String[] args) {
        HashMap<Integer, NumberImpl> hashMap = new HashMap<>();
        hashMap.put(0, new Complex(3.0, 2.1));
        hashMap.put(1, new Complex(2.0, 1.1));
        hashMap.put(2, new Complex(4.0, 8.1));
        hashMap.put(3, new Complex(6.0, 5.1));
        hashMap.put(4, new Complex(1.0, 1.1));
        Sorter sorter = new Sorter();
        sorter.MergeSorter(hashMap);
        System.out.println(hashMap);

        HashMap<Integer, NumberImpl> hashMap2 = new HashMap<>();
        hashMap2.put(0, new Rational(3.0, 2.1));
        hashMap2.put(1, new Rational(2.0, 1.1));
        hashMap2.put(2, new Rational(4.0, 8.1));
        hashMap2.put(3, new Rational(6.0, 5.1));
        hashMap2.put(4, new Rational(1.0, 1.1));
        Sorter sorter2 = new Sorter();
        sorter2.MergeSorter(hashMap2);
        System.out.println(hashMap2);

    }

    public void MergeSorter(int low, int high, HashMap<Integer, NumberImpl> hashMap, HashMap<Integer, NumberImpl> helperList) {
        if (low < high) {
            System.out.println(low + " and " + high);
            // means we can still sort
            int mid = (low + high) / 2;
            MergeSorter(low, mid, hashMap, helperList);
            MergeSorter(mid + 1, high, hashMap, helperList);
            merge(hashMap, helperList, low, mid, high);
        }
    }

    public void MergeSorter(HashMap<Integer, NumberImpl> hashMap) {
        HashMap<Integer, NumberImpl> helperList = new HashMap<>();
        int high = hashMap.size() - 1;
        int low = 0;
        MergeSorter(low, high, hashMap, helperList);
    }

    public void merge(HashMap<Integer, NumberImpl> hashMap, HashMap<Integer, NumberImpl> helperList, int low, int mid, int high) {
        // first copy arraylist into helper array
        for (int i = low; i <= high; i++) {
            helperList.put(i, hashMap.get(i));
        }
        int helperLeft = low;
        int helperRight = mid + 1;
        int current = low;
        while (helperLeft <= mid && helperRight <= high) {
            // while at least one element is unsorted
            // convert both to doubles and check
            if ((helperList.get(helperLeft)).compareTo(helperList.get(helperRight)) > 0) {
                // means right is greater
                hashMap.put(current, helperList.get(helperRight));
                helperRight++;
            } else {
                hashMap.put(current, helperList.get(helperLeft));
                helperLeft++;
            }
            // in both cases, inc current
            current++;
        }

        // is there something left out?
        int remaining = mid - helperLeft;
        for (int i = 0; i <= remaining; i++) {
            hashMap.put(current + i, helperList.get(helperLeft + i));
        }
    }
}
