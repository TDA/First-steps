/**
 * Created by schandramouli on 11/17/15.
 */
public class Rational extends Number implements Comparable {
    double numerator;
    double denominator;

    public Rational(double x, double denominator) {
        this.numerator = x;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return this.numerator + "";
    }

    @Override
    public double doubleValue() {
        return this.numerator;
    }

    @Override
    public float floatValue() {
        return (float) this.numerator;
    }

    @Override
    public long longValue() {
        return (long) this.numerator;
    }

    @Override
    public int intValue() {
        return (int) this.numerator;
    }

    @Override
    public int compareTo(Object o) {
        double epsilon = 0.00001;
        if (Math.abs(this.numerator - ((Complex) o).x) < epsilon) {
            // they are equal, return 0
            return 0;
        }
        // only care about numerator values
        if (this.numerator > ((Complex) o).x) {
            return 1;
        } else {
            return -1;
        }
    }
}
