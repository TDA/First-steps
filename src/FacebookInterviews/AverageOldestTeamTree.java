package FacebookInterviews;

import java.util.ArrayList;

/**
 * Created by schandramouli on 10/7/16.
 */
public class AverageOldestTeamTree {
    public static void main(String[] args) {
        // problem is that u are given an org chart, with age (in company time)
        // of each person in the org, and asked to find the sub-org which has the
        // highest average age (aver-age, anyone?). Enough to print that nodes name.
    }
}

class TreeNode {
    String name;
    int age;
    float average;
    ArrayList<TreeNode> children;

    public TreeNode(String name, int age, ArrayList<TreeNode> children) {
        this.name = name;
        this.children = children;
        this.age = age;
    }
}
