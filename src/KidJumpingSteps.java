import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/16/15.
 */
public class KidJumpingSteps {
    static HashMap<Integer, Integer> hm = new HashMap<>();
    static int recCount;
    static int dpCount;
    // kid can jump 1 2 or 3 steps at a time, count total ways of jumping n steps
    public static void main(String[] args) {
        // hm cache
        hm.put(0, 1);
        Scanner scanner = new Scanner(System.in);
        int steps = scanner.nextInt();
        System.out.println(calculateJumps(steps) + " in " + recCount);
        System.out.println(calculateJumpsDP(steps) + " in " + dpCount);
    }

    static int calculateJumps(int n) {
        recCount++;
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            // requires one jump to finish
            return 1;
        }
        return calculateJumps(n - 1) + calculateJumps(n - 2) + calculateJumps(n - 3);
    }

    static int calculateJumpsDP(int n) {
        // we cache stuff
        dpCount++;
        System.out.println(n);
        // if already cached, return that
        if (hm.containsKey(n)) {
            return hm.get(n);
        } else {
            // not cached, need to calculate
            if (n < 0) {
                return 0;
            }
            int jumps = calculateJumpsDP(n - 1) + calculateJumpsDP(n - 2) + calculateJumpsDP(n - 3);
            hm.put(n, jumps);
            return jumps;
        }
    }
}
