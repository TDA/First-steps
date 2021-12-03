package graphs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public Graph() {
    }

    Map<String, GraphNode> nodes = new LinkedHashMap<>();

    public void addDirectedEdge(GraphNode nodeX, GraphNode nodeY) {
        nodeX.addEdge(nodeY);
        nodeY.incrementInboundEdges();
        nodes.putAll(Map.of(nodeX.nodeName, nodeX, nodeY.nodeName, nodeY));
    }

    public GraphNode getOrAddNode(String currentNodeName) {
        if (!nodes.containsKey(currentNodeName)) {
            nodes.put(currentNodeName, new GraphNode(currentNodeName));
        }
        return nodes.get(currentNodeName);
    }

    public void addNode(GraphNode node) {
        nodes.put(node.nodeName, node);
    }

    public void addNodes(List<GraphNode> nodes) {
        for (GraphNode graphNode: nodes) {
            this.nodes.put(graphNode.nodeName, graphNode);
        }
    }

    public void addUndirectedEdge(GraphNode nodeX, GraphNode nodeY) {
        nodeX.addEdge(nodeY);
        nodeY.addEdge(nodeX);
        nodes.putAll(Map.of(nodeX.nodeName, nodeX, nodeY.nodeName, nodeY));
    }

    public void removeUndirectedEdge(GraphNode nodeX, GraphNode nodeY) {
        nodeX.removeEdge(nodeY);
        nodeY.removeEdge(nodeX);
    }

    public void removeDirectedEdge(GraphNode nodeX, GraphNode nodeY) {
        nodeX.removeEdge(nodeY);
    }

    public Graph(Graph graphToCopy) {
        graphToCopy.nodes.forEach((name, graphNode) -> {
            GraphNode copiedNode = new GraphNode(graphNode.nodeName);
            copiedNode.setEdges(graphNode.getEdges());
            nodes.put(copiedNode.nodeName, copiedNode);
        });
    }
}
