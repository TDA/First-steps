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
        TreeNode a = new TreeNode("A", 5, null);
        TreeNode b = new TreeNode("B", 2, null);
        TreeNode c = new TreeNode("C", 1, null);
        TreeNode d = new TreeNode("D", 4, null);
        TreeNode e = new TreeNode("E", 4, null);
        TreeNode f = new TreeNode("F", 3, null);
        TreeNode g = new TreeNode("G", 8, null);
        a.setChildren(new ArrayList<TreeNode>(Arrays.asList(b, c, d)));
        b.setChildren(new ArrayList<TreeNode>(Arrays.asList(e, f)));
        e.setChildren(new ArrayList<TreeNode>(Arrays.asList(g)));

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
        for (TreeNode child : root.getChildren()) {
            System.out.println("I am trying to check " + child);
            sum += calculateSums(child);
            root.setSum(sum);
        }
        return root.getSum();
    }
}

class TreeNode {
    String name;
    int age;
    int sum;
    ArrayList<TreeNode> children = new ArrayList<>();

    public TreeNode(String name, int age, ArrayList<TreeNode> children) {
        this.name = name;
        this.children = children;
        this.age = age;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public int getSum() {
        return sum;
    }

    public int getNumberOfChildren() {
        return children.size();
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
