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

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertNull;
import static OpenAI_Interviews.TestHelpers.runTest;

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
        // This requires us to flatten all the transaction layers before figuring out what needs to be counted versus not.
        // Because if a key has been overwritten or deleted in an upper level that also means the underlying ones should
        // not be counted the opposite is also true if a key has been updated to the matching value in an upper level then
        // it should only be counted once (this happens by default)
        int count = 0;

        var flattenedTransaction = getFlattenedTransaction();
        if (flattenedTransaction == null) return 0;

        for (var entryValue : flattenedTransaction.values()) {
            if (entryValue.equals(value))
                count++;
        }

        return count;
    }

    private Map<String, String> getFlattenedTransaction() {
        // Create a copy
        var transactionsCopy = new ArrayDeque<>(transactions);
        var topMostTransaction = transactionsCopy.poll();
        if (topMostTransaction == null) return null;
        while (!transactionsCopy.isEmpty()) {
            var secondTopMostTransaction = transactionsCopy.poll();
            if (secondTopMostTransaction == null) {
                // we are done flattening, exit
                break;
            }
            secondTopMostTransaction.putAll(topMostTransaction);
            topMostTransaction = secondTopMostTransaction;
        }
        return topMostTransaction;
    }

    private List<String> keys() {
        List<String> keys = new ArrayList<>();
        var flattenedTransactions = getFlattenedTransaction();
        if (flattenedTransactions == null) return keys;
        for (var e : flattenedTransactions.entrySet()) {
            if (!e.getValue().equals(DELETED_MARKER))
                keys.add(e.getKey());
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
            recentTransaction.entrySet().removeIf(e -> e.getValue().equals(DELETED_MARKER));
            transactions.push(recentTransaction);
        } else {
            nextAvailableTransaction.putAll(recentTransaction);
            nextAvailableTransaction.entrySet().removeIf(e -> e.getValue().equals(DELETED_MARKER));
        }
    }

    private void rollback() {
        // safe op even if transactions are empty
        transactions.poll();
    }

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
            db.begin();          // T1
            db.set("z", "6");
            db.begin();          // T2
            db.delete("z");
            assertNull(db.get("z"), "inside nested delete");
            db.rollback();       // rollback T2
            assertEquals("6", db.get("z"), "after inner rollback");
            db.commit();         // commit T1
            assertEquals("6", db.get("z"), "after outer commit");
        });

        runTest("count ignores overridden value with different v", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("k", "1");
            db.set("m", "1");
            // top level
            db.begin();                 // T1
            db.set("k", "2");
            db.set("m", "3");// overrides with different value
            assertEquals(0, db.count("1"), "count after override with diff value");
            assertEquals(1, db.count("2"), "count after override with diff value");
            db.delete("k");
            assertEquals(0, db.count("2"), "count after override with diff value");
            assertEquals(0, db.count("1"), "count after override with diff value");
        });

        runTest("count ignores overridden value with same v", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("k", "1");          // top level
            db.begin();                 // T1
            db.set("k", "1");          // overrides with same value
            assertEquals(1, db.count("1"), "count after override with same value");
        });

        // ------------------------------------------------------------------
        // Edge‑case tests for keys()
        // ------------------------------------------------------------------

        runTest("keys excludes deleted key in current scope", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("x", "1");
            db.begin();                     // T1
            db.delete("x");                  // delete in current transaction
            List<String> expected = new ArrayList<>();
            assertEquals(expected, db.keys(), "keys after delete in current");
        });

        runTest("keys excludes key deleted by inner transaction", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("y", "2");
            db.begin();                     // T1
            db.begin();                     // T2
            db.delete("y");                  // delete in inner transaction
            List<String> expected = new ArrayList<>();
            assertEquals(expected, db.keys(), "keys after delete in inner");
        });

        runTest("keys returns key once even if overridden with same value", () -> {
            InMemoryDBWithTransactions db = new InMemoryDBWithTransactions();
            db.set("z", "3");
            db.begin();                     // T1
            db.set("z", "3");                // same value, different scope
            List<String> expected = List.of("z");
            assertEquals(expected, db.keys(), "keys with duplicate key");
        });
    }
}
