package trees;

import java.util.*;

public class TreeTraversals {
    public static void main(String[] args){
        TreeTraversals treeTraversals = new TreeTraversals();
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode(1);
        BinaryTreeNode binaryTreeNode2 = new BinaryTreeNode(2);
        BinaryTreeNode binaryTreeNode3 = new BinaryTreeNode(3);
        BinaryTreeNode binaryTreeNode4 = new BinaryTreeNode(4);
        BinaryTreeNode binaryTreeNode5 = new BinaryTreeNode(5);
        BinaryTreeNode binaryTreeNode6 = new BinaryTreeNode(6);
        BinaryTreeNode binaryTreeNode7 = new BinaryTreeNode(7);
        BinaryTreeNode binaryTreeNode8 = new BinaryTreeNode(8);
        BinaryTreeNode binaryTreeNode9 = new BinaryTreeNode(9);
        BinaryTreeNode binaryTreeNode10 = new BinaryTreeNode(10);
        BinaryTreeNode binaryTreeNode11 = new BinaryTreeNode(11);
        binaryTreeNode.left = binaryTreeNode2;
        binaryTreeNode.right = binaryTreeNode3;
        binaryTreeNode2.left = binaryTreeNode4;
        binaryTreeNode2.right = binaryTreeNode5;
        binaryTreeNode3.left = binaryTreeNode6;
        binaryTreeNode3.right = binaryTreeNode7;
        binaryTreeNode4.left = binaryTreeNode8;
        binaryTreeNode7.right = binaryTreeNode9;
        binaryTreeNode9.right = binaryTreeNode10;
        binaryTreeNode10.right = binaryTreeNode11;

        System.out.println(treeTraversals.inOrder(binaryTreeNode));
        System.out.println(treeTraversals.preOrder(binaryTreeNode));
        System.out.println(treeTraversals.postOrder(binaryTreeNode));
        System.out.println(treeTraversals.levelOrder(binaryTreeNode));

        System.out.println(treeTraversals.height(binaryTreeNode));
        System.out.println(treeTraversals.isBalancedSuboptimalFromCTCI(binaryTreeNode));
        System.out.println(treeTraversals.isBalancedOptimal(binaryTreeNode));

        BinaryTreeNode bstNode6 = new BinaryTreeNode(6);
        BinaryTreeNode bstNode3 = new BinaryTreeNode(3);
        BinaryTreeNode bstNode2 = new BinaryTreeNode(2);
        BinaryTreeNode bstNode1 = new BinaryTreeNode(1);
        BinaryTreeNode bstNode5 = new BinaryTreeNode(5);
        BinaryTreeNode bstNode4 = new BinaryTreeNode(4);
        BinaryTreeNode bstNode9 = new BinaryTreeNode(9);
        BinaryTreeNode bstNode8 = new BinaryTreeNode(8);
        BinaryTreeNode bstNode7 = new BinaryTreeNode(7);
        BinaryTreeNode bstNode10 = new BinaryTreeNode(10);
        bstNode6.left = bstNode3;
        bstNode3.left = bstNode2;
        bstNode3.right = bstNode5;
        bstNode5.left = bstNode4;
        bstNode2.left = bstNode1;
        bstNode6.right = bstNode9;
        bstNode9.left = bstNode8;
        bstNode9.right = bstNode10;
        bstNode8.left = bstNode7;

        System.out.println(treeTraversals.levelOrder(bstNode6));
        System.out.println(treeTraversals.zigzagLevelOrder(bstNode6));
        System.out.println(treeTraversals.inOrder(bstNode6));
        System.out.println(treeTraversals.validateBST(bstNode6));
        System.out.println(treeTraversals.validateBST(binaryTreeNode));

        System.out.println(treeTraversals.levelOrder(treeTraversals.invertTree(bstNode6)));

        BinaryTreeNode btn1 = new BinaryTreeNode(1);
        BinaryTreeNode btn2 = new BinaryTreeNode(2);
        BinaryTreeNode btn3 = new BinaryTreeNode(3);
        BinaryTreeNode btn4 = new BinaryTreeNode(4);

        btn1.left = btn2;
        btn1.right = btn3;
        btn3.right = btn4;

        System.out.println(treeTraversals.inOrder(btn1));
        System.out.println(treeTraversals.inOrder(treeTraversals.invertTree(btn1)));

        populateSubNodeCount(bstNode6);
        System.out.println(treeTraversals.kthElement(bstNode6, 0));
        System.out.println(treeTraversals.kthElement(bstNode6, 1));
        System.out.println(treeTraversals.kthElement(bstNode6, 2));
        System.out.println(treeTraversals.kthElement(bstNode6, 3));
        System.out.println(treeTraversals.kthElement(bstNode6, 4));
        System.out.println(treeTraversals.kthElement(bstNode6, 5));

        System.out.println(treeTraversals.lowestCommonAncestor(bstNode6, bstNode2, bstNode8).value);
        System.out.println(treeTraversals.lowestCommonAncestor(bstNode6, bstNode9, bstNode10).value);
    }

    public String levelOrder(BinaryTreeNode binaryTreeNode) {
        StringJoiner levelOrderString = new StringJoiner(" - ", "[", "]");
        // use a queue for each node popped and add its children immediately
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(binaryTreeNode);
        while (!queue.isEmpty()) {
            int popSize = queue.size();
            for (int i = 0; i < popSize; i++) {
                BinaryTreeNode treeNode = queue.poll();
                if (treeNode != null) {
                    levelOrderString.add(String.valueOf(treeNode.value));
                    queue.add(treeNode.left);
                    queue.add(treeNode.right);
                } else {
                    levelOrderString.add("X");
                }
            }
            levelOrderString.add("\n");
        }
        return levelOrderString.toString();
    }

