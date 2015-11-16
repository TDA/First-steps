import java.util.Scanner;

/**
 * Created by schandramouli on 11/14/15.
 */
enum Type {
    Iterative,
    Recursive,
    DP
}
public class FindFibonacciNumber {
    public static void main(String[] args) {
        // need to expand this
        Scanner scanner = new Scanner(System.in);
        int fibonacciLimit = scanner.nextInt();
        int second = fibonacci(fibonacciLimit, Type.Iterative);
        System.out.printf("The largest fibonacci number below %d is %d", fibonacciLimit, second);
    }

    static int fibonacci(int n, Type t) {
        // find the nth fibonacci by iteration
        switch (t) {
            case Iterative:
                int first = 0;
                int second = 1;
                while (first + second < n) {
                    int temp = first + second;
                    first = second;
                    second = temp;
                }
                return second;
                // no break needed
            default:
                return 0;
        }

    }


}