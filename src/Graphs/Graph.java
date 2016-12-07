package Graphs;

import java.util.Arrays;

/**
 * Created by schandramouli on 12/5/16.
 */
public class Graph {
    // this will be a adjacency matrix based representation
    // pros, easy to implement
    // cons, for a sparse graph the matrix will also be sparse and waste extra space
    // the other option is an adjacency list
    // which creates a sort of ragged array, i think.
    int[][] adjMatrix;

    public Graph(int n) {
        // n is number of nodes
        adjMatrix = new int[n][n];
    }

    void addEdge(int node1, int node2) {
        adjMatrix[node1 - 1][node2 - 1] = 1;
        adjMatrix[node2 - 1][node1 - 1] = 1;
    }

    void removeEdge(int node1, int node2) {
        adjMatrix[node1 - 1][node2 - 1] = 0;
        adjMatrix[node2 - 1][node1 - 1] = 0;
    }

    boolean hasEdge(int node1, int node2) {
        return adjMatrix[node1 - 1][node2 - 1] == 1;
    }

    public void printGraph() {
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                System.out.print(adjMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(2);
        graph.addEdge(1, 2);
        System.out.println(graph.hasEdge(1, 2));
        graph.printGraph();
        graph.removeEdge(1, 2);
        System.out.println(graph.hasEdge(1, 2));
        graph.printGraph();
    }
}
