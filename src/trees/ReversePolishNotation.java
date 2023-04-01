package trees;

import java.net.Inet4Address;
import java.util.Stack;

public class ReversePolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            switch (s) {
                case "+" -> {
                    int val = stack.pop() + stack.pop();
                    stack.push(val);
                }
                case "-" -> {
                    int val = -stack.pop() + stack.pop();
                    stack.push(val);
                }
                case "*" -> {
                    int val = stack.pop() * stack.pop();
                    stack.push(val);
                }
                case "/" -> {
                    int divisor = stack.pop();
                    int val = stack.pop() / divisor;
                    stack.push(val);
                }
                default -> stack.push(Integer.parseInt(s));
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens = {"2","1","+","3","*"};
        System.out.println(new ReversePolishNotation().evalRPN(tokens));
        String[] tokens1 = {"4","13","5","/","+"};
        System.out.println(new ReversePolishNotation().evalRPN(tokens1));

    }
}
