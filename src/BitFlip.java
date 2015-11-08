import java.util.Scanner;

/**
 * Created by schandramouli on 11/8/15.
 */
public class BitFlip {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        Integer k = i ^ j;
        System.out.println(Integer.bitCount(k));
    }
}
