package fb_recent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//==Question==
//        Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
//
//        ===Example===
//        Binary Tree:
//
//        1            <---
//        /   \
//        2     3         <---
//        \
//        5              <---
//
//
//        Answer: [1, 3, 5]
//
//
//
//        1            <---
//        /   \
//        2     3         <---
//        \   /
//        5   4          <---
//
//        Answer: [1, 3, 4]
//
//
//        All left children
//        1
//
//        2
//
//        3
//
//        4
//
//        Zigzag test-case
//        1            <---
//        /  \
//        2    3        <---
//        \
//        5
//        \
//        6

class RightView {

    List<Integer> getRightView(TreeNode root) {
        List<Integer> resultsList = new ArrayList<>();
        if (root == null) return resultsList;

        Queue<TreeNode> levelOrderQueue = new ArrayDeque<>();
        levelOrderQueue.add(root); // 1

        while (!levelOrderQueue.isEmpty()) { // 2, 3
            int levelSize = levelOrderQueue.size(); //
            for (int i = 0; i < levelSize; i++) { // one times
                TreeNode node = levelOrderQueue.poll(); // 5

                if (node.left != null) levelOrderQueue.add(node.left); //
                if (node.right != null) levelOrderQueue.add(node.right); //

                if (i == levelSize - 1) { //0 == 0
                    resultsList.add(node.val);        // 1, 3, 5
                }
            }
        }

        return resultsList;

    }
}
