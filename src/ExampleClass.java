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
    }
}
