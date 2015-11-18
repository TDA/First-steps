import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Sorter<T> implements Comparable<T>{
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(6);
        arrayList.add(2);
        arrayList.add(5);
        Sorter<Integer> sorter = new Sorter<>();
        sorter.MergeSorter(arrayList);
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

    public void MergeSorter(int low, int high, ArrayList<T> arrayList, ArrayList<T> helperList) {
        if (low < high) {
            // means we can still sort
            int mid = (low + high) / 2;
            MergeSorter(low, mid, arrayList, helperList);
            MergeSorter(mid + 1, high, arrayList, helperList);
            merge(arrayList, helperList, low, mid, high);
        }
    }

    public void MergeSorter(ArrayList<T> arrayList) {
        ArrayList<T> helperList = new ArrayList<>();
        int high = arrayList.size() - 1;
        int low = 0;
        MergeSorter(low, high, arrayList, helperList);
    }

    public void merge(ArrayList<T> arrayList, ArrayList<T> helperList, int low, int mid, int high) {
        // first copy arraylist into helper array
        for (int i = low; i <= high; i++) {
            helperList.add(arrayList.get(i));
        }
        int helperLeft = low;
        int helperRight = mid + 1;
        int current = low;
        while (helperLeft <= mid && helperRight <= high) {
            // while at least one element is unsorted
            if (helperList.get(helperLeft).compareTo(helperList.get(helperRight))) {

            }
        }
    }
}
