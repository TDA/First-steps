package fb_recent;

public class IntegerDivision {

    // NOTE: this is the right answer and solves all edge-cases, but LC times this out even though it runs in 736ms.
    public int divide(int dividend, int divisor) {
        int quotient = 0;
        int multiplier = 1;
        if (divisor == 0) return Integer.MAX_VALUE;

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
            multiplier = -1;
        }
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        dividend = dividend - divisor;
        while (dividend >= 0 && quotient != Integer.MAX_VALUE) {
            quotient++;
            dividend = dividend - divisor;
        }

        if (quotient == Integer.MAX_VALUE) {
            // 2 cases, multiplier negs or pos
            if (multiplier == 1) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        return quotient * multiplier;
    }

    public static void main(String[] args){
        IntegerDivision integerDivision = new IntegerDivision();
        System.out.println(integerDivision.divide(10,3 ));
        System.out.println(integerDivision.divide(7,-3 ));
        System.out.println(integerDivision.divide(-7,-2 ));
        System.out.println(integerDivision.divide(Integer.MIN_VALUE,-1));
        System.out.println(integerDivision.divide(Integer.MAX_VALUE,-1));
        System.out.println(integerDivision.divide(Integer.MAX_VALUE,2));
        System.out.println(integerDivision.divide(Integer.MIN_VALUE,1));
        System.out.println(integerDivision.divide(Integer.MAX_VALUE,1));
    }
}
