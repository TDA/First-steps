package trees;

import java.util.ArrayDeque;
import java.util.Queue;

public class EvenOddTree {
    public boolean isEvenOddTree(TreeNode root) {
        int levelIndex = 0;
        if (root == null) return false;
        if (root.left == null && root.right == null) return true;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int previousValue = root.val;
        while (!queue.isEmpty()) {
            int popSize = queue.size();
            for (int i = 0; i < popSize; i++) {
                // verify oddeven condition
                TreeNode poll = queue.poll();
                if (poll != null) {
                    // add children
                    if (poll.left != null) queue.add(poll.left);
                    if (poll.right != null) queue.add(poll.right);
                    if (poll.val % 2 == levelIndex % 2) return false;
                    if (i == 0) {
                        // never check for first element ==> also skips root so double duty
                        previousValue = poll.val;
                        continue;
                    }
                    if (levelIndex % 2 == 0) {
                        // even level -- check odds increasing order
                        if (poll.val <= previousValue) return false;
                    } else {
                        // odd level -- check evens decreasing order
                        if (poll.val >= previousValue) return false;
                    }
                    previousValue = poll.val;
                }
            }
            levelIndex++;
        }
        return true;
    }
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(11);
        TreeNode treeNode1 = new TreeNode(18);
        TreeNode treeNode2 = new TreeNode(14);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(7);
        TreeNode treeNode5 = new TreeNode(18);
        TreeNode treeNode6 = new TreeNode(6);
//        TreeNode treeNode7 = new TreeNode(8);
//        TreeNode treeNode8 = new TreeNode(6);
//        TreeNode treeNode9 = new TreeNode(2);

        treeNode.left = treeNode1;
        treeNode.right = treeNode2;
        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;

//        treeNode2.right = treeNode5;
//        treeNode3.left = treeNode6;
//        treeNode3.right = treeNode7;
//        treeNode4.left = treeNode8;
//        treeNode5.right = treeNode9;


        System.out.println(new EvenOddTree().isEvenOddTree(treeNode));

    }
}
