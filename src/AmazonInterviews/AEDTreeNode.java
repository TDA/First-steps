package AmazonInterviews;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by schandramouli on 11/29/16.
 */
public class AEDTreeNode {
    // typical binary tree, nothing special here
    AEDTreeNode left;
    AEDTreeNode right;
    char value;
    static HashMap<Character, Integer> bodmasMap = new HashMap<>();

    public static void main(String[] args) {
        // write the runner/driver here
        bodmasMap.put('+', 0);
        bodmasMap.put('-', 1);
        bodmasMap.put('*', 2);
        bodmasMap.put('/', 3);

        String example = "7+3*4-2";
        // expected o/p:
        // int expectedAnswer = 17;

        for (int i = 0; i < example.length(); i++) {
            char c = example.charAt(i);
            if (! isOperator(c)) {
                // means a number for our purposes
            } else {
                // means an operator for our purposes, check precedence of this operator to the next :D
                char nextOperator = getNextOperator(example, i + 1);
                if (isHigherPrecedence(c, nextOperator)) {
                    // if higher precedence, we can simply form the node here
                    AEDTreeNode leftOperand = new AEDTreeNode(example.charAt(i - 1));
                    AEDTreeNode rightOperand = new AEDTreeNode(example.charAt(i + 1));
                    AEDTreeNode operator = new AEDTreeNode(leftOperand, rightOperand, c);
                } else {
                    // we need to finish that node first before this node :D
                }
            }
        }
    }

    private static boolean isHigherPrecedence(char c, char nextOperator) {
        // returns true if precedence of c >= nextOperator
        return bodmasMap.get(c) >= bodmasMap.get(nextOperator);
    }

    private static char getNextOperator(String example, int nextIndex) {
        for (int i = nextIndex; i < example.length(); i++) {
            char nextC = example.charAt(i);
            if (isOperator(nextC))
                return nextC;
        }
        return 0;
    }

    private static boolean isOperator(char c) {
        return (Character.getNumericValue(c) == -1);
    }

    public AEDTreeNode() {
        left = null;
        right = null;
        value = '\0';
    }

    public AEDTreeNode(char c) {
        left = null;
        right = null;
        value = c;
    }

    public AEDTreeNode(AEDTreeNode left, AEDTreeNode right, char value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}
