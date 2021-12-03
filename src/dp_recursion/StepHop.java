package dp_recursion;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class StepHop {
    private final Map<Integer, Integer> cache = new HashMap<>();

    public static void main(String[] args){
        StepHop stepHop = new StepHop();
        int n = 36;
        System.out.println(stepHop.countHops(n));
    }

    // Child can hop 1, 2, or 3 steps at a time
    private int countHops(int n) {
        if (n < 0) return 0;
        if (n == 0) return 1;
        if (!cache.containsKey(n)) {
            cache.put(n, countHops(n - 3) + countHops(n - 2) + countHops(n - 1));
        }
        return cache.get(n);
    }
}
