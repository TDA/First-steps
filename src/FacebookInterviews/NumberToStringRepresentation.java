package FacebookInterviews;

import java.util.Scanner;

/**
 * Created by schandramouli on 9/30/16.
 */
public class NumberToStringRepresentation {
    public static void main(String[] args) {
        String[] onesAndHundreds = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        String[] doubleDigits = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eightteen", "nineteen"};

        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        StringBuilder number = new StringBuilder("");
        int count = 0;
        String[] appendices = {"", "thousand", "million", "billion"};
        // pretty sure this can be written in a better way,
        // but it works, so lets leave it as is.
        while (num > 0) {
            // get a 3 digit slice
            int digitSlice = num % 1000;
            number.insert(0, appendices[count++] + " ");
            num = num / 1000;
            // special cases first
            int tensAndOnes = digitSlice % 100;
            if (tensAndOnes > 10 && tensAndOnes < 20) {
                number.insert(0, doubleDigits[tensAndOnes % 10] + " ");
                digitSlice = digitSlice - tensAndOnes;
            }
            for (int i = 0; i < 3 && digitSlice > 0; i++) {
                int subDigit = digitSlice % 10;
                digitSlice = digitSlice / 10;
                if (i == 1) {
                    number.insert(0, tens[subDigit] + " ");
                } else {
                    String h = onesAndHundreds[subDigit] + " ";
                    if (i == 2) {
                        h += "hundred and ";
                    }
                    number.insert(0, h);
                }
            }
        }

        System.out.println(number.toString().replaceAll(" +", " "));

    }
}
