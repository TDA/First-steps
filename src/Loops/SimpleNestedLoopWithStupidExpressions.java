package Loops;

import java.util.Scanner;
/**
 * Created by schandramouli on 4/24/17.
 */
public class SimpleNestedLoopWithStupidExpressions {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i=0;i<t;i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();
            int currTerm = 0;
            for (int j = 0; j < n; j++) {
                // get the 2^n * b term
                int stupidTerm = (int)Math.pow(2, j) * b;
                currTerm = stupidTerm + currTerm;
                if (j < n - 1)
                    System.out.print(a + currTerm + " ");
                else
                    System.out.print(a + currTerm);
            }
            System.out.println();
        }
        in.close();
    }
}
