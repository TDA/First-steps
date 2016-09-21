/**
 * Created by schandramouli on 9/20/16.
 */
public class NumBits {
    public static int numSetBits(long a) {
        int count = 0;
        while (a > 0) {
            count += a & 1;
            a = a >> 1;
        }
        return count;
    }

    public static void main(String[] args) {
        for (long i = 0; i < 1000L; i++) {
            System.out.println(numSetBits(i));
        }
        System.out.println(numSetBits(4294967295L));
        System.out.println(numSetBits(4294967294L));
        System.out.println(numSetBits(4294967293L));
    }
}
