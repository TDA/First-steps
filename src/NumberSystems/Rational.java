package NumberSystems;

import java.math.BigDecimal;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Rational extends NumberImpl implements Comparable<NumberImpl> {
    double numerator;
    double denominator;
    double value;
    int precision = 6; // default is 6

    public Rational(double numerator, double denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        // store only 3 digits of precision
        this.value = new BigDecimal(this.numerator / this.denominator).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Rational(String numberRep) {
        try {
            if (Double.parseDouble(numberRep) == 0) {
                this.numerator = this.value = 0;
                this.denominator = 1;
            } else if (Double.parseDouble(numberRep) == 1) {
                this.numerator = this.denominator = this.value = 1;
            }
        } catch (Exception e) {
            int indexOfSlash = numberRep.indexOf("/");
            String num = numberRep.substring(0, indexOfSlash);
            String denom = numberRep.substring(indexOfSlash + 1);
            // crappy java rules on constructor being first statement :\
            // have to repeat code here due to that
            this.numerator = Double.parseDouble(num);
            this.denominator = Double.parseDouble(denom);
            this.value = new BigDecimal(this.numerator / this.denominator).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public Rational getReciprocal() {
        return new Rational(this.denominator, this.numerator);
    }

    public Rational getLCM(Rational secondNumber) {
        // we want them to be of same precision
        Rational cloneThis = new Rational(this.numerator, this.denominator);
        cloneThis.setPrecision(this.getPrecision());
        Rational cloneSecond = new Rational(secondNumber.numerator, secondNumber.denominator);
        cloneSecond.setPrecision(this.getPrecision());

        // find the lcm of the denoms, then multiply both numbers to get same denom
        Double lcmDenom = Integers.getLCM(cloneThis.denominator, cloneSecond.denominator);
        // multi first number
        cloneThis.numerator = cloneThis.numerator * lcmDenom / cloneThis.denominator;
        cloneThis.denominator = cloneThis.numerator * lcmDenom / cloneThis.denominator;


        cloneSecond.numerator = cloneSecond.numerator * lcmDenom / cloneSecond.denominator;
        cloneSecond.denominator = cloneSecond.numerator * lcmDenom / cloneSecond.denominator;

        Double lcmNum = Integers.getLCM(cloneThis.numerator, cloneSecond.numerator);

        return new Rational(lcmNum, lcmDenom);
    }

    @Override
    public String toString() {
        return this.value + "";
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return (float) (this.value);
    }

    @Override
    public long longValue() {
        return Math.round(this.value);
    }

    @Override
    public int intValue() {
        return (int) (Math.round(this.value));
    }
    @Override
    public int compareTo(NumberImpl o) {
        // low precision
        double epsilon = 1 / Math.pow(10, this.precision);
        System.out.println(this.doubleValue() + " , " + o.doubleValue() + " diff " + (Math.abs(this.doubleValue() - ((Rational) o).doubleValue()) < epsilon) + " " + epsilon);

        if (Math.abs(this.doubleValue() - ((Rational) o).doubleValue()) < epsilon) {
            // they are equal, return 0
            return 0;
        }

        if (this.doubleValue() > ((Rational) o).doubleValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
