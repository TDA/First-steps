package trees;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public class QuadTrees {
    static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {}
        public Node(boolean _isLeaf, boolean _val) {
            if (!_isLeaf) throw new InvalidParameterException("Cannot be non-leaf node without children being set");
            val = _val;
            isLeaf = _isLeaf;
        }

        public Node(boolean _isLeaf,
                    boolean _val,
                    Node _topLeft,
                    Node _topRight,
                    Node _bottomLeft,
                    Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }

        @Override
        public String toString() {
            if (isLeaf) {
                return "val=" + (val ? 1 : 0) + ", isLeaf=true";
            }
            return "{topLeft=" + topLeft + "\t" +
                    "topRight=" + topRight + "\n" +
                    "bottomLeft=" + bottomLeft + "\t" +
                    "bottomRight=" + bottomRight + "}\n";
        }
    }

    public Node intersect(Node quadTree1, Node quadTree2) {
        return intersectGrid(quadTree1, quadTree2);
    }

    private Node intersectGrid(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf) {
            return quadTree1.val ? quadTree1 : quadTree2;
        }
        if (quadTree2.isLeaf) {
            return quadTree2.val ? quadTree2 : quadTree1;
        }

        quadTree1.topLeft = intersectGrid(quadTree1.topLeft, quadTree2.topLeft);
        quadTree1.topRight = intersectGrid(quadTree1.topRight, quadTree2.topRight);
        quadTree1.bottomLeft = intersectGrid(quadTree1.bottomLeft, quadTree2.bottomLeft);
        quadTree1.bottomRight = intersectGrid(quadTree1.bottomRight, quadTree2.bottomRight);

        if (quadTree1.bottomLeft.isLeaf && quadTree1.bottomRight.isLeaf && quadTree1.topLeft.isLeaf && quadTree1.topRight.isLeaf) {
            boolean bottomLeftVal = quadTree1.bottomLeft.val;
            boolean bottomRightVal = quadTree1.bottomRight.val;
            boolean topLeftVal = quadTree1.topLeft.val;
            boolean topRightVal = quadTree1.topRight.val;
            if (bottomLeftVal == bottomRightVal && topLeftVal == topRightVal && bottomRightVal == topRightVal) {
                quadTree1.val = bottomLeftVal;
                quadTree1.isLeaf = true;
                quadTree1.bottomLeft = quadTree1.topLeft = quadTree1.bottomRight = quadTree1.topRight = null;
            }
        }

        return quadTree1;
    }

    public static void main(String[] args) {
        Node quadTree1 = new Node(false, false,
                new Node(true, true),
                new Node(true, false),
                new Node(true, true),
                new Node(true, true)
        );
        System.out.println(quadTree1);
        Node quadTree2 = new Node(false, false,
                new Node(true, true),
                new Node(false, true,  new Node(true, true), new Node(true, false), new Node(true, false), new Node(true, true)),
                new Node(true, true),
                new Node(true, true)
        );
        System.out.println(quadTree2);
        System.out.println(new QuadTrees().intersect(quadTree1, quadTree2));
    }
}
