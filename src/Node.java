/**
 * Created by schandramouli on 8/22/15.
 */
public class Node {
    String data;
    Node left;
    Node right;

    public Node (String data) {
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
        return "";
    }

    public static void main (String [] args) {
        // Create a tree
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.right.left = new Node("D");
        root.right.right = new Node("E");
        displayTree(root);
        String inOrder = inOrderTraversal(root);
        Node c = new Node("C");
        c.right = new Node("E");
        String inOrderC = inOrderTraversal(c);
        if(inOrder.contains(inOrderC)) {
            System.out.println("Subtree");
        }

    }
}
