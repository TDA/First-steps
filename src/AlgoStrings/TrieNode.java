package AlgoStrings;

import java.util.HashMap;

/**
 * Created by schandramouli on 9/9/16.
 */
public class TrieNode {
    char c;
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

    @Override
    public String toString() {
        String s = "\n";
        s += "TrieNode{";
        s += "c=" + c;
        HashMap<Character, TrieNode> children = this.getChildren();
        for (TrieNode child : children.values()) {
            s += ", children=" + child.toString();
        }
        s += "}";
        return s;
    }
}
