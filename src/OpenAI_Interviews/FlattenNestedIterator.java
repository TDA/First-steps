package OpenAI_Interviews;

// NOTE: This is similar to the meta question at src/fb_recent/NestedInteger.java and the
// Google question src/GoogleInterviews/FirstInterview.java with a slight twist (was extension for the others).
// The question talks about a streaming problem where you get a consistent stream of incoming inputs instead of being
// given all inputs ahead of time and that this stream is so large that it won't fit in memory from the beginning.
// for the solution we wrote for meta we did active flattening at construction time storing
// everything into memory and then the processing itself was just a linear scan through the resulting flattened array list.
// However, we cannot do that here so we will need to use a technique that will allow us to incrementally flatten things
// instead of doing it ahead of time.

// Full Problem: Given a list that can contain integers or other nested lists, implement an iterator that flattens the structure and yields only the integers one by one.
//
//Interface:
//
//NestedIterator(List<NestedInteger> nestedList)
//
//next() → int
//
//hasNext() → boolean
//
//Example:
//
//Input:
//[
//   [1, [2]],
//   3,
//   [4, [5, 6]]
//]
//
//Iterator yields: 1, 2, 3, 4, 5, 6
//
//Core Data Structures: Deque<Iterator<NestedInteger>> — stack of iterators. hasNext() advances through nested lists by pushing/popping iterators until it finds the next integer. next() returns it.
//
//Alternative approach: Deque<int[]> where each frame is (listIndex, positionInList) — more explicit, easier to serialize for pause/resume.
//
//Key Design Decisions:
//
//Eagerly flatten in constructor (simple but O(n) space upfront) vs lazily flatten in hasNext() (more complex but memory-efficient)
//Lazy is preferred for interview — shows iterator protocol understanding
//
//Extensions:
//
//Support pause() and resume() — serialize the stack state (this IS Q14)
//Support filtering (skip elements matching a predicate)
//Support merging multiple iterators (merge-sort style)
//Handle concurrent modification of underlying data


/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 * <p>
 * // @return true if this NestedInteger holds a single integer, rather than a nested list.
 * public boolean isInteger();
 * <p>
 * // @return the single integer that this NestedInteger holds, if it holds a single integer
 * // Return null if this NestedInteger holds a nested list
 * public Integer getInteger();
 * <p>
 * // @return the nested list that this NestedInteger holds, if it holds a nested list
 * // Return empty list if this NestedInteger holds a single integer
 * public List<NestedInteger> getList();
 * }
 */

import fb_recent.NestedInteger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

public class FlattenNestedIterator {
    Deque<NestedInteger> stackholder = new ArrayDeque<>();

    public FlattenNestedIterator(List<NestedInteger> nestedList) {
        stackholder.addAll(nestedList);
    }

    public boolean hasNext() {
        while (!stackholder.isEmpty()) {
            var nestedInteger = stackholder.peek();
            if (nestedInteger.isInteger()) {
                return true;
            } else {
                // need to push to stack
                stackholder.pop();
                var list = nestedInteger.getList();
                for (int i = list.size() - 1; i >= 0; i--) {
                    stackholder.push(list.get(i));
                }
            }
        }
        return false;
    }

    public Integer next() {
        if (!hasNext()) return null;
        return stackholder.pop().getInteger();
    }




    public static void main(String[] args) {
        runTest("simple flat list", () -> {
            List<NestedInteger> list = new ArrayList<>();
            list.add(new NestedInteger(1));
            list.add(new NestedInteger(2));

            FlattenNestedIterator it = new FlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2), result, "simple flat list");
        });

        runTest("nested lists", () -> {
            List<NestedInteger> list = new ArrayList<>();
            NestedInteger outer = new NestedInteger();
            outer.getList().add(new NestedInteger(1));
            outer.getList().add(new NestedInteger(2));
            list.add(outer);

            FlattenNestedIterator it = new FlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2), result, "nested lists");
        });

        runTest("mixed nested and flat", () -> {
            List<NestedInteger> list = new ArrayList<>();
            list.add(new NestedInteger(1));

            NestedInteger outer = new NestedInteger();
            outer.getList().add(new NestedInteger(2));
            outer.getList().add(new NestedInteger(3));
            list.add(outer);

            list.add(new NestedInteger(4));
            System.out.println(list);

            FlattenNestedIterator it = new FlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2, 3, 4), result, "mixed nested and flat");
        });

        runTest("complex nested mix", () -> {
            // Represent: [ [], [1, [2,3]], [], [3] ]
            List<NestedInteger> list = new ArrayList<>();

            // first empty list
            list.add(new NestedInteger());

            // second element: [1, [2,3]]
            NestedInteger second = new NestedInteger();
            second.getList().add(new NestedInteger(1));

            NestedInteger inner = new NestedInteger();
            inner.getList().add(new NestedInteger(2));
            inner.getList().add(new NestedInteger(3));
            second.getList().add(inner);
            list.add(second);

            // third empty list
            list.add(new NestedInteger());

            // fourth element: [3]
            NestedInteger fourth = new NestedInteger();
            fourth.getList().add(new NestedInteger(3));
            list.add(fourth);

            FlattenNestedIterator it = new FlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2, 3, 3), result, "complex nested mix");
        });
    }
}
