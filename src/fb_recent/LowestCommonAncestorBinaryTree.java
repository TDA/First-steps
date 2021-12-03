package fb_recent;

public class LowestCommonAncestorBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int x) { val = x; }

        @Override
        public String toString() {
            return "TreeNode{" + "val=" + val + ", left=" + left + ", right=" + right;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return dfs(root, p, q);
    }

    private TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == q || root == p)
            return root;

        TreeNode leftNode = dfs(root.left, p, q);
        TreeNode rightNode = dfs(root.right, p, q);
        if (leftNode == null) {
            return rightNode;
        } else if (rightNode == null) {
            return leftNode;
        } else {
            return root;
        }
    }

    public static void main(String[] args){
        LowestCommonAncestorBinaryTree lowestCommonAncestorBinaryTree = new LowestCommonAncestorBinaryTree();
        TreeNode root = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        root.left = node5;
        TreeNode node1 = new TreeNode(1);
        node1.left = new TreeNode(0);
        node1.right = new TreeNode(8);
        root.right = node1;
        node5.left = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        node5.right = node2;
        node2.left = new TreeNode(7);
        node2.right = new TreeNode(4);


        System.out.println(lowestCommonAncestorBinaryTree.lowestCommonAncestor(root, node5, node1));
        System.out.println(root);
    }
}