    public List<List<Integer>> zigzagLevelOrder(BinaryTreeNode root) {
        Queue<BinaryTreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> bigList = new ArrayList<>();
        if (root == null) return List.of();
        queue.add(root);
        boolean isZig = true;
        while (!queue.isEmpty()) {
            int sizeToPop = queue.size();

            List<Integer> levelList = new ArrayList<>();
            for (int i = 0; i < sizeToPop; i++) {
                BinaryTreeNode node = queue.poll();
                if (node != null) {
                    levelList.add(node.value);
                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
            }
            if (isZig) {
                bigList.add(levelList);
            } else {
                Collections.reverse(levelList);
                bigList.add(levelList);
            }

            isZig = !isZig;
        }
        return bigList;
    }

    public boolean isBalancedSuboptimalFromCTCI(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return true;
        int heightDiff = Math.abs(height(binaryTreeNode.left) - height(binaryTreeNode.right));
        if (heightDiff > 1) return false;
        return isBalancedSuboptimalFromCTCI(binaryTreeNode.left) && isBalancedSuboptimalFromCTCI(binaryTreeNode.right);
    }

    public boolean isBalancedOptimal(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return false;
        return Math.abs(height(binaryTreeNode.left) - height(binaryTreeNode.right)) <= 1;
    }

    public int height(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return 0;
        return 1 + Math.max(height(binaryTreeNode.left), height(binaryTreeNode.right));
    }

    private static void populateSubNodeCount(BinaryTreeNode root) {
        if (root == null) return;
        populateSubNodeCount(root.left);
        populateSubNodeCount(root.right);
        if (root.left == null && root.right == null) root.count = 1;
        root.count = 1;
        if (root.left != null) {
            root.count += root.left.count;
        }
        if (root.right != null) {
            root.count += root.right.count;
        }
    }

    public int kthElement(BinaryTreeNode binaryTreeNode, int k) {
        if (binaryTreeNode == null) return 0;
        int leftSize =( binaryTreeNode.left != null? binaryTreeNode.left.count : 0) ;
        if (leftSize + 1 == k) return binaryTreeNode.value;
        if (leftSize + 1 > k) return kthElement(binaryTreeNode.left, k);
        else return kthElement(binaryTreeNode.right, k - leftSize - 1);

    }

    public String inOrder(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return "";
        return inOrder(binaryTreeNode.left) + " - " + binaryTreeNode.value +  " - " + inOrder(binaryTreeNode.right);
    }

    public String preOrder(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return "";
        return binaryTreeNode.value +  " - " + preOrder(binaryTreeNode.left) + " - " + preOrder(binaryTreeNode.right);
    }

    public String postOrder(BinaryTreeNode binaryTreeNode) {
        if (binaryTreeNode == null) return "";
        return postOrder(binaryTreeNode.left) + " - " + postOrder(binaryTreeNode.right) +  " - " + binaryTreeNode.value;
    }

    public boolean validateBST(BinaryTreeNode binaryTreeNode) {
        // BST needs to be balanced
        boolean isValidHeight = isBalancedOptimal(binaryTreeNode);

        return isValidHeight && isValidSubtree(binaryTreeNode);
    }

    public boolean isValidSubtree(BinaryTreeNode binaryTreeNode) {
        boolean isValid = true;
        if (binaryTreeNode == null) return true;
        if (binaryTreeNode.left != null) {
            isValid = binaryTreeNode.left.value <= binaryTreeNode.value;
        }
        if (binaryTreeNode.right != null) {
            isValid = isValid && binaryTreeNode.right.value > binaryTreeNode.value;
        }

        return isValid && isValidSubtree(binaryTreeNode.left) && isValidSubtree(binaryTreeNode.right);
    }

    private BinaryTreeNode invertTree(BinaryTreeNode root) {
        if (root == null) return null;
        BinaryTreeNode temp = root.right;
        root.right = invertTree(root.left);
        root.left = invertTree(temp);
        return root;
    }

    public BinaryTreeNode lowestCommonAncestor(BinaryTreeNode root, BinaryTreeNode p, BinaryTreeNode q) {
        BinaryTreeNode bigger, smaller;
        if (p.value < q.value) {
            bigger = q;
            smaller = p;
        } else {
            bigger = p;
            smaller = q;
        }
        return lcaHelper(root, smaller, bigger);
    }

    private BinaryTreeNode lcaHelper(BinaryTreeNode root, BinaryTreeNode smaller, BinaryTreeNode bigger) {
        if (root == null) // not found
            return null;
        System.out.println(String.format("At %s, %s and %s", root.value, smaller.value, bigger.value));
        if (bigger.left == smaller) return bigger;
        if (smaller.right == bigger) return smaller;
        if (smaller.value < root.value && bigger.value > root.value) {
            // different sides, this is the ancestor
            return root;
        } else if (smaller.value < root.value && bigger.value < root.value) {
            // both on left, traverse left
            return lowestCommonAncestor(root.left, smaller, bigger);
        } else {
            // both on right, traverse right
            return lowestCommonAncestor(root.right, smaller, bigger);
        }
    }
}
