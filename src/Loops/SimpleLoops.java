package Loops;

import java.util.Scanner;

/**
 * Created by schandramouli on 4/24/17.
 */
public class SimpleLoops {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        for (int i = 1; i < 11; i++)
            System.out.println(N + " x " + i + " = " + N * i);
    }
}
