package OpenAI_Interviews;

//Full Problem: Implement a key-value database with nested transaction support.
//
//Interface:
//
//set(key, value) → void
//
//get(key) → value or null
//
//delete(key) → void
//
//begin() → void (start a new transaction, can be nested)
//
//commit() → void (apply current transaction to parent scope)
//
//rollback() → void (discard current transaction)
//
//Example:
//
//set("x", "10")
//
//begin()
//
//set("x", "20")
//
//get("x") → "20"
//
//begin()
//
//set("x", "30")
//
//rollback()
//
//get("x") → "20"
//
//commit()
//
//get("x") → "20"
//
//Core Data Structures: Deque<HashMap<String, String>> — stack of scopes. Each begin() pushes a new map. get() walks the stack top-down until it finds the key. commit() merges top scope into the one below. rollback() pops and discards the top scope.
//
//Key Design Decisions:
//
//Use a sentinel value (e.g., "__DELETED__") to represent explicit deletes within a transaction scope, so that get() stops searching when it hits a delete marker
//commit() must merge, not replace — iterate over the top scope and put each entry into the parent
//
//Extensions:
//
//count(value) — count keys with a given value across all scopes
//keys() — return all active keys (merge all scopes, exclude deleted)
//Concurrent transaction isolation (MVCC)

import java.util.*;

public class InMemoryDBWithTransactions {

    public static final String DELETED_MARKER = "__DELETED__";
    Deque<Map<String, String>> transactions = new ArrayDeque<>();

    // O(1)
    private void set(String key, String value) {
        var mostRecentTransaction = transactions.peek();
        if (mostRecentTransaction == null) {
            // no transactions, start pushing to new one
            var transaction = new HashMap<String, String>();
            transaction.put(key, value);
            transactions.push(transaction);
        } else {
            mostRecentTransaction.put(key, value);
        }
    }

    // O(n * k)
    private int count(String value) {
        int count = 0;
        for (var transaction : transactions) {
            for (var storedValue : transaction.values()) {
                if (storedValue.equals(value))
                    count++;
            }
        }
        return count;
    }

    private List<String> keys() {
        List<String> keys = new ArrayList<>();
        for (var transaction : transactions) {
            for (var entry : transaction.entrySet()) {
                if (entry.getValue().equals(DELETED_MARKER))
                    keys.add(entry.getKey());
            }
        }
        return keys;
    }

    // O(n) worst case, need to check every transaction to see if key is present
    private String get(String key) {
        var it = transactions.iterator();
        while (it.hasNext()) {
            var transaction = it.next();
            if (transaction == null)
                return null;
            if (transaction.get(key) != null) {
                if (!transaction.get(key).equals(DELETED_MARKER))
                    return transaction.get(key);
                else
                    return null;
            }
        }
        return null;
    }

    private void delete(String key) {
        // use an explicit DELETE marker so that when we merge commits, we remove the underlying keys
        // This is needed because if we just remove the key from the topmost transaction and then later
        // we do a commit. when we try to merge this back with the existing higher scopes, Empty keys will just
        // be skipped over while if we have a dedicated marker we can use that to remove keys from outside scopes as well.
        var recentTransaction = transactions.peek();
        if (recentTransaction == null) return;
        recentTransaction.put(key, DELETED_MARKER);
    }

    private void begin() {
        transactions.push(new HashMap<>());
    }

    // O(k) -> k number of key-values in most recent transaction
    private void commit() {
        var recentTransaction = transactions.poll();
        if (recentTransaction == null)
            return;
        var nextAvailableTransaction = transactions.peek();

        if (nextAvailableTransaction == null) {
            // No outside scope, just need to write this as permanent
            // sanitize deleted keys and then store
            for (var entry : recentTransaction.entrySet())
                if (entry.getValue().equals(DELETED_MARKER))
                    recentTransaction.remove(entry.getKey());
            transactions.push(recentTransaction);
        } else {
            for (var entry : recentTransaction.entrySet())
                if (!entry.getValue().equals(DELETED_MARKER))
                    nextAvailableTransaction.putAll(recentTransaction);
                else
                    nextAvailableTransaction.remove(entry.getKey());
        }
    }

    private void rollback() {
        // safe op even if transactions are empty
        transactions.poll();
    }

    // ------------------------------------------------------------------
    // Test harness – mimics the style used in InMemorySQLDatabase
    // ------------------------------------------------------------------

    @FunctionalInterface
    private interface TestCase {
        void run();
    }

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        runTest("basic set/get", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("a", "1");
            assertEquals("1", db.get("a"), "set/get");
        });

        runTest("delete removes key", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("b", "2");
            db.delete("b");
            assertNull(db.get("b"), "delete");
        });

        runTest("transaction commit", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("x", "10");
            db.begin();
            db.set("x", "20");
            assertEquals("20", db.get("x"), "within tx");
            db.commit();
            assertEquals("20", db.get("x"), "after commit");
        });

        runTest("transaction rollback", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("y", "30");
            db.begin();
            db.set("y", "40");
            assertEquals("40", db.get("y"), "within tx");
            db.rollback();
            assertEquals("30", db.get("y"), "after rollback");
        });

        runTest("nested transactions", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("z", "5");
            System.out.println(db.transactions);
            db.begin();          // T1
            db.set("z", "6");
            System.out.println(db.transactions);
            db.begin();          // T2
            System.out.println(db.transactions);
            db.delete("z");
            System.out.println(db.transactions);
            System.out.println(db.get("z"));
            assertNull(db.get("z"), "inside nested delete");
            db.rollback();       // rollback T2
            assertEquals("6", db.get("z"), "after inner rollback");
            db.commit();         // commit T1
            assertEquals("6", db.get("z"), "after outer commit");
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
            throw new AssertionError(message + " – expected: "
                    + expected + ", actual: " + actual);
        }
    }

    private static void assertNull(Object obj, String message) {
        if (obj != null) throw new AssertionError(message + " – expected null");
    }
}
