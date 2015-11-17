import java.util.Scanner;

/**
 * Created by schandramouli on 11/16/15.
 */
public class KidJumpingSteps {
    // kid can jump 1 2 or 3 steps at a time, count total ways of jumping n steps
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int steps = scanner.nextInt();
        System.out.println(calculateJumps(steps));
    }

    static int calculateJumps(int n) {
        // base cases, 1, 2, 3
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            // requires one jump to finish
            return 1;
        }
        return calculateJumps(n - 1) + calculateJumps(n - 2) + calculateJumps(n - 3);
    }
}
