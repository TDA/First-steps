/**
 * Created by schandramouli on 8/29/15.
 */
public class ExampleClass {
    public static void main(String[] args) {
        for (String arg : args) {

        }
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
        }
        int i = 2;
        String c[] = {"even", "odd"};
        System.out.println(c[i%2]);

        int a = 5;
        int b = 10;
        System.out.println(a + " " + b);
        a = a ^ b;
        b = b ^ a;
        a = a ^ b;
        System.out.println(a + " " + b);
        // I could have won, and I should have.
        // But you did not, and that's all that matters.
    }
}
