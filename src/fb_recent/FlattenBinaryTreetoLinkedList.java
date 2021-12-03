package fb_recent;

import java.util.ArrayList;
import java.util.List;

public class FlattenBinaryTreetoLinkedList {
    public void flatten(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        helper(root, nodes);
        System.out.println(nodes);
        for (int i = 0; i < nodes.size() - 1; i++) {
            TreeNode treeNode = nodes.get(i);
            treeNode.left = null;
            treeNode.right = nodes.get(i + 1);
        }
        TreeNode treeNode = nodes.get(nodes.size() - 1);
        treeNode.left = null;
        treeNode.right = null;
        System.out.println(nodes);
    }

    private void helper(TreeNode root, List<TreeNode> nodes) {
        // dfs pre-order
        nodes.add(new TreeNode(root.val));
        if (root.left != null) helper(root.left, nodes);
        if (root.right != null) helper(root.right, nodes);
    }

    public static void main(String[] args){
        FlattenBinaryTreetoLinkedList flattenBinaryTreetoLinkedList = new FlattenBinaryTreetoLinkedList();
        TreeNode bstNode6 = new TreeNode(6);
        TreeNode bstNode3 = new TreeNode(3);
        TreeNode bstNode2 = new TreeNode(2);
        TreeNode bstNode1 = new TreeNode(1);
        TreeNode bstNode5 = new TreeNode(5);
        TreeNode bstNode4 = new TreeNode(4);

        bstNode1.left = bstNode2;
        bstNode1.right = bstNode5;
        bstNode2.left = bstNode3;
        bstNode2.right = bstNode4;
        bstNode5.right = bstNode6;

        flattenBinaryTreetoLinkedList.flatten(bstNode1);
    }
}
