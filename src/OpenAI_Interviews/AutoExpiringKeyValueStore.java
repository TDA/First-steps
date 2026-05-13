package OpenAI_Interviews;

// Key-Value Store with TTL
//Frequency: Most reported OpenAI coding question.
//
//Core requirements:
//
//put(key, value) — store a key-value pair
//put(key, value, ttl) — store with time-to-live in seconds
//get(key) — return value if exists and not expired, else null
//delete(key) — remove a key
//
//
//Java-specific notes: Use System.currentTimeMillis() or Instant.now().toEpochMilli() for timestamps.
// Use a record for Entry: record Entry(String key, String value, long expiryMs) {}.
//
// Target time: Core in 25-30 min. Extension in 15-20 min.

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoExpiringKeyValueStore {
    static final long TIME_IN_MILLIS = 1000;

    record ValueEntry(String key, String value, Long creationTime, Long expiryTime) {
    }

    Map<String, ValueEntry> keyValuePairs = new HashMap<>();
    PriorityQueue<ValueEntry> orderedKeyValuePairs = new PriorityQueue<>(
            Comparator.comparing(ValueEntry::expiryTime)
    );

    public String get(String key) {
        ValueEntry entry = keyValuePairs.get(key);
        if (entry != null) {
            if (entry.expiryTime() == null || entry.expiryTime() >= System.currentTimeMillis())
                return entry.value();
        }
        // Soft-delete only, don't touch heap to keep this O(1)
        keyValuePairs.remove(key);
        return null;
    }

    public void put(String key, String value) {
        this.put(key, value, null);
    }

    public void put(String key, String value, Long ttl) {
        long creationTime = System.currentTimeMillis();
        Long expiryTime = null;
        if (ttl != null) {
            expiryTime = creationTime + ttl * TIME_IN_MILLIS;
        }
        // Another optimization here: only add items with an expiry to the heap - Keeps the heap lean and doesn't clutter it with a bunch of null operations. exceptions, saves us from null pointer exceptions, and ensures that we do not need to do additional calculations at all.
        ValueEntry entry = new ValueEntry(key, value, creationTime, expiryTime);
        keyValuePairs.put(key, entry);
        if (expiryTime != null)
            orderedKeyValuePairs.add(entry);

    }

    // Active deletion - this is problematic since its O(n) for the heap
    // Solution - lazy deletion - we dont delete here from heap, but do not re-insert during cleanup. Poll is O(log n) and thats better than O(n)
    public void delete(String key) {
        keyValuePairs.remove(key);
        // We are explicitly not removing this from the heap, and we'll let the cleanup function organically make this happen.
    }

    // Worst case: everything has expired and needs removals - O(n * log n)
    // Best case: no cleanup needed = O(1)
    // This is discussion point with the interviewer - how frequently we want clean up.
    // Obviously, some form of garbage collection is important so that the collection doesn't keep growing exponentially
    // with many different expired items.
    // However, the put, delete and get operations right now are significantly faster than a linear scan, while cleanup is definitely linear.
    // Three things we can suggest trading off:
    // 1. Lazy cleanup - we cleanup when we get a .get() that results in an expired item. This changes get from O(1) -> O(log n) for the heap removal/rebalance.
    // 2. Active cleanup on every put - call cleanup on insert.  This changes put from O(log n) -> O(n) for the heap addition/rebalance.
    // 3. [Recommended] Cleanup is separate thread, running every X seconds - This is likely what we would do in a production setting where cleanup is run in a separate thread or even a separate service that goes and removes items that have expired. This will keep get and put super fast while allowing clean up on a regular basis. We can still soft-delete on get() returning null.
    private void cleanup() {
        var expiredItemsAvailable = !orderedKeyValuePairs.isEmpty();
        while (expiredItemsAvailable) {
            // Another optimization here is that we only do a peak instead of a pole. so that way if there are no heap operations to be done because nothing has expired then this becomes constant time.
            var earliestExpiringItem = orderedKeyValuePairs.peek();
            if (earliestExpiringItem == null) {
                // done - no operation
                expiredItemsAvailable = false;
                continue;
            }
            if (earliestExpiringItem.expiryTime() < System.currentTimeMillis()) {
                // Needs removal
                orderedKeyValuePairs.poll(); // do actual removal
                // Since we allow overwriting new entries with a new TTL, there could be a possibility where the first insertion had a TTL but subsequent insertions removed the TTL. So we should only remove it from key value pairs if it matches the existing item.
                // e.g.
                // 1. put(key, value, TTL = 5)
                // 2. put(key, value) // Reset TTL
                // Due to the way output operation works, the second item would not be inserted into the the heap while the first item would stay on the heap. So in this case we only remove it from the heap, but not from the map.
                keyValuePairs.remove(earliestExpiringItem.key(), earliestExpiringItem);
            } else {
                expiredItemsAvailable = false;
            }
        }
    }

    static void main() {
        AutoExpiringKeyValueStore store = new AutoExpiringKeyValueStore();

        store.put("key1", "no expiry");
        store.put("key2", "expiry in 10s", 10L);
        store.put("key3", "no expiry ever");
        store.put("key4", "expiry in 5s", 5L);

        System.out.println(store.keyValuePairs);
        System.out.println(store.orderedKeyValuePairs);
        System.out.println(System.currentTimeMillis());

        System.out.println("Immediate execution, no expiries yet");
        System.out.println(store.get("key1"));
        System.out.println(store.get("key2"));
        System.out.println(store.get("key3"));
        System.out.println(store.get("key4"));
        store.cleanup();

        try (ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor()) {
            scheduler.schedule(() -> {
                System.out.println("7s delayed execution, some expiries");
                System.out.println(store.get("key1"));
                System.out.println(store.get("key2"));
                System.out.println(store.get("key3"));
                System.out.println(store.get("key4"));

                // Active Delete
                store.delete("key3");

                // Overwrite with new TTL
                store.put("key1", "oops", 3L);

                store.cleanup();
                System.out.println(store.keyValuePairs);
                System.out.println(store.orderedKeyValuePairs);
                System.out.println(System.currentTimeMillis());

            }, 7, TimeUnit.SECONDS);

            scheduler.schedule(() -> {
                System.out.println("12s delayed execution, many expiries");
                System.out.println(store.get("key1"));
                System.out.println(store.get("key2"));
                System.out.println(store.get("key3"));
                System.out.println(store.get("key4"));

                store.cleanup();
                System.out.println(store.keyValuePairs);
                System.out.println(store.orderedKeyValuePairs);
                System.out.println(System.currentTimeMillis());
            }, 12, TimeUnit.SECONDS);

            // Remember to shut down the scheduler when done to allow the JVM to exit
            scheduler.shutdown();
        }
    }
}
