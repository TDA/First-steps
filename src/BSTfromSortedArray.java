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


    }

    public static int findRootElementIndex(int[] array) {
        if (array.length % 2 == 0) {
            // even array, return the mid element
            return array.length / 2;
        } else {
            return (array.length / 2) + 1;
        }
    }

    public static int getRootElement(int[] array) {
        int rootIndex = findRootElementIndex(array);
        return array[rootIndex];
    }
}
