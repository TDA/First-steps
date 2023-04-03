package trees;

import java.util.ArrayDeque;
import java.util.Queue;

public class AddRowToTree {
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (root == null) return null;
        int height = findHeight(root);
        if (height < depth - 1) return root;
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int nextLevel = 2;
        while (!queue.isEmpty() && nextLevel < depth) {
            int sizeToPop = queue.size();
            for (int i = 0; i < sizeToPop; i++) {
                TreeNode poll = queue.poll();
                if (poll != null) {
                    if (poll.left != null) queue.add(poll.left);
                    if (poll.right != null) queue.add(poll.right);
                }
            }
            nextLevel++;
        }
        // At this point, we have all the nodes that we need to add children to
        for (TreeNode node : queue) {
            TreeNode newLeftNode = new TreeNode(val);
            TreeNode newRightNode = new TreeNode(val);
            newLeftNode.left = node.left;
            newRightNode.right = node.right;
            node.left = newLeftNode;
            node.right = newRightNode;
        }

        return root;
    }

    private int findHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(findHeight(root.left), findHeight(root.right));
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[]{4,2,6,3,1,5, -1});
        System.out.println(new AddRowToTree().addOneRow(root, 1, 2));
        TreeNode root2 = new ArrayToBinaryTree().convert(new int[]{4,2,-1,3,1});
        System.out.println(new AddRowToTree().addOneRow(root2, 1, 3));
    }
}
