package trees;

public class CountGoodNodes {
    public int goodNodes(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int[] goodNodeCount = {0};
        goodNodesHelper(root, root.val, goodNodeCount);
        return goodNodeCount[0];
    }

    public void goodNodesHelper(TreeNode node, int maxValInPath, int[] goodNodeCount) {
        if (node == null) return;
        System.out.println("Checking node " + node.val + " with max value " + maxValInPath);
        if (node.val >= maxValInPath) {
             // good node, update
            maxValInPath = node.val;
            goodNodeCount[0] = goodNodeCount[0] + 1;
            System.out.println("Found good node " + node.val + " with max value " + maxValInPath);
        }
        // bad node, move on
        if (node.left != null) goodNodesHelper(node.left, maxValInPath, goodNodeCount);
        if (node.right != null) goodNodesHelper(node.right, maxValInPath, goodNodeCount);
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[]{3,1,4,3,-1,1,5});
        System.out.println(new CountGoodNodes().goodNodes(root));
    }
}
