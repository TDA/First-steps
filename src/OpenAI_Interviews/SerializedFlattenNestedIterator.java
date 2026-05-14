package OpenAI_Interviews;

// Full Problem: Implement an iterator over a nested data structure with
// next(),
// hasNext(),
// The question is very similar to the flattened nested iterator question. However, there are trade-offs between the two approaches.
// In the approach we took for flattened nested iterator, we can do eager loading in the constructor and flatten it out, or lazy loading
// if we need to support larger data sets by pushing things into a stack only when hasnext is called.

// Pause and resume can be implemented in the other patterns as well. However, you would be serializing and deserializing the entire remaining stack, which consumes O(N) memory.
// In other words, the three compare to each other like so.
// 1. Eager Loading - Memory: O(N) always. Full serialize and de-serialize.
// 2. Lazy Loading with elements - Memory: O(Depth) best case, O(N) worst case (flat lists). Full serialize and de-serialize.
// 3. Lazy Loading with iterators - Memory: O(Depth) always - we only store the iterator, not the elements of the list. serialize and de-serialize only O(D) objects.


import fb_recent.NestedInteger;

import java.util.*;

record ListSnapshot(List<NestedInteger> nestedIntegerList, int index) {
}

public class SerializedFlattenNestedIterator implements Iterator<Integer> {
    Deque<ListSnapshot> stackholder = new ArrayDeque<>();
    List<NestedInteger> originalList;

    public SerializedFlattenNestedIterator(List<NestedInteger> nestedIntegerList) {
        stackholder.push(new ListSnapshot(nestedIntegerList, 0));
        originalList = nestedIntegerList;
    }

    @Override
    public boolean hasNext() {
        while (!stackholder.isEmpty()) {
            ListSnapshot snapshot = stackholder.peek();
            List<NestedInteger> list = snapshot.nestedIntegerList();
            int index = snapshot.index();
            if (index < list.size()) {
                // if there are items left in current Snapshot
                NestedInteger ni = list.get(index);
                if (ni.isInteger()) {
                    // found integer, return
                    return true;
                } else {
                    // found a list - we need to move index for this current traversal, but also insert its child list
                    stackholder.pop();
                    stackholder.push(new ListSnapshot(list, index + 1));
                    stackholder.push(new ListSnapshot(ni.getList(), 0));
                }
            } else {
                // this snapshot is done, remove
                stackholder.pop();
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) return null;
        // consumed, remove and re-insert with updated values
        ListSnapshot pop = stackholder.pop();
        stackholder.push(new ListSnapshot(pop.nestedIntegerList(), pop.index() + 1));
        return pop.nestedIntegerList().get(pop.index()).getInteger();
    }

    public List<Integer> pause() {
        List<Integer> serializablePositions = stackholder.stream().map(ListSnapshot::index).toList();
        stackholder.clear();
        return serializablePositions;
    }

    public void resume(List<Integer> positions) {
        // reconstruct the stack from positions and root node
        // we trust that positions dont lie, and the size() of positions == depth of the stack
        // we need to traverse positions in reverse order since deque::stream does it as FIFO, and we need LIFO
        var currentList = originalList;
        for (int i = positions.size() - 1; i >= 0; i--) {
            // push a frame, and then move currentList to the right index so we can get the next level if its a list
            var position = positions.get(i);
            stackholder.push(new ListSnapshot(currentList, position));
            // why position minus 1? because when we are traversing through any of these items in constructing the stack
            // in the first place. Whenever we come across a list, we pop out the existing parent, move the parent snapshot to
            // point to the next available item, and then insert the child at index 0, which means if we save it at this point,
            // the parent will always be pointing to the item after the list and so we have to go one step backwards to find the actual list and continue building things
            if (position <= currentList.size()) {
                currentList = currentList.get(position - 1).getList();
            }
        }
    }

    @FunctionalInterface
    private interface TestCase {
        void run();
    }

    private static int testsRun = 0;
    private static int testsPassed = 0;

    static void main(String[] args) {
        runTest("simple flat list", () -> {
            List<NestedInteger> list = new ArrayList<>();
            list.add(new NestedInteger(1));
            list.add(new NestedInteger(2));

            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);
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

            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);
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


            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);
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


            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2, 3, 3), result, "complex nested mix");
        });

        runTest("pause preserves state", () -> {
            // Build a simple nested list: [1, [2, 3]]
            List<NestedInteger> list = new ArrayList<>();
            list.add(new NestedInteger(1));
            NestedInteger inner = new NestedInteger();
            inner.getList().add(new NestedInteger(2));
            inner.getList().add(new NestedInteger(3));
            list.add(inner);

            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);

            // Consume the first element
            assertTrue(it.hasNext(), "has next before pause");
            int first = it.next();
            assertEquals(1, first, "first element");

            // Pause the iterator
            System.out.println("----Before pausing------");
            System.out.println(it.stackholder);
            List<Integer> positions = it.pause();
            System.out.println(positions);

            // After pausing, stack is cleared, so no possible iterations
            assertTrue(!it.hasNext(), "should not have next after pause");

            // Resume and finish
            it.resume(positions);
            System.out.println("----After resuming------");
            System.out.println(it.stackholder);

            assertTrue(it.hasNext(), "has next after resume");
            int second = it.next();
            assertEquals(2, second, "second element after resume");
            int third = it.next();
            assertEquals(3, third, "third element after resume");
            assertTrue(!it.hasNext(), "no more elements");
        });

        runTest("resume from saved state", () -> {
            // Build a nested list: [1, [2], 3]
            List<NestedInteger> list = new ArrayList<>();
            list.add(new NestedInteger(1));
            NestedInteger inner = new NestedInteger();
            inner.getList().add(new NestedInteger(2));
            list.add(inner);
            list.add(new NestedInteger(3));

            SerializedFlattenNestedIterator it = new SerializedFlattenNestedIterator(list);

            // Consume first two elements
            assertEquals(1, it.next(), "first");
            assertEquals(2, it.next(), "second");

            System.out.println("----Before pausing------");
            System.out.println(it.stackholder);
            // Pause now
            List<Integer> positions = it.pause();
            System.out.println(it.stackholder);

            // Resume and consume the rest
            it.resume(positions);
            System.out.println("----After resuming------");
            System.out.println(it.stackholder);
            assertTrue(it.hasNext(), "has elements after resume");
            assertEquals(3, it.next(), "third after resume");
            assertTrue(!it.hasNext(), "no more elements after resume");
        });

        System.out.println("\nPassed " + testsPassed + " / " + testsRun + " tests.");
    }

    private static void runTest(String name, TestCase testCase) {
        testsRun++;
        try {
            testCase.run();
            testsPassed++;
            System.out.println("[PASS] " + name);
        } catch (AssertionError e) {
            System.err.println("[FAIL] " + name + ": " + e.getMessage());
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " – expected: " + expected + ", actual: " + actual);
        }
    }
}