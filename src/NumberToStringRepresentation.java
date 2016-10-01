import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 9/30/16.
 */
public class NumberToStringRepresentation {
    public static void main(String[] args) {
        String[] onesAndHundreds = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        int count = 0;
        String[] appendices = {"", "thousand", "million", "billion"};
        StringBuilder number = new StringBuilder("");
        while (num > 0) {
            // get a 3 digit slice
            int digitSlice = num % 1000;
            num = num / 1000;
            number.insert(0, appendices[count++] + " ");
            for (int i = 0; i < 3 && digitSlice > 0; i++) {
                int subDigit = digitSlice % 10;
                digitSlice = digitSlice / 10;
                if (i == 1) {
                    number.insert(0, tens[subDigit] + " ");
                    System.out.println(tens[subDigit]);
                } else {
                    String h = onesAndHundreds[subDigit] + " ";
                    if (i == 2) h += "hundred and ";
                    number.insert(0, h);
                    System.out.println(onesAndHundreds[subDigit]);
                }
            }
        }

        System.out.println(number.toString());

    }
}
