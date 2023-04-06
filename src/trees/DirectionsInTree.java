package trees;

import java.util.HashMap;
import java.util.Map;

public class DirectionsInTree {
    public String getDirections(TreeNode root, int startValue, int destValue) {
        // traverse first node
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        findRouteToNode(root, startValue, sb1);
        findRouteToNode(root, destValue, sb2);
        String rootToStart = sb1.reverse().toString();
        String rootToEnd = sb2.reverse().toString();

        // process to find the lowest common item
        int i = 0;
        while (i < rootToStart.length() && i < rootToEnd.length()) {
            if (rootToStart.charAt(i) == rootToEnd.charAt(i)) {
                i++;
            } else break;
        }
        rootToStart = rootToStart.substring(i);
        rootToEnd = rootToEnd.substring(i);

        return convertUpwards(rootToStart) + rootToEnd;
    }

    private String convertUpwards(String rootToStart) {
        return rootToStart.replaceAll("[LR]", "U");
    }

    private boolean findRouteToNode(TreeNode root, int value, StringBuilder pathBuilder) {
        if (root.val == value) {
            // found node
            return true;
        }

        if (root.left != null && findRouteToNode(root.left, value, pathBuilder)) {
            pathBuilder.append("L");
        } else if (root.right != null && findRouteToNode(root.right, value, pathBuilder)) {
            pathBuilder.append("R");
        }
        return pathBuilder.length() > 0;
    }

    public static void main(String[] args) {
        TreeNode root = new ArrayToBinaryTree().convert(new int[]{5,1,2,3,-1,6,4});
        System.out.println(new DirectionsInTree().getDirections(root, 3, 6));
        TreeNode root2 = new ArrayToBinaryTree().convert(new int[]{1, 2, -1});
        System.out.println(new DirectionsInTree().getDirections(root2, 2, 1));
        TreeNode root3 = new ArrayToBinaryTree().convert(new int[]{2, 1, -1});
        System.out.println(new DirectionsInTree().getDirections(root3, 2, 1));
        TreeNode root4 = new ArrayToBinaryTree().convert(new int[]{2, -1, 1});
        System.out.println(new DirectionsInTree().getDirections(root4, 2, 1));
    }
}
