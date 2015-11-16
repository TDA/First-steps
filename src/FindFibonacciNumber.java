import java.util.ArrayList;
import java.util.HashMap;
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
    // find the fibonacci number below the given number
    // NOTE: NOT THE SAME AS THE nth Fibonacci problem
    // recursive solution is wayyyy worse in this particular prob.
    // statics are anyway defaulted to 0
    static int recCount;
    static int iterCount;
    static int dpCount;
    // need an HashMap for dp, as we dont know how many elements we are computing
    static HashMap<Integer, Integer> hm = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fibonacciLimit = scanner.nextInt();

        // iterative way, clean and simple
        int iterativeFibo = fibonacci(fibonacciLimit, Type.Iterative);
        System.out.printf("The largest iterative fibonacci number below %d is %d\n", fibonacciLimit, iterativeFibo);
        System.out.println("Iterative made " + iterCount + " function calls");

       // this is funny cuz for the recursive solution you still need the iterative check :|
        int prevRecursiveFibo = 0;
        int i;
        for (i = 1; i < fibonacciLimit; i++) {
            int recursiveFibo = fibonacci(i, Type.Recursive);
            if (recursiveFibo > fibonacciLimit) {
                break;
            }
            prevRecursiveFibo = recursiveFibo;
        }

        int recursiveFibo = prevRecursiveFibo;
        System.out.printf("The largest recursive fibonacci number below %d is %d\n", fibonacciLimit, recursiveFibo);
        System.out.println("Recursive made " + recCount + " function calls");

        hm.put(0, 0);
        hm.put(1, 1);
        int prevDpFibo = 0;
        for (i = 1; i < fibonacciLimit; i++) {
            int dpFibo = fibonacci(i, Type.DP);
            if (dpFibo > fibonacciLimit) {
                break;
            }
            prevDpFibo = dpFibo;
        }
        int dpFibo = prevDpFibo;
        System.out.printf("The largest dp fibonacci number below %d is %d\n", fibonacciLimit, dpFibo);
        System.out.println("DP made " + dpCount + " function calls");
        System.out.println(hm);

    }

    static int fibonacci(int n, Type t) {
        // find the nth fibonacci by iteration
        switch (t) {
            case Iterative:
                int first = 0;
                int second = 1;
                int i = 0;
                while (first + second < n) {
                    int temp = first + second;
                    first = second;
                    second = temp;
                    ++iterCount;
                    i++;
                }
                System.out.println("Which was the " + i + "th fibonacci number");
                return second;
                // no break needed due to return
            case Recursive:
                ++recCount;
                //System.out.println("n is " + n);
                if (n == 0) {
                    return 0;
                }
                if (n == 1) {
                    return 1;
                }
                return fibonacci(n - 1, Type.Recursive) + fibonacci(n - 2, Type.Recursive);
            case DP:
                ++dpCount;
                // dp way, store caches
                // if already computed, return that
                if (hm.containsKey(n)) {
                    return hm.get(n);
                } else {
                    // we need to compute and store
                    // call them recursively, but store
                    int fiboForN = fibonacci(n - 1, Type.DP) + fibonacci(n - 2, Type.DP);
                    //System.out.println(n + "     " + fiboForN);
                    hm.put(n, fiboForN);
                    return fiboForN;
                }

            default:
                return 0;
        }

    }


}