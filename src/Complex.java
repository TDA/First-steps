/**
 * Created by schandramouli on 11/17/15.
 */
public class Complex extends Number implements Comparable {
    double x;
    double y;

    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x + "";
    }

    @Override
    public double doubleValue() {
        return this.x;
    }

    @Override
    public float floatValue() {
        return (float) this.x;
    }

    @Override
    public long longValue() {
        return (long) this.x;
    }

    @Override
    public int intValue() {
        return (int) this.x;
    }

    @Override
    public int compareTo(Object o) {
        double epsilon = 0.00001;
        if (Math.abs(this.x - ((Complex) o).x) < epsilon) {
            // they are equal, return 0
            return 0;
        }
        // only care about numerator values
        if (this.x > ((Complex) o).x) {
            return 1;
        } else {
            return -1;
        }
    }
}
