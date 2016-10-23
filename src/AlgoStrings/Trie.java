package AlgoStrings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public void insertWord(String word, Object o, TrieNode n) {
        int wordLength = word.length();
        //System.out.println("Word is " + word + " and node is " + n);
        if (wordLength == 0) {
            return;
        }
        if (wordLength == 1) {
            // final character, set its endValue
            n.setEndValue(o);
        }
        char c = word.charAt(0);
        if (n.hasChild(c)) {
            // continue traversing its children
            insertWord(word.substring(1), o, n.getChild(c));
        } else {
            // insert the character and move down
            TrieNode child = new TrieNode(c);
            //System.out.println("Inserting " + c);
            n.insertChild(c, child);
            // recursively populate its children.
            insertWord(word.substring(1), o, child);
        }
    }


    public void insertWord(String word, Object o) {
        insertWord(word, o, root);
    }

    public Object prefixMap(String prefix) {
        HashMap<String, Object> prefMap = new HashMap<>();
        return prefixMap(prefix, prefMap, root);
    }

    private Object prefixMap(String prefix, HashMap prefMap, TrieNode n) {
        // we havent hit the end of the string yet
        while (prefix.length() >= 0) {
            if (prefix.length() == 0) {
                // we have hit the end of the string, means we need to get all children out now,
                // and put them as key => value pairs and return that
            }
            // else move to the end of the string
            char c = prefix.charAt(0);
            if (n.hasChild(c)) {
                n = n.getChild(c);
                prefix = prefix.substring(1);
            }
        }
        return null;
    }


    public boolean isWordPresent(String word, TrieNode n) {
        int wordLength = word.length();
        if (wordLength == 0) {
            return true;
        }
        char c = word.charAt(0);
        return (n.hasChild(c)) && isWordPresent(word.substring(1), n.getChild(c));
    }

    public boolean isWordPresent(String word) {
        return isWordPresent(word, root);
    }

    public Object getWordValue(String word, TrieNode n) {
        if (word.length() == 0) {
            return null;
        }
        if (word.length() == 1) {
            return n.getEndValue();
        }
        char c = word.charAt(0);
        return getWordValue(word.substring(1), n.getChild(c));
    }

    public Object getWordValue(String word) {
        return getWordValue(word, root);
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
        String[] Lol = {"a", "b"};
        t.insertWord("tree", Arrays.asList(Lol));
        System.out.println(t);
        t.insertWord("trap", "trap");
        System.out.println(t);
        t.insertWord("trap", "trap");
        System.out.println(t);
        t.insertWord("triangle", "triangle");
        System.out.println(t);
        t.insertWord("trengle", "trengle");
        System.out.println(t);
        t.insertWord("adonis", "adonis");
        System.out.println(t);

        System.out.println(t.isWordPresent("tree"));
        System.out.println(t.isWordPresent("adonit"));

        System.out.println(t.prefixMap("tr"));
    }
}
