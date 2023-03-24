package Graphs;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Graph graph = new Graph();
        if (prerequisites.length == 0) return true;
        // Create Graph
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
        return  (allVisitedNodes.size() == numCourses);
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

    public static void main(String[] args){
        CourseSchedule courseSchedule = new CourseSchedule();
        int numCourses = 2;
        int[][] prerequisites = {{1,0}};

        System.out.println(courseSchedule.canFinish(numCourses, prerequisites));

        int numCourses1 = 2; int[][] prerequisites1 = {{1,0},{0,1}};
        System.out.println(courseSchedule.canFinish(numCourses1, prerequisites1));
    }
}
