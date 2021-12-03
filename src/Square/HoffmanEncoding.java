package Square;

// Realize a simplified version of huffman encoding, a compression algorithm. If you are interested, you can go to the wiki.
// Brief introduction: In an ordinary string, a char occupies 8 bits. For compression, we want to reduce the bit occupied
// by each char. The idea of this algorithm is that if a character appears more frequently, it should occupy as
// few bits as possible, so that the compression effect is better. If the frequency of a character is very low, it doesn’t matter if there are more bits. Anyway, the frequency is low.
//
//The interviewer is very good and will guide you step by step from simple questions
//1. Give you a string and return letter frequency count
//2. Take the count from the first question to generate a huffman tree. The algorithm starts from the character with the lowest frequency,
// first generates the left sibling of the current node, then uses the left sibling and the current node to generate the parent, and then the
// parent becomes the new root. I saw that the tree generated in the wiki is relatively balanced, but the tree generated according to the method
// described by the interviewer is right-leaning. It may be that the interviewer simplified the generation method.
//
//The generated tree probably grows to (starting from node8, and has been generated to node1):
//    node1 (iteration3)
//   / \
//node2 node3 (iteration 2)
//           / \
//    node4 node5 (iteration 1)
//                  / \
//            node7 node8 (iteration 0)
//
//3. Calculate the encoding of each char according to the generated tree. Simple dfs. Each leaf represents a char, and you can get the corresponding encoding when you go to l‍‌‌‌‍‌‌‌‍‍‌‌‌‌‌‌‌‌‍‍eaf. The encoding method is to go to the left as 1 and go to the right as 0 (or the reverse, I can’t remember it)
//4. Encode the original string and print the compressed binary form
//
//After writing the above four questions, you must write a test case and print it out for verification. The topic only has a text description, functions and nodes are all defined by themselves

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node {
    String character;
    int count;
    Node left;
    Node right;

    public Node(String character, int val) {
        this.character = character;
        this.count = val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "character=" + character +
                ", count=" + count +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}


public class HoffmanEncoding {
    Map<Character, Integer> letterFrequencyCount = new LinkedHashMap<>();
    Map<Character, String> encodedMap = new HashMap<>();
    PriorityQueue<Node> letterQueue = new PriorityQueue<>((a, b) -> a.count - b.count);

    void populateCharacters(String s) {
        for (Character c : s.toCharArray()) {
            letterFrequencyCount.put(c, letterFrequencyCount.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : letterFrequencyCount.entrySet()) {
            Node node = new Node(entry.getKey() + "", entry.getValue());
            letterQueue.add(node);
        }
    }

    Node generateHuffManTree() {
        Node firstNode = letterQueue.poll();
        while (firstNode != null) {
            Node secondNode = letterQueue.poll();
            if (secondNode == null) {
                break;
            }
            Node newRoot = new Node(firstNode.character + secondNode.character + "", firstNode.count + secondNode.count);
            newRoot.left = firstNode;
            newRoot.right = secondNode;
            letterQueue.add(newRoot);

            firstNode = letterQueue.poll();
        }
        return firstNode;
    }

    public static void main(String[] args){
        HoffmanEncoding hoffmanEncoding = new HoffmanEncoding();
        hoffmanEncoding.populateCharacters("A_DEAD_DAD_CEDED_A_BAD_BABE_A_BEADED_ABACA_BED");
        Node root = hoffmanEncoding.generateHuffManTree();
        System.out.println(root);
        hoffmanEncoding.traverseTree(root, "");
        System.out.println(hoffmanEncoding.encodedMap);
        System.out.println(hoffmanEncoding.printCompressed("A_DEAD_DAD_CEDED_A_BAD_BABE_A_BEADED_ABACA_BED"));
    }

    private String printCompressed(String s ) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : s.toCharArray()) {
            stringBuilder.append(encodedMap.get(c));
        }
        return stringBuilder.toString();
    }

    private void traverseTree(Node root, String s) {
        if (root == null) return;
        if (root.right == null && root.left == null) {
            encodedMap.put(root.character.charAt(0), s);
        }
        traverseTree(root.left, s + "0");
        traverseTree(root.right, s + "1");
    }
}
