import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class RomanNumerals {
    Map<String, Integer> romanNumerals = Map.ofEntries(
            entry("I", 1),
            entry("IV", 4),
            entry("V", 5),
            entry("IX", 9),
            entry("X", 10),
            entry("XL", 40),
            entry("L", 50),
            entry("XC", 90),
            entry("C", 100),
            entry("CD", 400),
            entry("D", 500),
            entry("CM", 900),
            entry("M", 1000)
    );

    Map<Integer, String> romanNumeralsInversed =
            romanNumerals.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public int romanToInt(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            String token;
            if (i + 1 != s.length()) {
                token = s.substring(i, i + 2);
                if (romanNumerals.containsKey(token)) {
                    sum += romanNumerals.get(token);
                    i++;
                    continue;
                }
            }
            token = "" + s.charAt(i);
            sum += romanNumerals.get(token);
        }
        return sum;
    }

    public String intToRoman(int num) {
        StringBuilder s = new StringBuilder();
        int[] denoms = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        while (num != 0) {
            int i = 0;
            while (i != denoms.length && num / denoms[i] != 0) {
                i++;
            }
            s.append(romanNumeralsInversed.get(denoms[i - 1]));
            num = num - denoms[i - 1];
        }
        return s.toString();
    }

    public static void main(String[] args) {
//        System.out.println(new RomanNumerals().romanToInt("III"));
//        System.out.println(new RomanNumerals().romanToInt("LVIII"));
//        System.out.println(new RomanNumerals().romanToInt("MCMXCIV"));
//        System.out.println(new RomanNumerals().intToRoman(3));
//        System.out.println(new RomanNumerals().intToRoman(58));
        System.out.println(new RomanNumerals().intToRoman(1994));
    }

}
