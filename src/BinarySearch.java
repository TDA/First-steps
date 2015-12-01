/**
 * Created by schandramouli on 9/6/15.
 */
public class BinarySearch {
    public static int binarySearch(int[] array, int start, int end, int element) {
        // takes in an array with indices that are integers
        int mid = (start + end) / 2;
        System.out.println("Searching from " + start + "to" + mid +"to"  + end);
        try {
            if (array[mid] == element) {
                return mid;
            } else if (element < array[mid]) {
                return binarySearch(array, start, mid, element);
            } else if (element > array[mid]) {
                return binarySearch(array, mid + 1, end, element);
            } else {
                return -1;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        int [] array = {1, 2, 3, 4, 5, 6};
        int x = binarySearch(array, 0, array.length, 11);
        if (x == -1) {
            System.out.println("Not found");
        } else {
            System.out.println("Found at position " + x);
        }
    }

}
