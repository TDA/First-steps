package common;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by schandramouli on 8/22/15.
 */
public class Node <T extends Comparable<T>> {
    public T data;
    public Node left;
    public Node right;

    public Node (T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public static void displayTree(Node root) {
        String s = inOrderTraversal(root);
        System.out.println(s);
    }

    public static String inOrderTraversal(Node root) {
        if(root != null) {
            return inOrderTraversal(root.left) + " " + root.data + " " + inOrderTraversal(root.right);
        }
        return "0";
    }

    public static String postOrderTraversal(Node root) {
        if(root != null) {
            return postOrderTraversal(root.left) + " " + postOrderTraversal(root.right) + " " + root.data;
        }
        return "0";
    }

    public static String preOrderTraversal(Node root) {
        if(root != null) {
            return root.data + " " + preOrderTraversal(root.left) + " " + preOrderTraversal(root.right);
        }
        return "0";
    }

    public static void levelOrderTraversal(Node newNode) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(newNode);
        while(! queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node root = queue.poll();
                System.out.print(root.data + " ");
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
                size--;
            }
            System.out.println();
        }
    }

    public static String removeSpaces(String s) {
        s = s.replaceAll("\\s+", " ");
        return s;
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static boolean isSubSequence(String s, String t) {
        if (s.length() == 0) {
            return false;
        }
        if (t.length() == 0) {
            return true;
        }
        int count = 0;
        s = s.replaceAll("\\s", "");
        t = t.replaceAll("\\s", "");
        for (int i = 0; i < s.length(); ) {
            for (int j = 0; j < t.length(); ) {
                if(i < s.length()) {
                    if (s.charAt(i) == t.charAt(j)) {
                        i++;
                        j++;
                        count++;
                    } else {
                        i++;
                    }
                    if(count == t.length()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void main (String [] args) {

        // Create a tree
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.left.left = new Node("G");
        root.left.right = new Node("H");
        root.right.left = new Node("D");
        root.right.right = new Node("E");
        root.right.right.right = new Node("F");
        displayTree(root);
        String inOrder = removeSpaces(inOrderTraversal(root));
        String postOrder = removeSpaces(postOrderTraversal(root));
        String preOrder = removeSpaces(preOrderTraversal(root));
        print("In: " + inOrder + " Post: " + postOrder + " Pre: " + preOrder);
        Node c = new Node("A");
        c.left = new Node("B");
        c.right = new Node("C");
        c.left.left = new Node("G");
        String inOrderC = removeSpaces(inOrderTraversal(c));
        String postOrderC = removeSpaces(postOrderTraversal(c));
        String preOrderC = removeSpaces(preOrderTraversal(c));
        print("In: " + inOrderC + " Post: " + postOrderC + " Pre: " + preOrderC);
        if(isSubSequence(inOrder, inOrderC) && (isSubSequence(postOrder, postOrderC) || isSubSequence(preOrder, preOrderC))) {
            System.out.println("The tree " + inOrderC);
            System.out.println("is a Subtree of ");
            System.out.println(inOrder);
        }

    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
