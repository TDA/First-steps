public class Pow {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == Integer.MIN_VALUE) {
            if (x == 1 || x == -1) return 1;
            return 0;
        }
        double multiplier = x;
        if (n < 0) {
            multiplier = 1/x;
            n = -n;
        }
        return myPowHelper(multiplier, n);
    }

    // Iterative version is too slow, recursive can optimize by cutting in half.
    public double myPowHelper(double x, int n) {
        if (n == 1) return x;
        double halfResult = myPowHelper(x * x, n / 2);
        if (n % 2 == 0) return halfResult;
        return halfResult * x;
    }

    public static void main(String[] args) {
        System.out.println(new Pow().myPow(2.0, 10));
        System.out.println(new Pow().myPow(2.1, 3));
        System.out.println(new Pow().myPow(2.0, -2));
        System.out.println(new Pow().myPow(27, -3));
        System.out.println(new Pow().myPow(8.95371, -1));
    }
}
