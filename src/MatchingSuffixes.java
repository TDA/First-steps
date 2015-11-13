import java.util.Scanner;

/**
 * Created by schandramouli on 11/13/15.
 */
public class MatchingSuffixes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String baseString = scanner.nextLine();
        int noOfMatches = baseString.length();
        int noOfSubString = 1;

        for (int i = 0; (noOfSubString + i) < baseString.length(); i++) {
            if (baseString.charAt(i) == baseString.charAt(i + noOfSubString)) {
                // they matched, so lets continue
                noOfMatches++;
            } else {
                // didnt match, lets move to next substring (noOfSub ++)
                noOfSubString++;
                // reset i
                i = -1;
            }
        }
        System.out.println(noOfMatches);
    }
}
