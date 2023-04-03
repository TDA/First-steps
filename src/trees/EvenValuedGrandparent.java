package trees;

import java.util.*;

public class EvenValuedGrandparent {
    int sum = 0;
    public int sumEvenGrandparent(TreeNode root) {
        summer(root, -1);
        return sum;
    }

    void summer(TreeNode node, int parentValue) {
        if (node == null) return;
        if (parentValue % 2 == 0) {
            if (node.left != null)
                sum += node.left.val;
            if (node.right != null)
                sum += node.right.val;
        }
        summer(node.left, node.val);
        summer(node.right, node.val);
    }


    public int sumEvenGrandparentLevelOrder(TreeNode root) {
        // misread the qn to be sum all the grandchildren when a level is even.. its only under that particular grandparent, so DFS not BFS
        if (root == null) return 0;
        int sum = 0;
        int currentLevel = 1;
        Set<Integer> grandparentLevels = new HashSet<>();
        Queue<TreeNode> levelOrderQueue = new ArrayDeque<>();
        levelOrderQueue.add(root);
        while (!levelOrderQueue.isEmpty()) {
            int popSize = levelOrderQueue.size();
            for (int i = 0; i < popSize; i++) {
                TreeNode treeNode = levelOrderQueue.poll();
                if (treeNode != null) {
                    if (treeNode.val % 2 == 0) grandparentLevels.add(currentLevel);
                    if (grandparentLevels.contains(currentLevel - 2)) {
                        sum += treeNode.val;
                    }
                    if (treeNode.left != null) levelOrderQueue.add(treeNode.left);
                    if (treeNode.right != null) levelOrderQueue.add(treeNode.right);
                }
            }
            grandparentLevels.remove(currentLevel - 2);
            currentLevel++;
        }

        return sum;
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[] {6,7,8,2,7,1,3,9,-1,1,4,-1,-1,-1,5});
        System.out.println(new EvenValuedGrandparent().sumEvenGrandparent(root));
    }
}
