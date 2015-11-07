/**
 * Created by schandramouli on 11/7/15.
 */
public class BinaryTreeBalance {

    public static void main(String[] args) {
        // create a bin tree first
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.left.left = new Node("G");
        root.left.right = new Node("H");
        root.right.left = new Node("D");
        root.right.right = new Node("E");
        root.right.right.right = new Node("F");
        //root.right.right.right.right = new Node("K");
        //root.right.right.right.right.right = new Node("J");
        System.out.println(findHeightDifference(root.left, root.right));
        // this is balanced, so lets compute and check
    }

    public static boolean isTreeBalanced(Node root) {
        // compute whether each subtree is balanced
        // if both nodes are null, its balanced,
        // so return true
        if (root.left == null && root.right == null) {
            return true;
        } else if (root.left != null && root.right != null){
            return isTreeBalanced(root.left) && isTreeBalanced(root.right);
        } else if (root.left != null){
            return isTreeBalanced(root.left);
        } else {
            return isTreeBalanced(root.right);
        }
    }

    public static int findHeightDifference (Node Left, Node Right) {
        if (Left == null && Right == null) {
            System.out.println(0);
            return 0;
        } else if (Left != null && Right != null){
            System.out.println(Left.data);
            System.out.println(Right.data);
            return findHeightDifference(Left.left, Left.right) - findHeightDifference(Right.left, Right.right);
        } else if (Left != null){
            System.out.println(Left.data);
            return findHeightDifference(Left.left, Left.right);
        } else {
            System.out.println(Right.data);
            return 0 - findHeightDifference(Right.left, Right.right);
        }
    }
}
