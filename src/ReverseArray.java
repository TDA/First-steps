import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class ReverseArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        int i = 0;
        while (i < n) {
            arr[i] = scanner.nextInt();
            i++;
        }
        for (int j = arr.length - 1; j >= 0; j--) {
            System.out.print(arr[j] + " ");
        }
    }
}
