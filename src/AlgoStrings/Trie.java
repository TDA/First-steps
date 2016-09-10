package AlgoStrings;

import java.util.HashMap;

/**
 * Created by schandramouli on 9/9/16.
 */
public class Trie {
    TrieNode root;
    // this will have the methods to traverse, insert, search etc.
    // It is similar to a linkedlist or binarytree implementation.

    public Trie() {
        this.root = new TrieNode('^');
    }

    public Trie(TrieNode root) {
        this.root = root;
    }

    public void insertWord(String word, TrieNode n) {
        int wordLength = word.length();
        //System.out.println("Word is " + word + " and node is " + n);
        if (wordLength == 0) {
            return;
        }
        char c = word.charAt(0);
        if (n.hasChild(c)) {
            // continue traversing its children
            insertWord(word.substring(1), n.getChild(c));
        } else {
            // insert the character and move down
            TrieNode child = new TrieNode(c);
            //System.out.println("Inserting " + c);
            n.insertChild(c, child);
            // recursively populate its children.
            insertWord(word.substring(1), child);
        }
    }

    public boolean isWordPresent(String word, TrieNode n) {
        int wordLength = word.length();
        if (wordLength == 0) {
            return true;
        }
        char c = word.charAt(0);
        return (n.hasChild(c)) && isWordPresent(word.substring(1), n.getChild(c));
    }

    public void insertWord(String word) {
        insertWord(word, root);
    }
    public boolean isWordPresent(String word) {
        return isWordPresent(word, root);
    }

    @Override
    public String toString() {
        return "Trie{" +
                "root=" + root.printTrieNode(0) +
                '}';
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        System.out.println(t);
        t.insertWord("tree");
        System.out.println(t);
        t.insertWord("trap");
        System.out.println(t);
        t.insertWord("trap");
        System.out.println(t);
        t.insertWord("triangle");
        System.out.println(t);
        t.insertWord("trengle");
        System.out.println(t);
        t.insertWord("adonis");
        System.out.println(t);

        System.out.println(t.isWordPresent("tree"));
        System.out.println(t.isWordPresent("adonit"));
    }
}
