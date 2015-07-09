import java.util.Random;

/**
 * Created by schandramouli on 7/8/15.
 */
public class hello_world {
    public static void main(String args[]){
        String s = "Hello World";
        String s1 = new String("Hello World");
        System.out.println(s + " " + s1.length());
        Random gen = new Random(1);
        System.out.println(gen.nextInt(1888));
    }
}
