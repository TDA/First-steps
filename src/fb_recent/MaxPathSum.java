package fb_recent;

import trees.BinaryTreeNode;

public class MaxPathSum {
    public static void main(String[] args){
        MaxPathSum maxPathSum = new MaxPathSum();

        BinaryTreeNode bmx = new BinaryTreeNode(-10);
        BinaryTreeNode bmx2 = new BinaryTreeNode(9);
        BinaryTreeNode bmx3 = new BinaryTreeNode(20);
        BinaryTreeNode bmx4 = new BinaryTreeNode(15);
        BinaryTreeNode bmx5 = new BinaryTreeNode(7);
        bmx.left = bmx2;
        bmx.right = bmx3;
        bmx3.left = bmx4;
        bmx3.right = bmx5;

        System.out.println(maxPathSum.maxPathSum(bmx));
    }

    int maxValue = Integer.MIN_VALUE;
    private int maxPathSum(BinaryTreeNode root) {
        maxPathSumHelper(root);
        return maxValue;
    }

    private int maxPathSumHelper(BinaryTreeNode root) {
        if (root == null) return 0;

        int leftMax = maxPathSumHelper(root.left);
        int rightMax = maxPathSumHelper(root.right);
        int maxRightLeft = Math.max(leftMax, rightMax);

        int localMaxLeftOrRightOnly = root.value + maxRightLeft;
        int localMaxOneNode = Math.max(root.value, localMaxLeftOrRightOnly);

        int localMaxAllThreeNodes = root.value + leftMax + rightMax;
        int localMax = Math.max(localMaxAllThreeNodes, localMaxOneNode);
        maxValue = Math.max(maxValue, localMax);
        return localMaxOneNode;
    }
}
