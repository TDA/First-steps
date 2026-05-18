package OpenAI_Interviews;

// Problem Statement: Cluster Topology Queries
//
//You are given a cluster of machines arranged as a rooted tree. Each machine has a unique integer ID from 0 to n - 1.
//
//The tree is described by a parent array:
//
//    parents[i] is the parent of node i
//    exactly one node has parent -1, which is the root
//
//Implement a class with these operations:
//
//    ClusterTopologyQueries(parents) -> Build the tree from the parent array.
//    query(command) -> Execute one of the following commands and return the answer as a string.
//
//Supported commands:
//
//    "count" -> Return the total number of nodes in the tree as a string.
//    "topology" -> Return a nested string representation of the tree.
//
//Topology format:
//
//    If a node has no children, represent it as just its ID
//    Example: "4"
//    If a node has children, represent it as
//    "ID(child1,child2,...)"
//
//Children must appear in ascending node ID order.
//Example
//
//Input:
//
//ClusterTopologyQueries([-1, 0, 0, 1, 1, 3])
//query("count")
//query("topology")
//
//Output:
//
//"6"
//"0(1(3(5),4),2)"
//
//Explanation:
//
//    There are 6 nodes in the tree.
//    Node 0 has children 1 and 2
//    Node 1 has children 3 and 4
//    Node 3 has child 5
//
//Constraints
//
//    The parent array describes exactly one rooted tree.
//    Exactly one node is the root.
//    Node IDs range from 0 to n - 1.
//    The query command will be either "count" or "topology".
//    The returned value must be a string.
//    For the topology query, child order is part of the expected output and must follow ascending node ID order.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

class TreeNode {
    Integer name;
    List<TreeNode> children;
    TreeNode parent;

    public TreeNode(Integer name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    @Override
    public String toString() {
        return name.toString() + "(" + children.toString() + ")";
    }
}

enum State {UNVISITED, VISITING, VISITED}

public class ClusterTopologies {
    Map<Integer, TreeNode> nodes = new HashMap<>();
    TreeNode root = null;

    public ClusterTopologies(int[] parents) {
        // [-1, 0, 0, 1, 1, 3]
        // 2 passes, create nodes first then assign parent - child
        for (int i = 0; i < parents.length; i++) {
            TreeNode node = new TreeNode(i);
            nodes.put(i, node);
        }

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == -1) {
                // found root
                root = nodes.get(i);
            } else {
                // we found a child node, update parents and child
                var childNode = nodes.get(i);
                var parentNode = nodes.get(parents[i]);
                parentNode.children.add(childNode);
                childNode.parent = parentNode;
            }
        }
    }

    String query(String command) {
        switch (command) {
            case "count": // do count;
                return String.valueOf(nodes.size());
            case "topology": // traverse and print the tree
                return dfs(root, new StringBuilder());
            case "hasCycle":
                return hasCycle();
            default:
                throw new IllegalArgumentException("Invalid command");
        }
    }

    private String hasCycle() {
        State[] states = new State[nodes.size()];
        for (TreeNode node : nodes.values()) {
            if (states[node.name] == null) {
                if (detectCycles(node, states)) return "true";
            }
        }

        return "false";
    }

    private boolean detectCycles(TreeNode node, State[] states) {
        if (states[node.name] == State.VISITING) return true;
        if (states[node.name] == State.VISITED) return false;
        // else this is being visited, mark and return
        states[node.name] = State.VISITING;

        for (TreeNode child : node.children) {
            if (detectCycles(child, states)) return true;
        }

        states[node.name] = State.VISITED;
        return false;
    }

    private String dfs(TreeNode node, StringBuilder result) {
        if (node.children.isEmpty()) {
            // found leaf, just return its value
            result.append(node.name.toString());
            return result.toString();
        }
        // this is non-leaf, need recursion
        result.append(node.name.toString());
        result.append("(");
        for (int i = 0; i < node.children.size(); i++) {
            dfs(node.children.get(i), result);
            if (i != node.children.size() - 1)
                result.append(",");
        }
        result.append(")");

        return result.toString();
    }

    public static void main(String[] args) {
        runTest("returns count for sample topology", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1, 0, 0, 1, 1, 3});

            assertEquals("6", topology.query("count"));
        });

        runTest("returns nested sample topology", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1, 0, 0, 1, 1, 3});

            assertEquals("0(1(3(5),4),2)", topology.query("topology"));
        });

        runTest("orders children by ascending node id", () -> {
            int[] parents = {2, 2, -1, 0, 0, 2};
            ClusterTopologies topology = new ClusterTopologies(parents);


            assertEquals("2(0(3,4),1,5)", topology.query("topology"));
        });

        runTest("preserves children when parent node appears later in parent array", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{2, 2, 3, -1});

            assertEquals("4", topology.query("count"));
            assertEquals("3(2(0,1))", topology.query("topology"));
        });

        runTest("handles single node tree", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1});

            assertEquals("1", topology.query("count"));
            assertEquals("0", topology.query("topology"));
        });

        runTest("handles deep chain topology", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1, 0, 1, 2, 3});

            assertEquals("5", topology.query("count"));
            assertEquals("0(1(2(3(4))))", topology.query("topology"));
        });

        runTest("hasCycle returns false for a valid rooted tree", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1, 0, 0, 1, 1, 3});

            assertEquals("false", topology.query("hasCycle"));
        });

        runTest("hasCycle returns true for a cycle outside the root component", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1, 2, 1});

            assertEquals("true", topology.query("hasCycle"));
        });

        runTest("throws for unsupported command", () -> {
            ClusterTopologies topology = new ClusterTopologies(new int[]{-1});

            try {
                topology.query("unknown");
                throw new AssertionError("Expected unsupported command to throw");
            } catch (IllegalArgumentException expected) {
                assertEquals("Invalid command", expected.getMessage());
            }
        });
    }
}
