import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by schandramouli on 11/7/15.
 */
public class LinkedListfromBinaryTree {
    public static void main(String[] args) {
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.left.left = new Node("G");
        root.left.left.left = new Node("G1");
        root.left.right = new Node("H");
        root.right.left = new Node("D");
        root.right.right = new Node("E");
        root.right.right.right = new Node("F");
        root.right.right.right.right = new Node("K");

        ArrayList<LinkedList> ListofLists = new ArrayList<>();
        ListofLists = createLLfromBT(root);
        System.out.println(ListofLists);
    }

    public static ArrayList<LinkedList> createLLfromBT(Node<Integer> newNode) {
        ArrayList<LinkedList> ListofLists = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(newNode);
        while(! queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> linkedList = new LinkedList<>();
            while (size > 0) {
                Node<Integer> root = queue.poll();
                linkedList.add(root.data);
                System.out.print(root.data);
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
                size--;
            }
            ListofLists.add(linkedList);
            System.out.println();
        }
        return ListofLists;
    }
}
