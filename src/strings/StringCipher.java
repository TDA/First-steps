package strings;

public class StringCipher {
    String rotationalCipher(String input, int rotationFactor) {
        // Write your code here
        int rotationForAlpha = rotationFactor % 26;
        int rotationForNum = rotationFactor % 10;
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (isAlpha(c)) {
                int sub = 26;
                int end = isCaps(c) ? 90 : 122;
                int diff = end - c;
                if (rotationForAlpha <= diff) {
                    char x = (char) (c + rotationForAlpha);
                    sb.append(x);
                } else {
                    char x = (char) (c + rotationForAlpha - sub);
                    sb.append(x);
                }
            } else if (isNum(c)) {
                int diff = '9' - c;
                int sub = 10;
                if (rotationForNum <= diff) {
                    char x = (char) (c + rotationForNum);
                    sb.append(x);
                } else {
                    char x = (char) (c + rotationForNum - sub);
                    sb.append(x);
                }

            } else {
                // copy as is
                sb.append(c);
            }
        }

        return sb.toString();

    }

    boolean isAlpha(char c) {
        return isSmallChar(c) || isCaps(c);
    }

    boolean isSmallChar(char c) {
        return (c >= 97 && c <= 122);
    }

    boolean isCaps(char c) {
        return (c >= 65 && c <= 90);
    }


    boolean isNum(char c) {
        return (c >= 48 && c <= 57);
    }










    // These are the tests we use to determine if the solution is correct.
    // You can add your own at the bottom.
    int test_case_number = 1;
    void check(String expected, String output) {
        boolean result = (expected.equals(output));
        char rightTick = '\u2713';
        char wrongTick = '\u2717';
        if (result) {
            System.out.println(rightTick + " Test #" + test_case_number);
        }
        else {
            System.out.print(wrongTick + " Test #" + test_case_number + ": Expected ");
            printString(expected);
            System.out.print(" Your output: ");
            printString(output);
            System.out.println();
        }
        test_case_number++;
    }
    void printString(String str) {
        System.out.print("[\"" + str + "\"]");
    }

    public void run() {
        String input_1 = "All-convoYs-9-be:Alert1.";
        int rotationFactor_1 = 4;
        String expected_1 = "Epp-gsrzsCw-3-fi:Epivx5.";
        String output_1 = rotationalCipher(input_1, rotationFactor_1);
        check(expected_1, output_1);

        String input_2 = "abcdZXYzxy-999.@";
        int rotationFactor_2 = 200;
        String expected_2 = "stuvRPQrpq-999.@";
        String output_2 = rotationalCipher(input_2, rotationFactor_2);
        check(expected_2, output_2);

        String input_3 = "1234";
        int rotationFactor_3 = 21;
        String expected_3 = "2345";
        String output_3 = rotationalCipher(input_3, rotationFactor_3);
        check(expected_3, output_3);

        // Add your own test cases here

    }

    public static void main(String[] args) {
        new StringCipher().run();
    }

}
