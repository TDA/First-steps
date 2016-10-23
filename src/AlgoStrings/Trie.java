package AlgoStrings;

import java.util.*;

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
            n.setEndValue(o);
            return;
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
        String prefixCopy = prefix;
        // while we havent hit the end of the string yet
        while (prefixCopy.length() > 0) {
            // move to the end of the string
            char c = prefixCopy.charAt(0);
            if (n.hasChild(c)) {
                n = n.getChild(c);
                prefixCopy = prefixCopy.substring(1);
            }
        }

        // we have hit the end of the string, means we need to get all children out now,
        // and put them as key => value pairs and return that

        Queue<TrieNode> allChildren = new ArrayDeque<>();
        allChildren.addAll(n.getChildren().values());
        while (! allChildren .isEmpty()) {
            // get one child and see if its got a value
            TrieNode currentChild = allChildren.poll();
            System.out.println(currentChild);
            if (currentChild.getEndValue() != null) {
                String key = prefix + currentChild.val();
                prefMap.put(key, currentChild.getEndValue());
            } else {
                // this is prolly not a leaf node, add its children if present
                // this is like a bfs basically
                if (! currentChild.isLeaf()) {
                    allChildren.addAll(currentChild.getChildren().values());
                }
                // IMP: need to keep track of the letters which dont have a value, bfs would be tough to do this.
            }
        }
        return prefMap;
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
        t.insertWord("triangle", "triangle");
        System.out.println(t);
        t.insertWord("trengle", "trengle");
        System.out.println(t);
        t.insertWord("adonis", "adonis");
        System.out.println(t);

        System.out.println(t.isWordPresent("tree"));
        System.out.println(t.isWordPresent("adonit"));

        System.out.println(t.getWordValue("trap"));
        System.out.println(t.prefixMap("tre"));
    }
}
