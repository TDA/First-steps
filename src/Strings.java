/**
 * Created by schandramouli on 7/11/15.
 */
public class Strings {
    public static void main(String[] args){
        StringBuilder b = new StringBuilder();
        for(int i = 0 ; i < 10; i++)
            b.append(i);

        System.out.println(b.toString());
        System.out.println(b.indexOf("7"));
        System.out.println("sdf".matches("s.f"));
    }
}
