/**
 * Created by schandramouli on 11/3/16.
 */
public class CountToZeroPrime {
    public int checkIfPrime(int no) {
        if (no <= 0) {
            return 0;
        }
        int flooredSqrt = ((int) Math.sqrt(no));
        int highestFactor = 0;

        for (int i = 1; i <= flooredSqrt; i++) {
            if (no % flooredSqrt == 0) {
                highestFactor = flooredSqrt;
            }
        }
        return highestFactor;
    }

    public static void main(String[] args) {

    }
}
