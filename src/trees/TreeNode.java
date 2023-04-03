package trees;

import java.util.List;

public class TreeNode {
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
        StringBuilder sb = new StringBuilder();
        List<List<String>> x = new PrintBinaryTree().printTree(this);
        for (List<String> strings : x) {
            for (String string : strings) {
                sb.append(string).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
