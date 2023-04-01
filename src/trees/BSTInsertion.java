package trees;

public class BSTInsertion {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode node = root;
        while (node != null) {
            if (node.val > val) {
                // insert on left side
                if (node.left == null) {
                    node.left = new TreeNode(val);
                }
                node = node.left;
            } else if (node.val < val) {
                // insert on right side
                if (node.right == null) {
                    node.right = new TreeNode(val);
                }
                node = node.right;
            } else {
                // do nothing -- guaranteed to not be duplicate
                break;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[]{4,2,7,1,3});
        TreeNode modRoot = new BSTInsertion().insertIntoBST(root, 5);
        System.out.println(modRoot);
    }
}
