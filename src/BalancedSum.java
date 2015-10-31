/**
 * Created by schandramouli on 10/31/15.
 */
public class BalancedSum {
    public static void main(String[] args) {
        int arr[] = {1, 2, 4, 3};
        System.out.println(CheckIfBalanced(arr));
    }
    // this is what i wrote for the godaddy interview
    public static int CheckIfBalanced(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (SumLeft(arr, i) == SumRight(arr, i)) {
                return 1;
            } else {
                continue;
            }
        }
        return 0;
    }

    public static int SumLeft(int[] arr, int x) {
        int sum = 0;
        for (int i = 0; i < x; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static int SumRight(int[] arr, int x) {
        int sum = 0;
        for (int i = x + 1; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
