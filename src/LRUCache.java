import java.util.*;

public class LRUCache {
    private int mostRecentPriority;

    class CacheObject {
        int key;
        int value;
        int priority;

        public CacheObject(int key, int value, int priority) {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "CacheObject{" +
                    "value=" + value +
                    ", priority=" + priority +
                    '}';
        }
    }
//    Queue<CacheObject> priorityQueue;
    Map<Integer, CacheObject> cacheObjectMap;
    Map<Integer, Set<Integer>> priorityMapKeys;
    int totalCapacity;
    public LRUCache(int capacity) {
        cacheObjectMap = new HashMap<>(capacity);
        priorityMapKeys = new LinkedHashMap<>();
        totalCapacity = capacity;
    }

    public int get(int key) {
        CacheObject cacheObject = cacheObjectMap.getOrDefault(key, null);
        if (cacheObject != null) {
            // update the priority maps for current and future priority
            int currentPriority = cacheObject.priority;
            Set<Integer> keysAtCurrentLevel = priorityMapKeys.getOrDefault(currentPriority, new LinkedHashSet<>());
            keysAtCurrentLevel.remove(cacheObject.key);
            if (keysAtCurrentLevel.isEmpty()) {
                priorityMapKeys.remove(currentPriority);
            } else {
                priorityMapKeys.put(currentPriority, keysAtCurrentLevel);
            }

            // bump priority
            mostRecentPriority = Math.max(cacheObject.priority + 1, mostRecentPriority);

            // update prioritymap
            cacheObject.priority = mostRecentPriority;
            bumpPriorityOfItem(cacheObject);

            return cacheObject.value;
        }
        return -1;
    }

    private void bumpPriorityOfItem(CacheObject cacheObject) {
        Set<Integer> keysAtNewLevel = priorityMapKeys.getOrDefault(mostRecentPriority, new LinkedHashSet<>());
        keysAtNewLevel.add(cacheObject.key);
        priorityMapKeys.put(mostRecentPriority, keysAtNewLevel);
    }

    public void put(int key, int value) {
        // needs to check for capacity and then remove oldest
        if (cacheObjectMap.size() == totalCapacity && !cacheObjectMap.containsKey(key)) {
            // at capacity, remove one to add one.
            // get oldest accessed item
            Optional<Integer> oldestKey = priorityMapKeys.keySet().stream().findFirst();
            if (oldestKey.isPresent()) {
                Integer oldKey = oldestKey.get();
                Set<Integer> oldKeys = priorityMapKeys.get(oldKey);
                Integer keyToRemove = oldKeys.iterator().next();
                oldKeys.remove(keyToRemove);
                if (oldKeys.isEmpty()) priorityMapKeys.remove(oldKey);
                cacheObjectMap.remove(keyToRemove);
            }
        }
        if (cacheObjectMap.containsKey(key)) {
            // force a get to bump priority
            get(key);
        }

        CacheObject updatedObject = new CacheObject(key, value, mostRecentPriority);
        cacheObjectMap.put(key, updatedObject);
        bumpPriorityOfItem(updatedObject);
    }

