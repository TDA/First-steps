import java.util.Scanner;

/**
 * Created by schandramouli on 11/1/15.
 */
public class FunnyStrings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        //System.out.println(n);

        for (int i = 0; i < n; i++) {
            String s = scanner.nextLine();
            // find the ascii difference of each set fo chars and check if they are equal,
            // if equal, go onto next letter seq, if not, break and return false
            //System.out.println(s);
            int k = s.length();
            //System.out.println(k);
            // for 1 to n - 1, check this
            int j = 1;
            for (; j <= k - 1; j++) {
                //System.out.println("first two " + s.charAt(j) + " " + s.charAt(j - 1));
                //System.out.println("first two " + (s.charAt(j) - s.charAt(j - 1)));
                //System.out.println("last two " + s.charAt(k - j) + s.charAt(k - j - 1));
                //System.out.println("last two " + (s.charAt(k - j) - s.charAt(k - j - 1)));
                if (Math.abs(s.charAt(j) - s.charAt(j - 1)) == Math.abs(s.charAt(k - j) - s.charAt(k - j - 1))) {
                    continue;
                } else {
                    System.out.println("Not Funny");
                    break;
                }
            }
            // if it came here and j == k - 1, means all were equal
            if (j == k) {
                System.out.println("Funny");
            }

        }
    }


}
