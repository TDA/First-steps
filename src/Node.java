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

    public static String postOrderTraversal(Node root) {
        if(root != null) {
            return postOrderTraversal(root.left) + " " + postOrderTraversal(root.right) + " " + root.data;
        }
        return "";
    }

    public static String preOrderTraversal(Node root) {
        if(root != null) {
            return root.data + " " + preOrderTraversal(root.left) + " " + preOrderTraversal(root.right);
        }
        return "";
    }

    public static void print(String s) {
        System.out.println(s);
    }
    public static void main (String [] args) {
        // Create a tree
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.right.left = new Node("D");
        root.right.right = new Node("E");
        root.right.right.right = new Node("F");
        displayTree(root);
        String inOrder = inOrderTraversal(root);
        String postOrder = postOrderTraversal(root);
        String preOrder = preOrderTraversal(root);
        print("In: " + inOrder + " Post: " + postOrder + " Pre: " + preOrder);
        Node c = new Node("C");
        c.left = new Node("D");
        //c.right = new Node("");
        String inOrderC = inOrderTraversal(c);
        String postOrderC = postOrderTraversal(c);
        String preOrderC = preOrderTraversal(c);
        print("In: " + inOrderC + " Post: " + postOrderC + " Pre: " + preOrderC);
        if(inOrder.contains(inOrderC) && postOrder.contains(postOrderC) ||
                inOrder.contains(inOrderC) && preOrder.contains(preOrderC)) {
            System.out.println("The tree " + inOrderC);
            System.out.println("is a Subtree of ");
            System.out.println(inOrder);
        }

    }
}
