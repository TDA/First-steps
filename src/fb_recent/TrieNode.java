package fb_recent;

import java.util.Arrays;

public class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word;

    public void addChild(Character character, TrieNode newNode) {
        children[character - 'a'] = newNode;
    }

    public TrieNode getChild(Character character) {
        return children[character - 'a'];
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}