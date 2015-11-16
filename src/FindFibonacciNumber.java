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
    static int recCount = 0;
    static int iterCount = 0;
    public static void main(String[] args) {
        // need to expand this
        Scanner scanner = new Scanner(System.in);
        int fibonacciLimit = scanner.nextInt();
        int iterativeFibo = fibonacci(fibonacciLimit, Type.Iterative);
        System.out.printf("The largest iterative fibonacci number below %d is %d\n", fibonacciLimit, iterativeFibo);

       // this is funny cuz for the recursive solution you still need the iterative check :|
        int i;
        for (i = 1; i < fibonacciLimit; i++) {
            int recursiveFibo = fibonacci(i, Type.Recursive);
            if (recursiveFibo > fibonacciLimit) {
                break;
            }
        }
        int recursiveFibo = fibonacci(i - 1, Type.Recursive);
        System.out.printf("The largest recursive fibonacci number below %d is %d\n", fibonacciLimit, recursiveFibo);

    }

    static int fibonacci(int n, Type t) {
        // find the nth fibonacci by iteration
        switch (t) {
            case Iterative:
                System.out.println(iterCount++);
                int first = 0;
                int second = 1;
                while (first + second < n) {
                    int temp = first + second;
                    first = second;
                    second = temp;
                }
                return second;
                // no break needed due to return
            case Recursive:
                //System.out.println("n is " + n);
                if (n == 0) {
                    return 0;
                }
                if (n == 1) {
                    return 1;
                }
                return fibonacci(n - 1, Type.Recursive) + fibonacci(n - 2, Type.Recursive);
            default:
                return 0;
        }

    }


}