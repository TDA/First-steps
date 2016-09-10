package AlgoStrings;

import java.util.HashMap;

/**
 * Created by schandramouli on 9/9/16.
 */
public class TrieNode {
    char c;
    HashMap<Character, TrieNode> trieNodeHashMap = new HashMap<>();

    public TrieNode() {
        this.c = '$';
    }

    public TrieNode(char c) {
        this.c = c;
    }

    public char val() {
        return c;
    }

    public boolean isLeaf() {
        return c == '$';
    }
}
