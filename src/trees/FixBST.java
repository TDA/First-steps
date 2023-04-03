package trees;

import java.util.ArrayList;
import java.util.List;

public class FixBST {
    TreeNode previousNodeInOrder;
    TreeNode firstBadNode;
    TreeNode secondBadNode;
    public void recoverTree(TreeNode root) {
        walkInorder(root);
        if (firstBadNode == null) return;
        int temp = firstBadNode.val;
        firstBadNode.val = secondBadNode.val;
        secondBadNode.val = temp;
    }

    public FixBST() {
        firstBadNode = null;
        secondBadNode = null;
        previousNodeInOrder = null;
    }

    private void walkInorder(TreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return;
        walkInorder(binaryTreeNode.left);
        if (previousNodeInOrder != null && previousNodeInOrder.val > binaryTreeNode.val) {
            if (firstBadNode == null) {
                firstBadNode = previousNodeInOrder;
            }
            secondBadNode = binaryTreeNode;
        }
        previousNodeInOrder = binaryTreeNode;
        walkInorder(binaryTreeNode.right);
    }

    public static void main(String[] args) {
        FixBST fixBST = new FixBST();
        TreeNode root = new ArrayToBinaryTree().convert(new int[] {1,3,-1,-1,2});
        System.out.println(root);
        fixBST.recoverTree(root);
        System.out.println(root);
        FixBST fixBST2 = new FixBST();
        TreeNode root2 = new ArrayToBinaryTree().convert(new int[] {3,1,4,-1,-1,2, -1});
        System.out.println(root2);
        fixBST2.recoverTree(root2);
        System.out.println(root2);

        FixBST fixBST3 = new FixBST();
        TreeNode root3 = new ArrayToBinaryTree().convert(new int[] {3,1,-1,-1,2});
        System.out.println(root3);
        fixBST3.recoverTree(root3);
        System.out.println(root3);
    }
}
