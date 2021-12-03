package fb_recent;

import java.util.HashMap;
import java.util.Map;

public class NumberToWords {
    Map<Integer, String> wordsLessThanTwenty = new HashMap<>() {{
        put(0, "");
        put(1, "One");
        put(2, "Two");
        put(3, "Three");
        put(4, "Four");
        put(5, "Five");
        put(6, "Six");
        put(7, "Seven");
        put(8, "Eight");
        put(9, "Nine");
        put(10, "Ten");
        put(11, "Eleven");
        put(12, "Twelve");
        put(13, "Thirteen");
        put(14, "Fourteen");
        put(15, "Fifteen");
        put(16, "Sixteen");
        put(17, "Seventeen");
        put(18, "Eighteen");
        put(19, "Nineteen");
    }};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        StringBuilder stringBuilder = new StringBuilder();
        int numberOfTriplets = 0;
        while (num > 0) {
            int triplet = num % 1000;
            if (triplet > 0)
                stringBuilder.insert(0, helper(triplet) + " " + THOUSANDS[numberOfTriplets] + " ");
            num = (num / 1000);
            numberOfTriplets++;
        }
        return stringBuilder.toString().trim();
    }

    private String helper(int triplet) {
        String s = "";
        int ones = triplet % 10;
        if (triplet % 100 < 20) {
            s = wordsLessThanTwenty.get(triplet % 100);
        } else {
            s = s  + " " +  wordsLessThanTwenty.get(ones);
            String tens = TENS[(triplet % 100) / 10];
            s = tens + s;
        }
        int hundoDigit = triplet / 100;
        if (hundoDigit != 0) {
            String hundreds = wordsLessThanTwenty.get(hundoDigit);
            s = hundreds + " Hundred " + s;
        }
        return s.trim();
    }

    public static void main(String[] args){
        NumberToWords numberToWords = new NumberToWords();
        System.out.println(numberToWords.numberToWords(1_000_000_000));
        System.out.println(numberToWords.numberToWords(1_234_567_893));
        System.out.println(numberToWords.numberToWords(12345));
        System.out.println(numberToWords.numberToWords(20));
        System.out.println(numberToWords.numberToWords(101));
        System.out.println(numberToWords.numberToWords(100000));
    }
}
