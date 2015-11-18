import java.util.Arrays;

/**
 * Created by schandramouli on 11/17/15.
 */
public class QuickSort {
    static int[] array = {13, 12, 11, 10, 9, 8, 2, 15, 11, 9};
    static int count;
    // find a pivot, and shift elements lesser than it to left, and greater than it to right
    public static void main(String[] args) {

        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    static int partition (int arr[], int left, int right) {
        int pivot = (left + right) / 2;
        int i = left;
        int j = right;
        while (arr[i] < arr[pivot]) {
            i++;
        }
        while (arr[j] > arr[pivot]) {
            j--;
        }

        if (i <= j) {
            // switch the elements
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }
        return i;
    }

    static void quickSort(int[] arr, int left, int right) {
        System.out.println(++count);
        int pivot = partition(arr, left, right);

        if (left < pivot) {
            // recursively break down the array
            quickSort(arr, left, pivot);
        }
        if ((pivot + 1) < right) {
            quickSort(arr, pivot + 1, right);
        }
    }
}
