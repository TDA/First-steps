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
        // assumption is that all the operators are 0-9, if more than one char, use spaces and stringbuilder to make it
        // easier. also, bodmas =~= pemdas
        bodmasMap.put('-', 0);
        bodmasMap.put('+', 1);
        bodmasMap.put('*', 2);
        bodmasMap.put('/', 3);

        String example = "7+3*4-2*2";
        // expected o/p:
        // int expectedAnswer = 17;

        // so the intuition here is that both these perform the exact same higher level function, i.e. we
        // have a need to check for next/prev operators before constructing the tree. The string traversal
        // makes this semantically more valid, as it takes the approach of a grammar parser, which is what
        // I would expect an SDE3 to be able to determine. The stack based postfix notation construction is
        // what an SDE1 must absolutely know, as this is out of school books.

//        stringTraversal(example);
        getPostfixNotation(example);
        // constructing the tree at this point is trivial, loop through, use a stack for numbers, whenever u encounter
        // an operator, pop last 2 elements, construct node, keep pointer to node and update as necessary.
    }

    private static String getPostfixNotation(String example) {
        Stack<Character> operators = new Stack<>();
        String finalopString = "";

        for (int i = 0; i < example.length(); i++) {
            char c = example.charAt(i);
            if (!isOperator(c)) {
                // means a number for our purposes
                finalopString += c;
                System.out.println("just adding num");
            } else {
                // means an operator for our purposes, check precedence of this operator to the previous :D
                if (operators.empty()) {
                    operators.push(c);
                    continue;
                }
                System.out.println(operators);
                char prevOperator = operators.peek();
                if (isHigherPrecedence(c, prevOperator)) {
                    // if c has higher precedence, we can simply push it onto the operators
                    operators.push(c);
                } else {
                    // we need to pop the last n operators of higher precedence and push onto output string
                    System.out.println("found lower precedence");
                    while (!operators.empty()) {
                        prevOperator = operators.pop();
                        if (isHigherPrecedence(prevOperator, c))
                            finalopString += prevOperator;
                    }
                    operators.push(c);
                }
            }
        }
        while (!operators.empty()) {
            finalopString += operators.pop();
        }
        System.out.println(finalopString);
        return finalopString;
    }

    private static void stringTraversal(String example) {
        for (int i = 0; i < example.length(); i++) {
            char c = example.charAt(i);
            if (! isOperator(c)) {
                // means a number for our purposes
            } else {
                // means an operator for our purposes, check precedence of this operator to the next :D
                char nextOperator = getNextOperator(example, i + 1);
                if (isHigherPrecedence(c, nextOperator)) {
                    // if c has higher precedence, we can simply form the node here

                } else {
                    // we need to finish that node first before this node :D
                    // use a recursive approach to do this, will allow u to populate the tree
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
