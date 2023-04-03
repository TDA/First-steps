package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmallestFromLeaf {
    // This can be generalized to be the smallest value path from a leaf to a root.
    // Obvs DFS
    public String smallestFromLeaf(TreeNode root) {
        if (root == null) return "";
        List<String> strings = new ArrayList<>();
        String rootPrefix = "";
        stringBuilderForPath(root, rootPrefix, strings);
        System.out.println(strings);
        Collections.sort(strings);
        return strings.get(0);
    }

    private void stringBuilderForPath(TreeNode node, String nodePrefix, List<String> strings) {
        if (node == null) return;
        nodePrefix = getStr(node.val) + nodePrefix;
        if (node.left == null && node.right == null) {
            // leaf node, we are done
            strings.add(nodePrefix);
            return;
        }
        stringBuilderForPath(node.left, nodePrefix, strings);
        stringBuilderForPath(node.right, nodePrefix, strings);
    }

    private String getStr(int val) {
        char c = (char) ('a' + val);
        return String.valueOf(c);
    }

    boolean compareStrings(String s1, String s2) {
        // compare lexico
        return s1.compareTo(s2) < 0;
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[]{0,1,2,3,4,3,4});
        System.out.println(new SmallestFromLeaf().smallestFromLeaf(root));
    }
}
