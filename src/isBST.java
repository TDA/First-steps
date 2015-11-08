/**
 * Created by schandramouli on 11/7/15.
 */
public class isBST {
    public static void main(String[] args) {
        // is this a binary search tree?
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // bst should be like 5
        //       3           8
        //  2       4   7       9
        //1            6         10
//        Node<Integer> root = new Node(1);
//        root.left = new Node(2);
//        root.right = new Node(3);
//        root.left.left = new Node(4);
//        root.left.left.left = new Node(5);
//        root.left.right = new Node(6);
//        root.right.left = new Node(7);
//        root.right.right = new Node(8);
//        root.right.right.right = new Node(9);
//        root.right.right.right.right = new Node(10);

        Node<Integer> root = new Node(5);
        root.left = new Node(3);
        root.right = new Node(8);
        root.left.left = new Node(2);
        root.left.left.left = new Node(1);
        root.left.right = new Node(4);
        root.right.left = new Node(7);
        root.right.left.left = new Node(6);
        root.right.right = new Node(9);
        root.right.right.right = new Node(10);


        System.out.println(isBTaBST(root));
    }

    public static boolean isBTaBST(Node root) {
        if (root == null) {
            return true;
        } else {
            if (root.left != null) {
                if (root.data.compareTo(root.left.data) >= 0) {
                    return isBTaBST(root.left);
                } else {
                    return false;
                }
            }
            if (root.right != null) {
                if (root.data.compareTo(root.left.data) < 0) {
                    return isBTaBST(root.left);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
