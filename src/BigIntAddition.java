import java.util.Arrays;

public class BigIntAddition {
    public int[] plusOne(int[] digits) {
        boolean allNines = false;
        for (int digit : digits) {
            if (digit != 9) {
                allNines = false;
                break;
            } else {
                allNines = true;
            }
        }
        int newSize = allNines ? digits.length + 1 : digits.length;

        int[] result = new int[newSize];
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            result[i] = digits[i] + carry;
            if (result[i] > 9) {
                result[i] = result[i] % 10;
                carry = 1;
            } else {
                carry = 0;
            }
        }
        if (carry == 1) result[0] = 1;

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BigIntAddition().plusOne(new int[]{1, 2, 3})));
        System.out.println(Arrays.toString(new BigIntAddition().plusOne(new int[]{4, 3, 2, 1})));
        System.out.println(Arrays.toString(new BigIntAddition().plusOne(new int[]{9})));
        System.out.println(Arrays.toString(new BigIntAddition().plusOne(new int[]{9, 9, 9})));
        System.out.println(Arrays.toString(new BigIntAddition().plusOne(new int[]{9, 8, 9})));
    }
}
