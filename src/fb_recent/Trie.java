package fb_recent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Trie {
    TrieNode node;

    public Trie() {
        node = new TrieNode();
    }

    public void insert(String s) {
        TrieNode currentNode = this.node;
        for (char c : s.toCharArray()) {
            TrieNode node = currentNode.getChild(c);
            if (node == null) {
                node = new TrieNode();
                currentNode.addChild(c, node);
            }
            currentNode = node;
        }
        currentNode.word = s;
    }

    boolean isWordPresent(String word) {
        TrieNode currentNode = this.node;
        for (char c : word.toCharArray()) {
            if (currentNode.getChild(c) != null) {
                currentNode = currentNode.getChild(c);
            } else {
                return false;
            }
        }
        return word.equals(currentNode.word);
    }

    boolean startsWith(String word) {
        TrieNode currentNode = this.node;
        for (char c : word.toCharArray()) {
            if (currentNode.getChild(c) != null) {
                currentNode = currentNode.getChild(c);
            } else {
                return false;
            }
        }
        return true;
    }

    boolean regexMatch(String word) {
        char[] chars = word.toCharArray();
        TrieNode currentNode = this.node;
        return regexMatch(chars, 0, currentNode);
    }

    boolean regexMatch(char[] chars, int nextChar, TrieNode currentNode) {
        // contains regex, if we see a dot, we can essentially go down any child to check the rest,
        // making this a huge search space, we need to branch and bound this to ensure we dont check every combo
        // few ways:
        // 1. limit recursions to number of total characters, we never have to go beyond X level
        if (nextChar == chars.length) {
            System.out.println("Last item, just need to check if word is present at this level " + (currentNode.word != null));
            return currentNode.word != null;
        }
        char c = chars[nextChar];
        System.out.printf("Checking %s\n", c);
        if (c != '.') {
            if (currentNode.getChild(c) != null) {
                return regexMatch(chars, nextChar + 1, currentNode.getChild(c));
            } else {
                return false;
            }
        } else {
            // for all else, need to evaluate EVERY possible future.
            for (TrieNode node : currentNode.children) {
                System.out.printf("Checking at %s\n", Arrays.toString(currentNode.children));
                if (node != null && regexMatch(chars, nextChar + 1, node)) {
                    return true;
                };
            }
        }
        return false;
    }

    public List<String> suffixes(String prefix) {
        TrieNode currentNode = this.node;
        for (char c : prefix.toCharArray()) {
            if (currentNode.getChild(c) != null) {
                currentNode = currentNode.getChild(c);
            } else {
                return Collections.emptyList();
            }
        }

        return depthFirstTraversal(currentNode);
    }

    private List<String> depthFirstTraversal(TrieNode currentNode) {
        List<String> strings = new ArrayList<>();
        if (currentNode.word != null) strings.add(currentNode.word);
        Arrays.stream(currentNode.children).filter(n -> n != null).forEach(n -> {
            strings.addAll(depthFirstTraversal(n));
        });

        return strings;
    }

    @Override
    public String toString() {
        return "Trie{" +
                "node=" + node +
                '}';
    }

    public static void main(String[] args){
        Trie trie = new Trie();
        trie.insert("sai");
        trie.insert("sat");
        trie.insert("sam");
        trie.insert("samet");
        trie.insert("same");
        trie.insert("sameem");
        trie.insert("stream");
        trie.insert("strong");
        trie.insert("strome");
        trie.insert("such");
        trie.insert("so");
        trie.insert("super");
        trie.insert("tom");
        trie.insert("tee");
        trie.insert("tone");
        trie.insert("tusk");
        System.out.println(trie);
        System.out.println(trie.suffixes("str"));
        System.out.println(trie.suffixes("st"));
        System.out.println(trie.suffixes("su"));
        System.out.println(trie.suffixes("s"));
        System.out.println(trie.suffixes("sam"));
        System.out.println(trie.isWordPresent("same"));
        System.out.println(trie.isWordPresent("samet"));
        System.out.println(trie.isWordPresent("sameth"));
        System.out.println(trie.startsWith("same"));
        System.out.println(trie.startsWith("samet"));
        System.out.println(trie.startsWith("samp"));
        System.out.println("s.r...  " + trie.regexMatch("s.r..."));
        System.out.println("...  " + trie.regexMatch("..."));
        System.out.println("t.f  " + trie.regexMatch("t.f"));


        trie.insert("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        System.out.println(trie.regexMatch("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"));
    }
}
