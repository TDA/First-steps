package trees;

public class MaximumBinaryTree {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        int startIndex = 0, endIndex = nums.length;
        return constructMaximumBinaryTreeHelper(nums, startIndex, endIndex - 1);
    }

    private TreeNode constructMaximumBinaryTreeHelper(int[] nums, int startIndex, int endIndex) {
        if (startIndex > endIndex || nums.length == startIndex) return null;
        if (startIndex == endIndex) return new TreeNode(nums[startIndex]);
        // find max
        int max = -1;
        int maxIndex = -1;
        for (int i = startIndex; i <= endIndex; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }
        // partition and recurse
        TreeNode subroot = new TreeNode(nums[maxIndex]);
        subroot.left = constructMaximumBinaryTreeHelper(nums, startIndex, maxIndex - 1);
        subroot.right = constructMaximumBinaryTreeHelper(nums, maxIndex + 1, endIndex);
        return subroot;
    }

    public static void main(String[] args) {
        int[] ints = {3,2,1,6,0,5};
        System.out.println(new MaximumBinaryTree().constructMaximumBinaryTree(ints));
    }
}
