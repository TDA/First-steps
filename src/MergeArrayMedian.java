import java.util.Arrays;

/**
 * Created by schandramouli on 3/22/16.
 */
public class MergeArrayMedian {
    public static void main(String[] args) {
        int [] a = {1, 2, 3, 4, 5};
        int [] b = {3, 4, 5, 6, 7};
        int [] c = new int[a.length * 2];
        int l = 0;
        int k = 0;
        int n= a.length;
        int n2 = (n * 2);
        int i;
        for (i = 0; i < n2; i++) {
            //c[i]
            if (l < n && k < n) {
                if (a[l] > b[k]) {
                    c[i] = b[k];
                    k++;
                } else {
                    c[i] = a[l];
                    l++;
                }
            } else {
                break;
            }
        }
        if (i < n2) {
            while (l < n) {
                c[i++] = a[l++];
            }
            while (k < n) {
                c[i++] = b[k++];
            }
        }
        System.out.println(Arrays.toString(c));
        // 2n is always even
        int median = (c[n - 1] + c[n])/ 2;
        System.out.println("Median: " + median);
    }
}
