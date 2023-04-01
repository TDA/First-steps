package trees;

public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    int count;

    public BinaryTreeNode(int i) {
        value = i;
    }

    @Override
    public String toString() {
        return new TreeTraversals().levelOrder(this);
    }
}
