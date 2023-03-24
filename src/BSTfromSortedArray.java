import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import common.Node;
/**
 * Created by schandramouli on 11/7/15.
 */
public class BSTfromSortedArray {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        // construct a BST from a sorted array with least possible height
        // least possible height will be when each node has 2 children, i guess
        // first we need to find the root node, which will generally be
        // the center elt in the array
        Node newNode = generateRoots(array, 0, array.length - 1);
        //System.out.println(newNode.data);
        Node.levelOrderTraversal(newNode);
        // then we can recursively split the array into smaller parts
        // and return the root element at every level

    }

    public static Node generateRoots(int[] array, int start, int end) {
        // base case
        if (end < start) {
            return null;
        } else {
            // split array into left and right, and store those as nodes
            int mid = (start + end) / 2;
            //System.out.println("mid is  " + mid);
            //System.out.println("elt val " + array[mid]);
            int data = array[mid];
            Node root = new Node(data);
            root.left = generateRoots(array, start, mid - 1);
            root.right = generateRoots(array, mid + 1, end);
            return root;
        }
    }
}
