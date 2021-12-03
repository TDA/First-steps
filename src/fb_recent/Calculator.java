package fb_recent;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class Calculator {
//
//    Pattern multiplierPattern = Pattern.compile("\\d+\\*\\d+");
//    Pattern divPattern = Pattern.compile("\\d+/\\d+");
//    public int calculate(String s) {
//        String noSpaceString = s.replaceAll("\\s+", "");
//        return calculateHelper(noSpaceString);
//    }
//
//    private int calculateHelper(String s) {
//        System.out.println("Getting to " + s);
//        try {
//            int onlyNumberLeft = Integer.parseInt(s);
//            System.out.println("Parsed " + onlyNumberLeft);
//            return onlyNumberLeft;
//        } catch (NumberFormatException nfe) {
//            System.out.println(nfe.getMessage());
//            // this is still an expression, evaluate recursively by breaking down finer
//            if (s.contains("/")) {
//                while (true) {
//                    Matcher matcher = divPattern.matcher(s);
//                    if (!matcher.find()) break;
//                    String group = matcher.group(0);
//                    System.out.println("found " + group);
//                    if (!group.equals("")) {
//                        String[] split = group.split("/");
//                        int i = Integer.parseInt(split[0]) / Integer.parseInt(split[1]);
//                        s = s.replace(group, i + "");
//                    }
//                }
//
//                return calculateHelper(s);
//            } else if (s.contains("*")) {
//                // split based on the most greedy match for the regex.. i.e. strip all operators on the left and all operators on the right and only get substring
//
//                Matcher matcher = multiplierPattern.matcher(s);
//                while (matcher.find()) {
//                    String group = matcher.group(0);
//                    System.out.println("found " + group);
//                    String[] split = group.split("\\*");
//                    int i = Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
//                    s = s.replace(group, i + "");
//                }
//
//                return calculateHelper(s);
//            } else if (s.contains("+")) {
//                String[] subExpressions = s.split("\\+");
//                System.out.println(Arrays.toString(subExpressions));
//                int count = 0;
//                for (String subExpr : subExpressions) {
//                    count += calculateHelper(subExpr);
//                }
//                return count;
//            } else {
//                String[] subExpressions = s.split("-");
//                System.out.println(Arrays.toString(subExpressions));
//                int count = Integer.parseInt(subExpressions[0]);
//                for (int i = 1; i < subExpressions.length; i++) {
//                    String subExpr = subExpressions[i];
//                    count -= calculateHelper(subExpr);
//                }
//                return count;
//            }
//        }
//    }
//
//    public static void main(String[] args){
//        Calculator calculator = new Calculator();
//        System.out.println("Actual: " + calculator.calculate("5*2*9*7+33"));
//        System.out.println("Expected 663");
//        System.out.println("Actual: " + calculator.calculate("1+1+1"));
//        System.out.println("Expected 3");
//        System.out.println("Actual: " + calculator.calculate("1-1-1"));
//        System.out.println("Expected -1");
//        System.out.println("Actual: " + calculator.calculate("3+2*2"));
//        System.out.println("Expected 7");
//        System.out.println("Actual: " + calculator.calculate(" 3/2 "));
//        System.out.println("Expected 1");
//        System.out.println("Actual: " + calculator.calculate(" 3+5 / 2 "));
//        System.out.println("Expected 5");
//        System.out.println("Actual: " + calculator.calculate("4/3+2"));
//        System.out.println("Expected 3");
//        System.out.println("Actual: " + calculator.calculate("100000000/1/2/3/4/5/6/7/8/9/10"));
//        System.out.println("Expected 27");
//        System.out.println("Actual : " + calculator.calculate("1+2*5/3+6/4*2"));
//        System.out.println("Expected 6");
//        System.out.println("Actual: " + calculator.calculate("4/3/2*2"));
//        System.out.println("Expected 2");
//    }
//}



//    Given a string representing an arithmetic expression with only addition and multiplication operators, return the result of the calculation.
//
//        "2*3+4", return 10
//
//        "2*3+4*2", return 10
//        "6+8" return 14


// Only integers
// Input string
// only add and multiply
// priority multiply > add

class Calculator {
    // define the patterns here
    Pattern multiplierPattern = Pattern.compile("\\d+\\*\\d+"); // "(2)*(3)"

    int calculate(String inputString) {
        // strip all other invalid characters from string

        return calculateHelper(inputString);
    }

    int calculateHelper(String inputString) {
        try {
            int finalNumber = Integer.parseInt(inputString); // "6+8"
            return finalNumber;
        } catch (NumberFormatException nfe) {
            // still stuff to parse through
            if (inputString.contains("*")) { // "2*3+4*2"
                Matcher matcher = multiplierPattern.matcher(inputString); // n = length O(n)
                while (matcher.find()) { // "2*3+4*2" O(k) multiplications and l additions O(k + n)
                    String group = matcher.group(0); // 4*2
                    String[] operands = group.split("\\*"); // 4, 2
                    int product = Integer.parseInt(operands[0]) * Integer.parseInt(operands[1]); // 8
                    inputString = inputString.replace(group, product + ""); // 4*2 => 8 // O(n)
                }
                // "6+8"
                return calculateHelper(inputString);
            } else if (inputString.contains("+")) { // 6 + 8
                String[] operands = inputString.split("\\+"); // multiple individual ints // 6, 8
                int runningSum = Integer.parseInt(operands[0]); // 6
                for (int i = 1; i < operands.length; i++) { // O(l)
                    runningSum += Integer.parseInt(operands[i]); // +8
                }
                return runningSum; // 14
                // inputString = inputString.replace(group, product);
            }
        }
        return -1;
    }

    // TC  = O(k) , k = no of multiplcation ops, WC (O(k * n)) if every op is multiplicative
    // SC = w.c. O(n)
    public static void main(String[] args){
        Calculator calculator = new Calculator();
        System.out.println("Actual: " + calculator.calculate("5*2*9*7+33"));
        System.out.println("Expected 663");
    }
}


