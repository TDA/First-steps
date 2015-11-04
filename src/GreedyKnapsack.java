import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/3/15.
 */
public class GreedyKnapsack {
    public static void main(String[] args) {
        Scanner stdin=new Scanner(System.in);
        int n=stdin.nextInt(),
                k=stdin.nextInt();
        int prices[]=new int[n];
        for(int i=0;i<n;i++) {
            prices[i] = stdin.nextInt();
        }

        Arrays.sort(prices);
        int sum = 0;
        int i;
        for (i = 0; i < prices.length; i++) {
            if (sum + prices[i] < k) {
                sum += prices[i];
            } else {
                break;
            }
        }
        int answer = i;
        // Compute the final answer from n,k,prices
        System.out.println(answer);
    }
}
