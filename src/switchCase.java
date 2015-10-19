import java.util.ArrayList;

/**
 * Created by schandramouli on 10/19/15.
 */
public class switchCase {
    public static void main(String[] args) {
        int code = 10;
        switch (code) {
            case (37):
                break;

        }
        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        a.forEach(System.out::println);
        a.forEach((number) -> System.out.println(number));
    }
}
