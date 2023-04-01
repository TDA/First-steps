package trees;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayToTree {
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(preorder, inorder, 0, 0, inorder.length);
    }

    public TreeNode helper(int[] preorder, int[] inorder, int preStartIndex, int inStartIndex, int inEndIndex) {
        if (preStartIndex >= preorder.length || inStartIndex > inEndIndex) return null;
        TreeNode root = new TreeNode(preorder[preStartIndex]);
        int index = findIndex(inorder, preorder[preStartIndex]);
        root.left = helper(preorder, inorder, preStartIndex + 1, inStartIndex, index - 1);
        // the start for right nodes is weird af, is preStartIndex + (index - inStartIndex) + 1 === index + 1 ??
        root.right = helper(preorder, inorder, preStartIndex + (index - inStartIndex) + 1, index + 1, inEndIndex);
        return root;
    }

    Map<Integer, Integer> lookup = new HashMap<>();
    private int findIndex(int[] inorder, int i) {
        if (lookup.getOrDefault(i, -1) != -1) {
            return lookup.get(i);
        }
        for (int j = 0; j < inorder.length; j++) {
            if (inorder[j] == i) {
                lookup.put(i, j);
                return j;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        int[] preorder = {3,9,20,15,7}, inorder = {9,3,15,20,7};
        ArrayToTree arrayToTree = new ArrayToTree();
        System.out.println(arrayToTree.buildTree(preorder, inorder));
    }
}
