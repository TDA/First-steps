package OpenAI_Interviews;

// Implement an LRUCache that supports O(1) average-time get(key) returning the value or -1 and
// O(1) put(key, value) which inserts or updates a key and, if capacity is exceeded, evicts the
// least-recently-used key.
// The core challenge is maintaining key-value storage together with recency ordering to enable
// constant-time access, updates, and eviction.

import java.util.*;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertNull;
import static OpenAI_Interviews.TestHelpers.runTest;

// LRUCache based on least recently used - maintain a lastUser for that
class CacheObject {
    String key;
    String value;
    long lastUsed;
    CacheObject previous;
    CacheObject next;

    public CacheObject(String key, String value, long lastUsed) {
        this.key = key;
        this.value = value;
        this.lastUsed = lastUsed;
    }

    public String getKey() {
        return key;
    }

    String getValue() {
        return this.value;
    }

    public void setLastUsed(long l) {
        this.lastUsed = l;
    }

    @Override
    public String toString() {
        return "[cacheObject] key=" + key + ", value = " + value + ", lastUsed =" + lastUsed;
    }

    public void setValue(String value) {
        this.value = value;
    }
    // These are going to be helpful methods to simplify the edge case's handling.
    public void remove() {
        this.previous.next = this.next;
        this.next.previous = this.previous;
    }

    public void addToEnd(CacheObject TAIL) {
        var prev = TAIL.previous;

        prev.next = this;
        this.previous = prev;
        this.next = TAIL;
        TAIL.previous = this;
    }
}

// The idea is that we keep a linear record of every put insertion and get operation.
// whenever there is a new insertion or a get operation, we bump the lastUsed of that object
// and put it in the linear record. the list will keep expanding building till capacity is hit.
// and every time we hit capacity, we need to remove the earliest item in that queue because it is
// monotonically increasing in terms of operations.
public class LRUCache {
    Map<String, CacheObject> cacheEntries = new HashMap<>();
    CacheObject DUMMY_HEAD;
    CacheObject DUMMY_TAIL;
    int capacity;
    long internalClock;

    public LRUCache(int capacity) {
        this.capacity = capacity;
         DUMMY_HEAD = new CacheObject("DUMMY_HEAD", null, 0);
         DUMMY_TAIL = new CacheObject("DUMMY_TAIL", null, 0);
         DUMMY_TAIL.previous = DUMMY_HEAD;
         DUMMY_TAIL.next = null;
         DUMMY_HEAD.next = DUMMY_TAIL;
         DUMMY_HEAD.previous = null;
    }

    String get (String key) {
        CacheObject cacheObject = cacheEntries.get(key);
        if (cacheObject == null) return null;
        // refresh lastUsed
        cacheObject.setLastUsed(internalClock++);

        // Refresh - remove from current spot and move to end
        cacheObject.remove();
        cacheObject.addToEnd(DUMMY_TAIL);

        cacheEntries.put(cacheObject.getKey(), cacheObject);
        return cacheObject.getValue();
    }

    void put (String key, String value) {
        CacheObject c = cacheEntries.get(key);
        if (c == null) {
            // new object
            c = new CacheObject(key, value, internalClock++);
        } else {
            // existing item, just update values and remove from list - we will add it back
            c.setValue(value);
            c.setLastUsed(internalClock++);
            c.remove();
        }
        c.addToEnd(DUMMY_TAIL);
        cacheEntries.put(key, c);

        if (cacheEntries.size() > capacity) {
            // update links
            cacheEntries.remove(DUMMY_HEAD.next.getKey());
            DUMMY_HEAD.next.remove();
        }
    }

    public static void main(String[] args) {
        runTest("get returns null for missing key", () -> {
            LRUCache cache = new LRUCache(2);

            assertNull(cache.get("missing"), "missing key should return null");
        });

        runTest("put inserts values and get returns them", () -> {
            LRUCache cache = new LRUCache(2);

            cache.put("a", "1");
            cache.put("b", "2");

            assertEquals("1", cache.get("a"), "cache should return inserted value for a");
            assertEquals("2", cache.get("b"), "cache should return inserted value for b");
        });

        runTest("put evicts least recently used key when capacity is exceeded", () -> {
            LRUCache cache = new LRUCache(2);

            cache.put("a", "1");
            cache.put("b", "2");
            cache.put("c", "3");

            assertNull(cache.get("a"), "a should be evicted as least recently used");
            assertEquals("2", cache.get("b"), "b should remain in cache");
            assertEquals("3", cache.get("c"), "c should remain in cache");
        });

        runTest("get refreshes recency", () -> {
            LRUCache cache = new LRUCache(2);

            cache.put("a", "1");
            cache.put("b", "2");
            assertEquals("1", cache.get("a"), "getting a should refresh it");
            cache.put("c", "3");

            assertEquals("1", cache.get("a"), "a should remain because get refreshed recency");
            assertNull(cache.get("b"), "b should be evicted after a was refreshed");
            assertEquals("3", cache.get("c"), "c should remain in cache");
        });

        runTest("updating existing key refreshes recency and value", () -> {
            LRUCache cache = new LRUCache(2);

            cache.put("a", "1");
            cache.put("b", "2");
            cache.put("a", "10");
            cache.put("c", "3");

            assertEquals("10", cache.get("a"), "updated key should return new value");
            assertNull(cache.get("b"), "b should be evicted because updating a refreshed a");
            assertEquals("3", cache.get("c"), "c should remain in cache");
        });

        runTest("capacity one keeps only most recent key", () -> {
            LRUCache cache = new LRUCache(1);

            cache.put("a", "1");
            cache.put("b", "2");

            assertNull(cache.get("a"), "capacity one cache should evict old key");
            assertEquals("2", cache.get("b"), "capacity one cache should keep newest key");
        });
    }
}
