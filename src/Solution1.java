import java.util.Scanner;

/**
 * Created by schandramouli on 8/26/15.
 */
public class Solution1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        String s1 = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                s1 += " ";
            }
            for (int k = 0; k < i+1; k++) {
                s1 += "#";
            }
            System.out.println(s1);
            s1 = "";
        }
    }
}
