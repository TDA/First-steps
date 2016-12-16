import java.util.Arrays;

/**
 * Created by schandramouli on 4/19/16.
 */
public class randomNumbers {

    public static void main(String[] args) {
        int[] arrayTimes = new int[5];
        for (int i = 0; i < 100; i++) {
            int n = rand5();
            System.out.println(n);
            try {
                Thread.sleep(1000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            arrayTimes[n]++;
        }
        System.out.println(Arrays.toString(arrayTimes));
    }

    public static int rand5() {
        // returns a no between 1 and 5 inclusive,
        // with equal probability
        // --> By definition, having equal
        // probability makes it non-random, lol.
        // lets use the current system time
        // as the seed
        long n = System.currentTimeMillis();
        return (int)n % 5;
    }


    public static int rand7() {
        // returns a no between 1 and 7 inclusive,
        // with equal probability
        long n = System.currentTimeMillis();
        return (int)n % 7;
    }
}
