import java.util.ArrayList;

/**
 * Created by schandramouli on 11/7/15.
 */
public class SumOfPathsinTree {
    public static void main(String[] args) {
        Node<Integer> root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(-3);
        root.left.left.left = new Node(3);
        root.left.right = new Node(6);
        root.right.left = new Node(7);
        root.right.right = new Node(-4);
        root.right.right.right = new Node(9);
        root.right.right.right.right = new Node(-5);
        //Node.levelOrderTraversal(root);
        // find from root to any path
        findSum(root, 0);

        // generalize to any node to downward paths
        Node<Integer> copyRootLeft = root;
        Node<Integer> copyRootRight = root;
        while (copyRootLeft != null) {
            findSum(copyRootLeft.left, 0);
            //findSum(copyRootLeft.right, 0);
            copyRootLeft = copyRootLeft.left;
        }
        while (copyRootRight != null) {
            findSum(copyRootRight.right, 0);
            //findSum(copyRootRight.right, 0);
            copyRootRight = copyRootRight.right;
        }


    }

    public static void printPath(ArrayList<Integer> arrayList) {
        for (Integer integer : arrayList) {
            System.out.print(" --> " + integer);
        }
        System.out.println();
    }
    public static void findSum(Node root, int sum) {
        findSum(root, sum, new ArrayList());
    }

    public static void findSum(Node root, int sum, ArrayList arrayList) {
        if (root == null) {
            return;
        }
        //System.out.println("Node value is " + root.data);
        //System.out.println("Sum is " + sum);
        arrayList.add(root.data);
        if ((int) root.data == sum) {
            // we have found a path which has equaled sum
            // lets print it
            System.out.println("Equal");
            printPath(arrayList);
        }
        findSum(root.left, sum - (int) root.data, (ArrayList) arrayList.clone());
        findSum(root.right, sum - (int) root.data, (ArrayList) arrayList.clone());
    }

    public static boolean isSumEqual(int[] path, int sum) {
        int tempSum = 0;
        for (int i : path) {
            tempSum += i;
        }
        return (tempSum == sum);
    }
}
