package fb_recent;

import java.util.Arrays;

public class CountAndSay {
    public String countAndSay(int n) {
        if (n == 1) return "1";
        String[] previousSay = new String[n + 1];
        previousSay[0] = "";
        previousSay[1] = "1";
        for (int i = 2; i <= n; i++) {
            previousSay[i] = say(previousSay[i - 1]);
        }
        System.out.println(Arrays.toString(previousSay));
        return previousSay[n];
    }

    public String say(String num) {
        int count = 1;
        char prevChar = num.charAt(0);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < num.length(); i++) {
            if (num.charAt(i) == prevChar) {
                count++;
            } else {
                stringBuilder.append(count);
                stringBuilder.append(prevChar);
                prevChar = num.charAt(i);
                count = 1;
            }
        }
        stringBuilder.append(count);
        stringBuilder.append(prevChar);
        return stringBuilder.toString();
    }

    public static void main(String[] args){
        CountAndSay countAndSay = new CountAndSay();
        System.out.println(countAndSay.countAndSay(1));
        System.out.println(countAndSay.countAndSay(4));
        System.out.println(countAndSay.countAndSay(10));
    }
}
