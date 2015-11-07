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
        Node root = new Node(getRootElement(array));
        // then we can recursively split the array into smaller parts
        // and return the root element at every level



    }

    public static int getRootElement(int[] array) {
        // simplified and removed more
        return array[array.length / 2];
    }
}
