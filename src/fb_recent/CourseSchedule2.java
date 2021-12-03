package fb_recent;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseSchedule2 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Graph graph = new Graph();
        int[] arr = new int[numCourses];
        if (prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++)
                arr[i] = i;
            return arr;
        };
        // Create Graph
        for (int i = 0; i < numCourses; i++) {
            graph.getOrAddNode("" + i);
        }
        for (int[] prerequisite : prerequisites) {
            int firstCourse = prerequisite[0];
            int prereqCourse = prerequisite[1];
            System.out.printf("from %s to %s\n", prereqCourse, firstCourse);
            GraphNode node = graph.getOrAddNode("" + prereqCourse);
            GraphNode node1 = graph.getOrAddNode("" + firstCourse);
            graph.addDirectedEdge(node, node1);
        }
        // Topo sort and see if everything is reachable
        Collection<GraphNode> graphNodes = graph.nodes.values();
        System.out.println(graphNodes);

        Set<GraphNode> visitedNodes = new LinkedHashSet<>();
        Set<GraphNode> allVisitedNodes = topologicalSort(graphNodes, visitedNodes);

        if (allVisitedNodes.size() != numCourses) return new int[0];
        int i = 0;
        for (GraphNode x : allVisitedNodes)
            arr[i++] = Integer.parseInt(x.nodeName);
        return arr;
    }

    private Set<GraphNode> topologicalSort(Collection<GraphNode> nodes, Set<GraphNode> visitedNodes) {
        while (!nodes.isEmpty()) {
            List<GraphNode> zeroInboundNodes = nodes.stream().filter((node -> node.inboundEdges == 0)).collect(Collectors.toList());
            if (zeroInboundNodes.isEmpty()) break;
            visitedNodes.addAll(zeroInboundNodes);
            for (GraphNode zeroInboundNode : zeroInboundNodes) {
                Set<GraphNode> adjacentNodes = zeroInboundNode.getAdjacentNodes();
                for (GraphNode neighborNode : adjacentNodes) {
                    neighborNode.decrementInboundEdges();
                }
            }
            nodes.removeAll(zeroInboundNodes);
        }
        return visitedNodes;
    }
}

class GraphNode {
    String nodeName;
    int inboundEdges = 0;
    Map<GraphNode, Integer> edges = new LinkedHashMap<>();

    public void incrementInboundEdges() {
        this.inboundEdges++;
    }

    public void decrementInboundEdges() {
        this.inboundEdges--;
    }

    public GraphNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public Set<GraphNode> getAdjacentNodes() {
        return edges.keySet();
    }

    public void addEdge(GraphNode b) {
        edges.put(b, 0);
    }
}

class Graph {
    public Graph() {
    }

    Map<String, GraphNode> nodes = new LinkedHashMap<>();

    public void addDirectedEdge(GraphNode nodeX, GraphNode nodeY) {
        nodeX.addEdge(nodeY);
        nodeY.incrementInboundEdges();
    }

    public GraphNode getOrAddNode(String currentNodeName) {
        if (!nodes.containsKey(currentNodeName)) {
            nodes.put(currentNodeName, new GraphNode(currentNodeName));
        }
        return nodes.get(currentNodeName);
    }
}
