import java.util.Scanner;

/**
 * Created by schandramouli on 8/25/15.
 */
public class CompressWords {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        char currentLetter = s.charAt(0);
        int letterCount = 1;
        String finalString = "";
        for (int i = 1; i < s.length(); i++) {
            char newLetter = s.charAt(i);
            if(newLetter == currentLetter) {
                letterCount++;
            } else {
                finalString += currentLetter + "" + letterCount;
                letterCount = 1;
                currentLetter = newLetter;
            }
        }
        finalString += currentLetter + "" + letterCount;
        if (! (finalString.length() < s.length())) {
            System.out.println(s);
        } else {
            System.out.println(finalString);
        }
    }
}
