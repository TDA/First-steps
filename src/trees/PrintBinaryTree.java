package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PrintBinaryTree {
    class Wrapper {
        BinaryTreeNode node;
        int x;
        int y;

        public Wrapper(BinaryTreeNode root, int x, int y) {
            node = root;
            this.x = x;
            this.y = y;
        }
    }

    public int getHeight(BinaryTreeNode root){
        if(root == null)
            return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    public List<List<String>> printTree(BinaryTreeNode root) {
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
                    BinaryTreeNode nodeToAdd = node.node;
                    int row = node.x;
                    int col = node.y;
                    results.get(row).set(col, "" + nodeToAdd.value);
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
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode(0);
        BinaryTreeNode binaryTreeNode1 = new BinaryTreeNode(1);
//        BinaryTreeNode binaryTreeNode2 = new BinaryTreeNode(2);
//        BinaryTreeNode binaryTreeNode3 = new BinaryTreeNode(3);
//        BinaryTreeNode binaryTreeNode4 = new BinaryTreeNode(4);
//        BinaryTreeNode binaryTreeNode5 = new BinaryTreeNode(5);
//        BinaryTreeNode binaryTreeNode6 = new BinaryTreeNode(6);
//        BinaryTreeNode binaryTreeNode7 = new BinaryTreeNode(7);
//        BinaryTreeNode binaryTreeNode8 = new BinaryTreeNode(8);

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
