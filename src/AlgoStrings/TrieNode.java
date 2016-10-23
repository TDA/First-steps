package AlgoStrings;

import java.util.HashMap;

/**
 * Created by schandramouli on 9/9/16.
 */
public class TrieNode {
    char c;
    Object endValue;
    HashMap<Character, TrieNode> children = new HashMap<>();

    public TrieNode() {
        this.c = '$';
    }

    public TrieNode(char c) {
        this.c = c;
    }

    public char val() {
        return c;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return this.getChildren().size() == 0;
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    public TrieNode getChild(char c) {
        return children.get(c);
    }

    public void insertChild(char c, TrieNode n) {
        children.put(c, n);
    }

    public Object getEndValue() {
        return endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }

    public String printTrieNode(int offset) {
        String s = "\n";
        for (int i = 0; i < offset; i++) {
            s += "\t";
        }
        s += "{";
        s += c;
        HashMap<Character, TrieNode> children = this.getChildren();
        for (TrieNode child : children.values()) {
            s += " =>" + child.printTrieNode(offset + 1);
        }
        s += "}";
        return s;
    }
}
