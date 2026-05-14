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

public class LazyLoadingFlattenNestedIterator implements Iterator<Integer> {
    Deque<Iterator<NestedInteger>> stackholder = new ArrayDeque<>();
    Integer peekedValue;

    public LazyLoadingFlattenNestedIterator(List<NestedInteger> nestedIntegerList) {
        stackholder.push(nestedIntegerList.iterator());
    }

    @Override
    public boolean hasNext() {
        // Since we always consume with hasNext (we call it.next()) - we need a "cached" value here so we dont consume unless the cached value has been used by the consumers by calling next();
        if (peekedValue != null) {
            // previous call to hasNext has set this, but next() has not been called ever.
            // no op here, there exists a pointer to the next value
            return true;
        }
        while (!stackholder.isEmpty()) {
            var it = stackholder.peek();
            // Keep in mind that we are consuming the iterator here, which is the reason for the above cache ^
            if (it.hasNext()) {
                var ni = it.next();
                if (ni.isInteger()) {
                    peekedValue = ni.getInteger();
                    return true;
                } else {
                    // found a list, need to add to stack
                    stackholder.push(ni.getList().iterator());
                }
            } else {
                // reached end of iterator, pop from stack and move on
                stackholder.pop();
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) return null;
        // consume from cache so that next value can be obtained on next run
        var result = peekedValue;
        peekedValue = null;
        return result;
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

            LazyLoadingFlattenNestedIterator it = new LazyLoadingFlattenNestedIterator(list);
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

            LazyLoadingFlattenNestedIterator it = new LazyLoadingFlattenNestedIterator(list);
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


            LazyLoadingFlattenNestedIterator it = new LazyLoadingFlattenNestedIterator(list);
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


            LazyLoadingFlattenNestedIterator it = new LazyLoadingFlattenNestedIterator(list);
            List<Integer> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(it.next());
            }
            assertEquals(List.of(1, 2, 3, 3), result, "complex nested mix");
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
