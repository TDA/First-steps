import sun.misc.Regexp;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * Created by schandramouli on 10/20/15.
 */
public class TimeConversionAMPM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        //System.out.println(string);

        String pattern = "(\\d{2}):(\\d{2}):(\\d{2})(\\w{2})";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(string);

        if (m.find( )) {
            switch (m.group(4)) {
                case "AM":
                    System.out.println(m.group(1) + ":" + m.group(2) + ":" + m.group(3));
                    break;
                case "PM":
                    System.out.println((Integer.parseInt(m.group(1)) + 12) + ":" + m.group(2) + ":" + m.group(3));
                    break;
            }
        } else {
            System.out.println("NO MATCH");
        }
    }
}