    public static void main(String[] args) {
//        LRUCache lruCache = new LRUCache(2);
        LRUCache lruCache = new LRUCache(10);
        lruCache.put(10,13);
        lruCache.put(3,17);
        lruCache.put(6,11);
        lruCache.put(10,5);
        lruCache.put(9,10);
        System.out.println("lruCache.get(13) : " + lruCache.get(13));
        lruCache.put(2,19);
        System.out.println("lruCache.get(2) : " + lruCache.get(2));
        System.out.println("lruCache.get(3) : " + lruCache.get(3));
        lruCache.put(5,25);
        System.out.println("lruCache.get(8) : " + lruCache.get(8));
        lruCache.put(9,22);
        lruCache.put(5,5);
        lruCache.put(1,30);
        System.out.println("lruCache.get(11) : " + lruCache.get(11));
        lruCache.put(9,12);
        System.out.println("lruCache.get(7) : " + lruCache.get(7));
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        System.out.println("lruCache.get(8) : " + lruCache.get(8));
        System.out.println("lruCache.get(9) : " + lruCache.get(9));
        lruCache.put(4,30);
        lruCache.put(9,3);
        System.out.println("lruCache.get(9) : " + lruCache.get(9));
        System.out.println("lruCache.get(10) : " + lruCache.get(10));
        System.out.println("lruCache.get(10) : " + lruCache.get(10));
        lruCache.put(6,14);
        lruCache.put(3,1);
        System.out.println("lruCache.get(3) : " + lruCache.get(3));
        lruCache.put(10,11);
        System.out.println("lruCache.get(8) : " + lruCache.get(8));
        lruCache.put(2,14);
        System.out.println("lruCache.get(1) : " + lruCache.get(1));
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        System.out.println("lruCache.get(4) : " + lruCache.get(4));
        lruCache.put(11,4);
        lruCache.put(12,24);
        lruCache.put(5,18);
        System.out.println("lruCache.get(13) : " + lruCache.get(13));
        lruCache.put(7,23);
        System.out.println("lruCache.get(8) : " + lruCache.get(8));
        System.out.println("lruCache.get(12) : " + lruCache.get(12));
        lruCache.put(3,27);
        lruCache.put(2,12);
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        lruCache.put(2,9);
        lruCache.put(13,4);
        lruCache.put(8,18);
        lruCache.put(1,7);
        System.out.println("lruCache.get(6) : " + lruCache.get(6));
        lruCache.put(9,29);
        lruCache.put(8,21);
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        lruCache.put(6,30);
        lruCache.put(1,12);
        System.out.println("lruCache.get(10) : " + lruCache.get(10));
        lruCache.put(4,15);
        lruCache.put(7,22);
        lruCache.put(11,26);
        lruCache.put(8,17);
        lruCache.put(9,29);
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        lruCache.put(3,4);
        lruCache.put(11,30);
        System.out.println("lruCache.get(12) : " + lruCache.get(12));
        lruCache.put(4,29);
        System.out.println("lruCache.get(3) : " + lruCache.get(3));
        System.out.println("lruCache.get(9) : " + lruCache.get(9));
        System.out.println("lruCache.get(6) : " + lruCache.get(6));
        lruCache.put(3,4);
        System.out.println("lruCache.get(1) : " + lruCache.get(1));
        System.out.println("lruCache.get(10) : " + lruCache.get(10));
        lruCache.put(3,29);
        lruCache.put(10,28);
        lruCache.put(1,20);
        lruCache.put(11,13);
        System.out.println("lruCache.get(3) : " + lruCache.get(3));
        lruCache.put(3,12);
        lruCache.put(3,8);
        lruCache.put(10,9);
        lruCache.put(3,26);
        System.out.println("lruCache.get(8) : " + lruCache.get(8));
        System.out.println("lruCache.get(7) : " + lruCache.get(7));
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        lruCache.put(13,17);
        lruCache.put(2,27);
        lruCache.put(11,15);
        System.out.println("lruCache.get(12) : " + lruCache.get(12));
        lruCache.put(9,19);
        lruCache.put(2,15);
        lruCache.put(3,16);
        System.out.println("lruCache.get(1) : " + lruCache.get(1));
        lruCache.put(12,17);
        lruCache.put(9,1);
        lruCache.put(6,19);
        System.out.println("lruCache.get(4) : " + lruCache.get(4));
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        System.out.println("lruCache.get(5) : " + lruCache.get(5));
        lruCache.put(8,1);
        lruCache.put(11,7);
        lruCache.put(5,2);
        lruCache.put(9,28);
        System.out.println("lruCache.get(1) : " + lruCache.get(1));
        lruCache.put(2,2);
        lruCache.put(7,4);
        lruCache.put(4,22);
        lruCache.put(7,24);
        lruCache.put(9,26);
        lruCache.put(13,28);
        lruCache.put(11,26);

    }
}