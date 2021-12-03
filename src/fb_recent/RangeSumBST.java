package fb_recent;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" + "val=" + val + ", left=" + left + ", right=" + right;
    }
}
public class RangeSumBST {
    public int rangeSumBST(TreeNode root, int low, int high) {
        return rangeHelper(root, low, high);
    }

    private int rangeHelper(TreeNode root, int low, int high) {
        if (root == null) return 0;
        if (root.val >= low && root.val <= high)
            return root.val + rangeHelper(root.right, low, high) + rangeHelper(root.left, low, high);
        if (root.val < low) {
            return rangeHelper(root.right, low, high);
        }
        return rangeHelper(root.left, low, high);
    }

    public static void main(String[] args){
        RangeSumBST rangeSumBst = new RangeSumBST();
        TreeNode root = new TreeNode(10);
        TreeNode node5 = new TreeNode(5);
        TreeNode node15 = new TreeNode(15);
        TreeNode node3 = new TreeNode(3);
        TreeNode node7 = new TreeNode(7);
        TreeNode node18 = new TreeNode(18);
        root.left = node5;
        root.right = node15;
        node5.left = node3;
        node5.right = node7;
        node15.right = node18;
        System.out.println(rangeSumBst.rangeSumBST(root, 7, 15));
        node3.left = new TreeNode(1);
        node7.left = new TreeNode(6);
        System.out.println(rangeSumBst.rangeSumBST(root, 6, 10));
    }
}
