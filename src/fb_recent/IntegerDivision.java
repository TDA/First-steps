package fb_recent;

public class IntegerDivision {
    public int divide(int dividend, int divisor) {
        int quotient = 0;
        int multiplier = 1;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
            multiplier = -1;
        }
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        dividend = dividend - divisor;
        while (dividend >= 0) {
            quotient++;
            dividend = dividend - divisor;
        }

        return quotient * multiplier;
    }

    public static void main(String[] args){
        IntegerDivision integerDivision = new IntegerDivision();
        System.out.println(integerDivision.divide(7,2 ));
        System.out.println(integerDivision.divide(7,-2 ));
        System.out.println(integerDivision.divide(-7,-2 ));
    }
}
