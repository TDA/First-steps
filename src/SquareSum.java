import java.util.ArrayList;

/**
 * Created by schandramouli on 9/26/16.
 */
public class SquareSum {
    public static ArrayList<ArrayList<Integer>> squareSum(int A) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();

        for (int a = 0; a * a < A; a++) {
            for (int b = 0; b * b < A; b++) {
                if (a <= b && a * a + b * b == A) {
                    ArrayList<Integer> newEntry = new ArrayList<Integer>();
                    newEntry.add(a);
                    newEntry.add(b);
                    ans.add(newEntry);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(squareSum(100));
        System.out.println(squareSum(10000));
        System.out.println(squareSum(0));
        System.out.println(squareSum(1));
        System.out.println(squareSum(2));
    }
}
