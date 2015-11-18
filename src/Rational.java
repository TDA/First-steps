/**
 * Created by schandramouli on 11/17/15.
 */
public class Rational extends NumberImpl implements Comparable<NumberImpl> {
    double numerator;
    double denominator;
    double value;

    public Rational(double x, double denominator) {
        this.numerator = x;
        this.denominator = denominator;
        this.value = this.numerator / this.denominator;
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
        // only care about numerator values
        if (this.doubleValue() > ((Rational) o).doubleValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
