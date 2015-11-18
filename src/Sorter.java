import java.util.HashMap;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Sorter {
    public static void main(String[] args) {
        HashMap<Integer, Complex> hashMap = new HashMap<>();
        hashMap.put(0, new Complex(3.0, 2.1));
        hashMap.put(1, new Complex(2.0, 1.1));
        hashMap.put(2, new Complex(4.0, 8.1));
        hashMap.put(3, new Complex(6.0, 5.1));
        hashMap.put(4, new Complex(1.0, 1.1));
        Sorter sorter = new Sorter();
        sorter.MergeSorter(hashMap);
        System.out.println(hashMap);
    }

//    @Override
//    public int compareTo(T t) {
//        if (Double.valueOf(t.toString()) > Double.valueOf(this.toString())) {
//            return -1;
//        } else if (Double.valueOf(t.toString()) < Double.valueOf(this.toString())){
//            return 1;
//        } else {
//            return 0;
//        }
//    }

    public void MergeSorter(int low, int high, HashMap<Integer, Complex> hashMap, HashMap<Integer, Complex> helperList) {
        if (low < high) {
            System.out.println(low + " and " + high);
            // means we can still sort
            int mid = (low + high) / 2;
            MergeSorter(low, mid, hashMap, helperList);
            MergeSorter(mid + 1, high, hashMap, helperList);
            merge(hashMap, helperList, low, mid, high);
        }
    }

    public void MergeSorter(HashMap<Integer, Complex> hashMap) {
        HashMap<Integer, Complex> helperList = new HashMap<>();
        int high = hashMap.size() - 1;
        int low = 0;
        MergeSorter(low, high, hashMap, helperList);
    }

    public void merge(HashMap<Integer, Complex> hashMap, HashMap<Integer, Complex> helperList, int low, int mid, int high) {
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
            if (helperList.get(helperLeft).compareTo(helperList.get(helperRight)) > 0) {
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
