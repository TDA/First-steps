package trees;

import java.util.Arrays;

public class SortedArrayToBinarySearchTree {
    public static void main(String[] args){
        SortedArrayToBinarySearchTree sortedArrayToBinarySearchTree = new SortedArrayToBinarySearchTree();
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(sortedArrayToBinarySearchTree.convert(array));
    }

    private BinaryTreeNode convert(int[] array) {
        if (array.length == 0) return null;
        if (array.length == 1) {
            return new BinaryTreeNode(array[0]);
        }
        // pick the pivot, pass the rest to left and right
        int midpoint = array.length / 2;
        BinaryTreeNode pivot = new BinaryTreeNode(array[midpoint]);
        pivot.left = convert(Arrays.copyOfRange(array, 0, midpoint));
        pivot.right = convert(Arrays.copyOfRange(array, midpoint + 1, array.length));
        return pivot;
    }
}
