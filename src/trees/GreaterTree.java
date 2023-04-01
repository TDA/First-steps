package trees;

public class GreaterTree {
    int runningSum = 0;
    public BinaryTreeNode convertBST(BinaryTreeNode root) {
        if (root != null) {
            convertBST(root.right);
            runningSum += root.value;
            root.value = runningSum;
            convertBST(root.left);
        }
        return root;
    }

    private int computeTree(BinaryTreeNode root) {
        if (root == null) return 0;
        return computeTree(root.left) + root.value + computeTree(root.right);
    }

    public static void main(String[] args) {
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode(0);
        BinaryTreeNode binaryTreeNode1 = new BinaryTreeNode(1);
        BinaryTreeNode binaryTreeNode2 = new BinaryTreeNode(2);
        BinaryTreeNode binaryTreeNode3 = new BinaryTreeNode(3);
        BinaryTreeNode binaryTreeNode4 = new BinaryTreeNode(4);
        BinaryTreeNode binaryTreeNode5 = new BinaryTreeNode(5);
        BinaryTreeNode binaryTreeNode6 = new BinaryTreeNode(6);
        BinaryTreeNode binaryTreeNode7 = new BinaryTreeNode(7);
        BinaryTreeNode binaryTreeNode8 = new BinaryTreeNode(8);

        binaryTreeNode4.left = binaryTreeNode1;
        binaryTreeNode4.right = binaryTreeNode6;
        binaryTreeNode6.left = binaryTreeNode5;
        binaryTreeNode6.right = binaryTreeNode7;
        binaryTreeNode7.right = binaryTreeNode8;
        binaryTreeNode1.left = binaryTreeNode;
        binaryTreeNode1.right = binaryTreeNode2;
        binaryTreeNode2.right = binaryTreeNode3;
        BinaryTreeNode greaterTree = new GreaterTree().convertBST(binaryTreeNode4);
        System.out.println(greaterTree);

    }
}
