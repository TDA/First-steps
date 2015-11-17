import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/16/15.
 */
public class RobotMovement {
    static HashMap<Point, Integer> hm = new HashMap<>();
    static int recCount;
    static int dpCount;
    // kid can jump 1 2 or 3 steps at a time, count total ways of jumping n steps
    public static void main(String[] args) {
        // hm cache
        hm.put(new Point(0, 0), 1);
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.println(calculateSteps(x, y) + " in " + recCount);
        System.out.println(calculateStepsDP(x, y) + " in " + dpCount);
        System.out.println(hm);
    }

    static int calculateSteps(int x, int y) {
        recCount++;
        // base cases, 1, 2, 3
        if (x < 0 || y < 0) {
            return 0;
        }
        if (x == 0 && y == 0) {
            // has only one way to finish
            return 1;
        }
        return calculateSteps(x - 1, y) + calculateSteps(x, y - 1);
    }

    static int calculateStepsDP(int x, int y) {
        // we cache stuff
        dpCount++;
        // if already cached, return that
        Point p = new Point(x, y);
        if (hm.containsKey(p)) {
            return hm.get(p);
        } else {
            // not cached, need to calculate
            if (x < 0 || y < 0) {
                return 0;
            }
            int jumps = calculateStepsDP(x - 1, y) + calculateStepsDP(x, y - 1);
            hm.put(p, jumps);
            return jumps;
        }
    }
}
