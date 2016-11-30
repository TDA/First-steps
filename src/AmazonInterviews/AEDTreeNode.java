package AmazonInterviews;

/**
 * Created by schandramouli on 11/29/16.
 */
public class AEDTreeNode {
    // typical binary tree, nothing special here
    AEDTreeNode left;
    AEDTreeNode right;
    char value;

    public static void main(String[] args) {
        // write the runner/driver here
        String[] bodmasMap = {"+", "-", "*", "/"};
        String example = "7+3*4-2";
        int expectedAnswer = 17;
    }

    public AEDTreeNode() {
        left = null;
        right = null;
        value = '\0';
    }

    public AEDTreeNode(char c) {
        left = null;
        right = null;
        value = c;
    }

    public AEDTreeNode(AEDTreeNode left, AEDTreeNode right, char value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}
