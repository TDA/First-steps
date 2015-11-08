import java.util.*;

/**
 * Created by schandramouli on 9/29/15.
 */
public class LevelOrderTraversal {
    public static void main(String[] args) {
        //ArrayList<Node> queue = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        Queue<Node> queue2 = new ArrayDeque<>();
        Node a = new Node("1");
        Node b = new Node("2");
        Node c = new Node("3");
        Node d = new Node("4");
        Node e = new Node("5");
        Node f = new Node("6");
        Node g = new Node("7");
        Node h = new Node("8");
        Node i = new Node("9");

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.left = f;
        c.right = g;
        d.right = h;
        d.left = h;
        f.left = i;
        f.right = i;
        e.left = h;


        queue.add(a);

        int level = 1;

        while (a.left !=null || a.right !=null) {
            level++;
            if (a.left != null) {
                a = a.left;
            }
            else if (a.right != null) {
                a = a.right;
            }
        }
        level = 1;
        //System.out.println(level);
        int iterations = 0;

        while(! queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node root = queue.poll();
                System.out.print(" " + root.data);
                if (root.left != null) {
                    //System.out.print(root.left.data);
                    queue.add(root.left);
                }
                if (root.right != null) {
                    //System.out.print(root.right.data);
                    queue.add(root.right);
                }
                size--;
                iterations++;
            }
            System.out.println();
        }

    System.out.println(iterations);
    }
}
