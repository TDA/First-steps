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
        for (int i = 0; i < n2; i++) {
            //c[i]
            if (a[l] > b[k]) {
                c[i] = b[k];
                k++;
            } else {
                c[i] = a[l];
                l++;
            }
        }

        System.out.println(Arrays.toString(c));
    }
}
