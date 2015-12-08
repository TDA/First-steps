package NumberSystems;

import java.math.BigDecimal;

/**
 * Created by schandramouli on 11/17/15.
 */
public class Rational extends NumberImpl implements Comparable<NumberImpl> {
    double numerator;
    double denominator;
    double value;

    public Rational(double numerator, double denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        // store only 3 digits of precision
        this.value = new BigDecimal(this.numerator / this.denominator).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Rational(String numberRep) {
        int indexOfSlash = numberRep.indexOf("/");
        String num = numberRep.substring(0, indexOfSlash);
        String denom = numberRep.substring(indexOfSlash + 1);
        // crappy java rules on constructor being first statement :\
        // have to repeat code here due to that
        this.numerator = Double.parseDouble(num);
        this.denominator = Double.parseDouble(denom);
        this.value = new BigDecimal(this.numerator / this.denominator).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Rational getReciprocal() {
        return new Rational(this.denominator, this.numerator);
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
        return (long) (this.value);
    }

    @Override
    public int intValue() {
        return (int) (this.value);
    }
    @Override
    public int compareTo(NumberImpl o) {
        double epsilon = 0.00001;

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
