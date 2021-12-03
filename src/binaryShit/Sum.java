package binaryShit;

public class Sum {
    public int getSum(int a, int b) {
        if (b == 0) {
            return a;
        }
        return getSum(a ^ b, (a & b) << 1);
    }

    public static void main(String[] args) {
        Sum s = new Sum();
        System.out.println(s.getSum(3,5));
    }
}
