package trees;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Codec {

    public static final String DELIMITER = " - ";
    public static final String EMPTY_NODE = "XX";

    // Encodes a tree to a single string.
    public String serialize(BinaryTreeNode root) {
        return preOrder(root);
    }

    private String preOrder(BinaryTreeNode root) {
        if (root == null) return EMPTY_NODE;
        return root.value + DELIMITER + preOrder(root.left) + DELIMITER + preOrder(root.right);
    }

    // Decodes your encoded data to tree.
    public BinaryTreeNode deserialize(String data) {
        // convert string to nodeValueStrings
        String[] nodeValueStrings = data.split(DELIMITER);
        Queue<String> nodeQueue = new ArrayDeque<>(Arrays.asList(nodeValueStrings));
        return convert(nodeQueue);
    }

    private BinaryTreeNode convert(Queue<String> stringQueue) {
        String nodeValueString = stringQueue.poll();
        if (EMPTY_NODE.equals(nodeValueString)) {
            return null;
        }
        BinaryTreeNode root = getBinaryTreeNode(nodeValueString);
        root.left = convert(stringQueue);
        root.right = convert(stringQueue);
        return root;
    }

    private BinaryTreeNode getBinaryTreeNode(String nodeValueString) {
        int parseInt = Integer.parseInt(nodeValueString);
        return new BinaryTreeNode(parseInt);
    }

    public static void main(String[] args){
        Codec codec = new Codec();
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode(1);
        BinaryTreeNode binaryTreeNode2 = new BinaryTreeNode(2);
        BinaryTreeNode binaryTreeNode3 = new BinaryTreeNode(3);
        BinaryTreeNode binaryTreeNode4 = new BinaryTreeNode(4);
        BinaryTreeNode binaryTreeNode5 = new BinaryTreeNode(5);
        BinaryTreeNode binaryTreeNode6 = new BinaryTreeNode(6);
        BinaryTreeNode binaryTreeNode7 = new BinaryTreeNode(7);
        BinaryTreeNode binaryTreeNode8 = new BinaryTreeNode(8);
        BinaryTreeNode binaryTreeNode9 = new BinaryTreeNode(9);
        BinaryTreeNode binaryTreeNode10 = new BinaryTreeNode(10);
        BinaryTreeNode binaryTreeNode11 = new BinaryTreeNode(11);
        binaryTreeNode.left = binaryTreeNode2;
        binaryTreeNode.right = binaryTreeNode3;
        binaryTreeNode2.left = binaryTreeNode4;
        binaryTreeNode2.right = binaryTreeNode5;
        binaryTreeNode3.left = binaryTreeNode6;
        binaryTreeNode3.right = binaryTreeNode7;
        binaryTreeNode4.left = binaryTreeNode8;
        binaryTreeNode7.right = binaryTreeNode9;
        binaryTreeNode9.right = binaryTreeNode10;
        binaryTreeNode10.right = binaryTreeNode11;

        TreeTraversals treeTraversals = new TreeTraversals();
        String serializedTree = codec.serialize(binaryTreeNode);
        System.out.println(treeTraversals.levelOrder(binaryTreeNode));
        System.out.println(serializedTree);
        BinaryTreeNode deserializedTree = codec.deserialize(serializedTree);
        System.out.println(deserializedTree);
        System.out.println(treeTraversals.levelOrder(deserializedTree));
    }
}

