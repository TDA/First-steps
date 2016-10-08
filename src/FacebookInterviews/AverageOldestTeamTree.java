package FacebookInterviews;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by schandramouli on 10/7/16.
 */
public class AverageOldestTeamTree {
    public static void main(String[] args) {
        // problem is that u are given an org chart, with age (in company time)
        // of each person in the org, and asked to find the sub-org which has the
        // highest average age (aver-age, anyone?). Enough to print that nodes name.
        TreeNode a = new TreeNode("A", 5);
        TreeNode b = new TreeNode("B", 2);
        TreeNode c = new TreeNode("C", 1);
        TreeNode d = new TreeNode("D", 4);
        TreeNode e = new TreeNode("E", 4);
        TreeNode f = new TreeNode("F", 3);
        TreeNode g = new TreeNode("G", 8);
        TreeNode h = new TreeNode("H", 2);
        a.setChildren(new ArrayList<TreeNode>(Arrays.asList(b, c, d)));
        b.setChildren(new ArrayList<TreeNode>(Arrays.asList(e, f)));
        e.setChildren(new ArrayList<TreeNode>(Arrays.asList(g)));
        d.setChildren(new ArrayList<TreeNode>(Arrays.asList(h)));
        f.setChildren(new ArrayList<TreeNode>(Arrays.asList(h)));

        calculateAverages(a);
        printTree(a);
    }

    private static void printTree(TreeNode t) {
        if(t != null) {
            System.out.println("---> " + t);
        }
        else {
            return;
        }
        if(t.getChildren() != null) {
            for (TreeNode s : t.children) {
                System.out.print("\t");
                printTree(s);
            }
        }
    }

    private static void calculateAverages(TreeNode root) {
        calculateSums(root);
    }

    private static int calculateSums(TreeNode root) {
        if (root == null) {
            return 0;
        }
        System.out.println(root);
        int sum = 0;
        System.out.println("I have these children " + root.getChildren());
        for (TreeNode child : root.getChildren()) {
            System.out.println("I am trying to check " + child);
            sum += calculateSums(child) + child.age;
            // really clever here, might never again think of doing it this way
            // propagate the childs value to the top, since this is essentially
            // dfs, itll work. if bfs, this might not.
            root.totalNumOfChildren += child.getNumberOfChildren();
            root.setSum(sum);
        }
        return root.getSum();
    }
}

class TreeNode {
    String name;
    int age;
    int sum;
    int totalNumOfChildren;
    ArrayList<TreeNode> children = new ArrayList<>();

    public TreeNode(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public TreeNode(String name, int age, ArrayList<TreeNode> children) {
        this.name = name;
        this.age = age;
        this.children = children;
        this.totalNumOfChildren = children.size();
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
        // set it initially to the number of added children
        this.totalNumOfChildren = children.size();
    }

    public int getSum() {
        return sum;
    }

    public int getNumberOfChildren() {
        return totalNumOfChildren;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sum=" + sum +
                ", totalChildren=" + totalNumOfChildren +
                '}';
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
