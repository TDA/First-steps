package MillerIndices;

import java.util.Scanner;
import NumberSystems.Rational;

/**
 * Created by schandramouli on 12/7/15.
 */
public class MillerIndices {
    Rational x;
    Rational y;
    Rational z;

    public MillerIndices(Rational x, Rational y, Rational z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter x, y and z");
        
        MillerIndices millerIndices =  new MillerIndices(
                new Rational(scanner.nextDouble(), scanner.nextDouble()),
                new Rational(scanner.nextDouble(), scanner.nextDouble()),
                new Rational(scanner.nextDouble(), scanner.nextDouble())
        );
    }
}
