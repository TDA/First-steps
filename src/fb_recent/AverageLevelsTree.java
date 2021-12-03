package fb_recent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AverageLevelsTree {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> results = new ArrayList<>();
        if (root == null) return results;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        // level order + average
        while (!queue.isEmpty()) {
            int popSize = queue.size();
            int sumAtLevel = 0;
            for (int i = 0; i < popSize; i++) {
                TreeNode polledNode = queue.poll();
                if (polledNode.left != null) {
                    queue.add(polledNode.left);
                }
                if (polledNode.right != null) {
                    queue.add(polledNode.right);
                }
                sumAtLevel += polledNode.val;
            }
            double averageAtLevel = (double) sumAtLevel / popSize;
            results.add(averageAtLevel);
        }

        return results;
    }

}
