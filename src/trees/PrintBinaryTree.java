package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PrintBinaryTree {
    class Wrapper {
        TreeNode node;
        int x;
        int y;

        public Wrapper(TreeNode root, int x, int y) {
            node = root;
            this.x = x;
            this.y = y;
        }
    }

    public int getHeight(TreeNode root){
        if(root == null)
            return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    public List<List<String>> printTree(TreeNode root) {
        if (root == null) return List.of();
        int height = getHeight(root);

        List<List<String>> results = new ArrayList<>(height);
        int cols = (int)Math.pow(2, (height)) - 1;
        for (int i = 0; i < height; i++) {
            List<String> resultsAtLevelX = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                resultsAtLevelX.add("");
            }
            results.add(resultsAtLevelX);
        }

        Queue<Wrapper> wrapperQueue = new ArrayDeque<>();
        wrapperQueue.add(new Wrapper(root, 0, (cols - 1) / 2));
        while (!wrapperQueue.isEmpty()) {
            int sizeToPop = wrapperQueue.size();
            for (int i = 0; i < sizeToPop; i++) {
                Wrapper node = wrapperQueue.poll();
                if (node != null) {
                    TreeNode nodeToAdd = node.node;
                    int row = node.x;
                    int col = node.y;
                    results.get(row).set(col, "" + nodeToAdd.val);
                    int leftChildY = col - (int)Math.pow(2, (height-row-2));
                    int rightChildY = col + (int)Math.pow(2, (height-row-2));
                    if (nodeToAdd.left != null) wrapperQueue.add(new Wrapper(nodeToAdd.left, row + 1 , leftChildY));
                    if (nodeToAdd.right != null) wrapperQueue.add(new Wrapper(nodeToAdd.right, row + 1 , rightChildY));
                }
            }
        }

        return results;
    }

    public static void main(String[] args) {
        TreeNode binaryTreeNode = new TreeNode(0);
        TreeNode binaryTreeNode1 = new TreeNode(1);
//        TreeNode binaryTreeNode2 = new TreeNode(2);
//        TreeNode binaryTreeNode3 = new TreeNode(3);
//        TreeNode binaryTreeNode4 = new TreeNode(4);
//        TreeNode binaryTreeNode5 = new TreeNode(5);
//        TreeNode binaryTreeNode6 = new TreeNode(6);
//        TreeNode binaryTreeNode7 = new TreeNode(7);
//        TreeNode binaryTreeNode8 = new TreeNode(8);

//        binaryTreeNode4.left = binaryTreeNode1;
//        binaryTreeNode4.right = binaryTreeNode6;
//        binaryTreeNode6.left = binaryTreeNode5;
//        binaryTreeNode6.right = binaryTreeNode7;
//        binaryTreeNode7.right = binaryTreeNode8;
        binaryTreeNode1.left = binaryTreeNode;
//        binaryTreeNode1.right = binaryTreeNode2;
//        binaryTreeNode2.right = binaryTreeNode3;
        List<List<String>> x = new PrintBinaryTree().printTree(binaryTreeNode1);
        for (int i = 0; i < x.size(); i++) {
            for (int j = 0; j < x.get(i).size(); j++) {
                System.out.print(x.get(i).get(j) + "\t");
            }
            System.out.println();
        }
        
    }
}
