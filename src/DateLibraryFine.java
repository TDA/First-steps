import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by schandramouli on 10/20/15.
 */
public class DateLibraryFine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstDate = scanner.nextLine();
        String secondDate = scanner.nextLine();
        int fine = computeFine(firstDate, secondDate);
        System.out.println(fine);
    }

    public static int computeFine(String s, String t) {
        int day = Integer.parseInt(s.split(" ")[0]);
        int month = Integer.parseInt(s.split(" ")[1]);
        int year = Integer.parseInt(s.split(" ")[2]);

        int day2 = Integer.parseInt(t.split(" ")[0]);
        int month2 = Integer.parseInt(t.split(" ")[1]);
        int year2 = Integer.parseInt(t.split(" ")[2]);

        if (year > year2) {
            return 10000;
        }
        if (month > month2) {
            return Math.abs(month - month2) * 500;
        }
        if ((month < month2) || (month == month2 && day <= day2)) {
            return 0;
        }

        return Math.abs(day - day2) * 15;
    }
}
