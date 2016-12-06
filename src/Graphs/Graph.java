package Graphs;

/**
 * Created by schandramouli on 12/5/16.
 */
public class Graph {
    // this will be a adjacency matrix based representation
    int[][] adjMatrix;

    public Graph(int n) {
        // n is number of nodes
        adjMatrix = new int[n][n];
    }

    void addEdge(int node1, int node2) {
        adjMatrix[node1][node2] = 1;
    }

    void removeEdge(int node1, int node2) {
        adjMatrix[node1][node2] = 0;
    }

    boolean hasEdge(int node1, int node2) {
        return adjMatrix[node1][node2] == 1;
    }

    public static void main(String[] args) {

    }
}
