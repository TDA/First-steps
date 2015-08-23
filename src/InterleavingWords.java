import java.util.Scanner;

/**
 * Created by schandramouli on 8/23/15.
 */
public class InterleavingWords {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        String firstWord = scanner.nextLine();
        String secondWord = scanner.nextLine();
        String outputWord = "";
        char[] firstWordArray = firstWord.toCharArray();
        char[] secondWordArray = secondWord.toCharArray();
        int i = 0,
            j = 0;
        for(; i < secondWordArray.length; i = i + 2, j++ ) {
            try {
                outputWord = outputWord + firstWordArray[j] + secondWordArray[i] + secondWordArray[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                outputWord = outputWord + firstWordArray[j] + secondWordArray[i];
            }
        }
        if(j < firstWordArray.length) {
            outputWord += firstWord.substring(j, firstWord.length());
        }


        System.out.println(outputWord);
    }
}
