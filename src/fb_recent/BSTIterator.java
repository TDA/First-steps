package fb_recent;


import java.util.Stack;

public class BSTIterator {
    Stack<TreeNode> iterator = new Stack<>();

    public BSTIterator(TreeNode root) {
        fillStack(root);
    }

    private void fillStack(TreeNode root) {
        while(root != null){
            iterator.push(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode treeNode = iterator.pop();
        fillStack(treeNode.right);
        return treeNode.val;
    }

    public boolean hasNext() {
        return !iterator.empty();
    }
}
