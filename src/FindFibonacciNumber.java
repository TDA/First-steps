import java.util.Scanner;

/**
 * Created by schandramouli on 11/14/15.
 */
public class FindFibonacciNumber {
    public static void main(String[] args) {
        int first = 0;
        int second = 1;
        Scanner scanner = new Scanner(System.in);
        int fibonacciLimit = scanner.nextInt();
        while (first + second < fibonacciLimit) {
            int temp = first + second;
            first = second;
            second = temp;
        }
        System.out.printf("The largest fibonacci number below %d is %d", fibonacciLimit, second);
    }
}

asdvasdv