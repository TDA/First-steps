import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 9/30/16.
 */
public class NumberToStringRepresentation {
    public static void main(String[] args) {
        HashMap<Integer, String> onesAndHundreds = new HashMap<>();
        onesAndHundreds.put(0, "");
        onesAndHundreds.put(1, "one");
        onesAndHundreds.put(2, "two");
        onesAndHundreds.put(3, "three");
        onesAndHundreds.put(4, "four");
        onesAndHundreds.put(5, "five");
        onesAndHundreds.put(6, "six");
        onesAndHundreds.put(7, "seven");
        onesAndHundreds.put(8, "eight");
        onesAndHundreds.put(9, "nine");



        HashMap<Integer, String> tens = new HashMap<>();
        tens.put(0, "");
        tens.put(1, "ten");
        tens.put(2, "twenty");
        tens.put(3, "thirty");
        tens.put(4, "forty");
        tens.put(5, "fifty");
        tens.put(6, "sixty");
        tens.put(7, "seventy");
        tens.put(8, "eighty");
        tens.put(9, "ninety");

        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        int count = 0;
        String[] appendices = {"", "Thousand", "Million", "Billion"};
        StringBuilder number = new StringBuilder("");
        while (num > 0) {
            // get a 3 digit slice
            int digitSlice = num % 1000;
            num = num / 1000;
            number.insert(0, appendices[count++]);
            for (int i = 0; i < 3; i++) {
                int subDigit = digitSlice % 10;
                digitSlice = digitSlice / 10;
                if (i == 1) {
                    number.insert(0, tens.get(subDigit));
                    System.out.println(tens.get(subDigit));
                } else {
                    number.insert(0, onesAndHundreds.get(subDigit));
                    System.out.println(onesAndHundreds.get(subDigit));
                }

            }
        }

        System.out.println(number.toString());

    }
}
