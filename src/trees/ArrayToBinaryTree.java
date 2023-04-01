package trees;

import java.util.Arrays;

public class ArrayToBinaryTree {
    public TreeNode convert(int[] array) {
        TreeNode[] nodes = new TreeNode[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != -1) {
                nodes[i] = new TreeNode(array[i]);
            } else {
                nodes[i] = null;
            }
        }

        int totalNodes = nodes.length;

        for (int i = 0; i < totalNodes / 2; i++) {
            TreeNode node = nodes[i];
            if (node == null) continue;
            node.left = nodes[i * 2 + 1];
            node.right = nodes[i * 2 + 2];
        }
        return nodes[0];
    }

    public static void main(String[] args) {
        System.out.println(new ArrayToBinaryTree().convert(new int[]{3, 1, 4, 3, -1, 1, 5}));
    }
}
