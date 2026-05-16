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
//    ClusterTopologyQueries(parents)
//    Build the tree from the parent array.
//    query(command)
//    Execute one of the following commands and return the answer as a string.
//
//Supported commands:
//
//    "count"
//    Return the total number of nodes in the tree as a string.
//    "topology"
//    Return a nested string representation of the tree.
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

public class ClusterTopologies {
}
