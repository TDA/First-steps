import java.time.LocalDate;
import java.util.Date;

/**
 * Created by schandramouli on 7/12/15.
 */
public class date {
    public static void main(String[] args){
        Date d = new Date();
        System.out.println(d);
        LocalDate ld = LocalDate.now();
        System.out.println(ld);

        LocalDate ld2 = LocalDate.of(2025,07,12);
        System.out.println(ld2);

        System.out.println(ld.isAfter(ld2));
        System.out.println(ld2.isAfter(ld));
        System.out.println(ld.atStartOfDay());
        System.out.println(ld.toEpochDay());
        System.out.println(ld2.toEpochDay());
        System.out.println(ld.until(ld2));
    }
}
