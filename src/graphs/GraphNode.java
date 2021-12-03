package graphs;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class GraphNode {
    String nodeName;
    int inboundEdges = 0;
    Map<GraphNode, Integer> edges = new LinkedHashMap<>();

    public GraphNode(int i) {
        this.nodeName = "" + i;
    }

    public void incrementInboundEdges() {
        this.inboundEdges++;
    }

    public void decrementInboundEdges() {
        this.inboundEdges--;
    }

    public GraphNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setEdges(Map<GraphNode, Integer> edges) {
        this.edges = edges;
    }

    public Map<GraphNode, Integer> getEdges() {
        return new LinkedHashMap<>(edges);
    }

    public Set<GraphNode> getAdjacentNodes() {
        return edges.keySet();
    }

    public void addEdge(GraphNode b) {
        edges.put(b, 0);
    }

    public void addEdge(GraphNode b, int weight) {
        edges.put(b, weight);
    }

    public void removeEdge(GraphNode nodeY) {
        edges.remove(nodeY);
    }

    public void removeAllEdges() {
        edges =  new LinkedHashMap<>();
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "nodeName='" + nodeName + '\'' +
                "inboundEdges='" + inboundEdges + '\'' +
                '}';
    }
}
