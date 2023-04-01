package trees;

import java.util.ArrayDeque;
import java.util.Queue;

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
public class RightPointerTrees {
    public Node connect(Node root) {
        Queue<Node> nodeQueue = new ArrayDeque<>();
        if (root == null) return null;
        nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            int sizeToPop = nodeQueue.size();
            for (int i = 0; i < sizeToPop; i++) {
                Node poppedItem = nodeQueue.poll();
                if (poppedItem != null) {
                    if (i == sizeToPop - 1) {
                        // last item, next item should be null acc to the pbm stmt
                        poppedItem.next = null;
                    } else {
                        poppedItem.next = nodeQueue.peek();
                    }
                    if (poppedItem.left != null) nodeQueue.add(poppedItem.left);
                    if (poppedItem.right != null) nodeQueue.add(poppedItem.right);
                }
            }
        }

        return root;
    }
}
