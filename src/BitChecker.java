/**
 * Created by schandramouli on 11/8/15.
 */
public class BitChecker {
    public static void main(String[] args) {
        for (int i = 1; i < 2048; i++) {
            //System.out.println(i + " " + (i - 1) + " is ");
            if((i & (i - 1)) == 0) {
                System.out.println(i + " " +  (i - 1) + " is " );
            }
        }
    }
}
