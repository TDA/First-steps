import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by schandramouli on 11/7/15.
 */
public class SortedListedArrays {
    public static void main(String[] args) {
        ArrayList<int []> listArrays = new ArrayList<>();
        int [] arr_one = {1, 2, 3, 4, 5, 6};
        int [] arr_two = {3, 4, 5, 6, 7, 8};
        int [] arr_three = {3, 5, 7, 10};
        int [] arr_four = {0, 2, 6, 9};
        listArrays.add(arr_one);
        listArrays.add(arr_two);
        listArrays.add(arr_three);
        listArrays.add(arr_four);
        int totalElements = listArrays.get(0).length + listArrays.get(1).length + listArrays.get(3).length + listArrays.get(2).length;
        ArrayList<Integer> sortedArray = new ArrayList<>();
        for (int[] listArray : listArrays) {
            for (int i : listArray) {
                sortedArray.add(i);
            }
        }
        Collections.sort(sortedArray);
        for (Integer integer : sortedArray) {
            System.out.println(integer);
        }
    }
}
