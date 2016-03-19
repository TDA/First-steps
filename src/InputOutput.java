import java.util.Scanner;

/**
 * Created by schandramouli on 3/18/16.
 */
public class InputOutput {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int x = sc.nextInt();
        Double y = sc.nextDouble();
        String s = sc.nextLine();
// It wasnt working cuz u reversed the order of input
        //Complete this code

        System.out.println("String: "+s);
        System.out.println("Double: "+y);
        System.out.println("Int: "+x);
    }
}
