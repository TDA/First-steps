/**
 * Created by schandramouli on 10/8/16.
 */

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal2 {
    public static void main(String args[]){
        LevelOrderTraversal2 binaryTree = new LevelOrderTraversal2();
        Node root = new Node(40);
        Node node1 = new Node(20);
        Node node2 = new Node(60);
        Node node3 = new Node(10);
        Node node4 = new Node(30);
        Node node5 = new Node(50);
        Node node6 = new Node(70);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        binaryTree.levelOrderTraversal(root);
    }

    public void levelOrderTraversal(Node root) {
        if (root == null) {
            System.out.println("tree is empty");
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node popped = queue.poll();
            System.out.print(popped.val+ " ");
            if (popped.left != null) {
                queue.add(popped.left);
            }
            if (popped.right != null) {
                queue.add(popped.right);
            }


        }
    }
    static class Node{
        int val;
        Node left;
        Node right;
        Node(int val){
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
}