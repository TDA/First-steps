package OpenAI_Interviews;

// Full Problem: Given a set of tasks with dependencies, determine which tasks can execute in parallel at each level.
//
//Interface:
//
//getLevels(tasks, dependencies) → List<List<String>>
//
//Example:
//
//Tasks: [A, B, C, D, E]
//
//Dependencies: A→B, A→C, B→D, C→D, D→E
//
//Output:
//
//  Level 0: [A]
//
//  Level 1: [B, C]
//
//  Level 2: [D]
//
//  Level 3: [E]
//
//Core Data Structures: HashMap<String, List<String>> for adjacency list, HashMap<String, Integer> for in-degree, Queue<String> for BFS.
//
//Algorithm: Kahn's algorithm — start with all nodes with in-degree 0 (Level 0). Process each level as a BFS wave: for each node in the current wave, decrement in-degree of neighbors; those reaching 0 form the next level.
//
//Extensions:
//
//Detect cycles (if processed count < total tasks after BFS completes)
//Support task weights / estimated durations — compute critical path
//Support dynamic task addition (new tasks added while executing)


import java.util.*;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

public class TaskDependencies {
    List<List<String>> getLevels(List<String> tasks, Map<String, List<String>> dependencies) {
        Map<String, Integer> inDegreesMap = getInDegreesMap(tasks, dependencies);

        // topo sort the levels
        return topoSort(inDegreesMap, dependencies);
    }

    private static Map<String, Integer> getInDegreesMap(List<String> tasks, Map<String, List<String>> dependencies) {
        Map<String, Integer> inDegreesMap = new HashMap<>();
        // Build the in-degrees first from the adjacencyMap
        var rows = dependencies.values();
        for (String task : tasks) {
            inDegreesMap.put(task, 0);
            for (var row : rows) {
                if (row.contains(task)) {
                    inDegreesMap.compute(task, (k, v) -> v + 1);
                }
            }
        }
        return inDegreesMap;
    }

    private List<List<String>> topoSort(Map<String, Integer> inDegreesMap, Map<String, List<String>> dependencies) {
        int processedNodes = 0;
        var entries = inDegreesMap.entrySet();
        Queue<String> topoQueue = new ArrayDeque<>();
        // Seed with nodes that don't have any dependencies
        for (var entry : entries) {
            if (entry.getValue().equals(0))
                topoQueue.add(entry.getKey());
        }
        // Topo sort - Lever order traversal
        List<List<String>> allLevelLists = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        while (!topoQueue.isEmpty()) {
            int levelSize = topoQueue.size();
            List<String> levelList = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                var task = topoQueue.poll();
                if (visited.contains(task)) continue;
                levelList.add(task);
                visited.add(task);
                processedNodes++;
                var childrenNode = dependencies.get(task);
                if (childrenNode != null)
                    topoQueue.addAll(childrenNode);
            }
            allLevelLists.add(levelList);
        }

        if (processedNodes != inDegreesMap.size()) throw new IllegalArgumentException("Dependency map has cycles");
        return allLevelLists;
    }

    static void main() {
        TaskDependencies td = new TaskDependencies();
        runTest("standard dependencies", () -> {
            List<String> tasks = List.of("A", "B", "C", "D", "E");
            Map<String, List<String>> dependencies = Map.of(
                    "A", List.of("B", "C"),
                    "B", List.of("D"),
                    "C", List.of("D"),
                    "D", List.of("E")
            );
            List<List<String>> expectedResults = List.of(
                    List.of("A"),
                    List.of("B", "C"),
                    List.of("D"),
                    List.of("E")
            );
            assertEquals(td.getLevels(tasks, dependencies), expectedResults, "expected levels not shown");
        });

        runTest("dependencies with cycles", () -> {
            List<String> tasks = List.of("A", "B", "C", "D", "E");
            Map<String, List<String>> dependencies = Map.of(
                    "A", List.of("B", "C"),
                    "B", List.of("D"),
                    "C", List.of("D"),
                    "D", List.of("E"),
                    "E", List.of("A")
            );
            try {
                td.getLevels(tasks, dependencies);
            } catch (IllegalArgumentException e) {
                assertEquals(e.getMessage(), "Dependency map has cycles", "Exception not thrown when cycles present");
            }
        });
    }
}
