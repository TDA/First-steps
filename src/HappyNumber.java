import java.util.HashMap;
import java.util.Map;

public class HappyNumber {

    public boolean isHappy(int n) {
        Map<Integer, Integer> checkedNumbers = new HashMap<>();
        while (true) {
            int semiHappy = getSemiHappyValue(n);
            if (checkedNumbers.containsKey(semiHappy)) {
                return false;
            }
            if (semiHappy == 1) {
                return true;
            } else {
                checkedNumbers.put(semiHappy, 1);
            }
            n = semiHappy;
            System.out.println(checkedNumbers);
            // means not 1 and hasnt been seen before, continue
        }
    }

    private int getSemiHappyValue(int n) {
        int sum = 0;
        while (n > 0) {
            int i = n % 10;
            sum += i * i;
            n = n / 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new HappyNumber().isHappy(19));
        System.out.println(new HappyNumber().isHappy(2));

    }
}
