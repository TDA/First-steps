package Graphs;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphTraversals {
    public static void main(String[] args){
        GraphTraversals graphTraversals = new GraphTraversals();
        Graph graph = new Graph();
        GraphNode nodeA = new GraphNode("a");
        GraphNode nodeB = new GraphNode("b");
        GraphNode nodeC = new GraphNode("c");
        GraphNode nodeD = new GraphNode("d");
        GraphNode nodeE = new GraphNode("e");
        GraphNode nodeF = new GraphNode("f");
        GraphNode nodeG = new GraphNode("g");
        GraphNode nodeH = new GraphNode("h");
        GraphNode nodeI = new GraphNode("i");
        GraphNode nodeX = new GraphNode("x");
        GraphNode nodeY = new GraphNode("y");

        ///      ---> b ---> d
        ///   a            ---> f
        ///     ---> c ---> e

        ///     g ---> h ---> i
        ///     x ---> y

        graph.addDirectedEdge(nodeA, nodeB);
        graph.addDirectedEdge(nodeA, nodeC);
        graph.addDirectedEdge(nodeB, nodeD);
        graph.addDirectedEdge(nodeD, nodeF);
        graph.addDirectedEdge(nodeC, nodeE);
        graph.addDirectedEdge(nodeE, nodeF);
        graph.addDirectedEdge(nodeG, nodeH);
        graph.addDirectedEdge(nodeH, nodeI);
        graph.addDirectedEdge(nodeX, nodeY);

        graphTraversals.dfs(nodeA);
        graphTraversals.bfs(nodeA);
        graphTraversals.topologicalSort(graph);

        System.out.println(graphTraversals.findRoute(nodeA, nodeB));
        System.out.println(graphTraversals.findRoute(nodeA, nodeD));
        System.out.println(graphTraversals.findRoute(nodeB, nodeD));
        System.out.println(graphTraversals.findRoute(nodeD, nodeG));
        System.out.println(graphTraversals.findRoute(nodeD, nodeA));


        Graph graph1 = new Graph();
        graph1.addDirectedEdge(nodeA, nodeB);
        graph1.addDirectedEdge(nodeA, nodeC);
        graph1.addDirectedEdge(nodeB, nodeC);
        graph1.addDirectedEdge(nodeB, nodeD);
        graph1.addDirectedEdge(nodeC, nodeE);
        graph1.addDirectedEdge(nodeD, nodeE);
        graphTraversals.topologicalSort(graph1);
    }

    private void topologicalSort(Graph graph) {
        // First traverse and find the inbound edges for each node. Update node data to include inbounds
        Collection<GraphNode> graphNodes = graph.nodes.values();
        graphNodes.forEach(graphNode -> {
            graphNode.edges.keySet().forEach(GraphNode::incrementInboundEdges);
        });

        Set<GraphNode> visitedNodes = new LinkedHashSet<>();
        topologicalSort(graphNodes, visitedNodes);
        System.out.println(visitedNodes);
    }

    private void topologicalSort(Collection<GraphNode> nodes, Set<GraphNode> visitedNodes) {
        int combinations = 1;
        while (!nodes.isEmpty()) {
            List<GraphNode> zeroInboundNodes = nodes.stream().filter((node -> node.inboundEdges == 0)).collect(Collectors.toList());
            // System.out.println(zeroInboundNodes);
            combinations = combinations * zeroInboundNodes.size();
            visitedNodes.addAll(zeroInboundNodes);
            // remove these nodes from the graph
            for (GraphNode node: zeroInboundNodes) {
                for (GraphNode neighborNode: node.getAdjacentNodes()) {
                    neighborNode.decrementInboundEdges();
                }
            }
            nodes.removeAll(zeroInboundNodes);
        }
        System.out.println("Total combinations: " + combinations);
    }

    private void bfs(GraphNode nodeA) {
        LinkedHashSet<GraphNode> visitedNodes = new LinkedHashSet<>();
        Queue<GraphNode> toVisit = new ArrayDeque<>();
        toVisit.add(nodeA);
        while (!toVisit.isEmpty()) {
            GraphNode nextNode = toVisit.poll();
            if (visitedNodes.contains(nextNode)) continue;
            visitedNodes.add(nextNode);
            toVisit.addAll(nextNode.getAdjacentNodes());
        }
        System.out.println(visitedNodes.stream().map(n -> n.nodeName).collect(Collectors.toList()));;
    }

    private void dfs(GraphNode nodeA) {
        LinkedHashSet<GraphNode> visitedNodes = new LinkedHashSet<>();
        dfs(nodeA, visitedNodes);
        System.out.println(visitedNodes.stream().map(n -> n.nodeName).collect(Collectors.toList()));;
    }

    private void dfs(GraphNode currentNode, Set<GraphNode> visitedNodes) {
        if (visitedNodes.contains(currentNode))
            return;
        visitedNodes.add(currentNode);
        for (GraphNode nextNode: currentNode.getAdjacentNodes()) {
            dfs(nextNode, visitedNodes);
        }
    }

    private boolean findRoute(GraphNode nodeA, GraphNode nodeB) {
        LinkedHashSet<GraphNode> visitedNodes = new LinkedHashSet<>();
        Queue<GraphNode> toVisit = new ArrayDeque<>();
        toVisit.add(nodeA);
        while (!toVisit.isEmpty()) {
            GraphNode nextNode = toVisit.poll();
            if (visitedNodes.contains(nextNode)) continue;
            visitedNodes.add(nextNode);
            Set<GraphNode> adjacentNodes = nextNode.getAdjacentNodes();
            if (adjacentNodes.contains(nodeB)) return true;
            toVisit.addAll(adjacentNodes);
        }
        return false;
    }
}
