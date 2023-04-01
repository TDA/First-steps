import java.util.*;

public class LFUCache {
    class CacheObject {
        int key;
        int value;
        int usedTimes;

        public CacheObject(int key, int value, int usedTimes) {
            this.key = key;
            this.value = value;
            this.usedTimes = usedTimes;
        }

        @Override
        public String toString() {
            return "CacheObject{" +
                    "value=" + value +
                    ", priority=" + usedTimes +
                    '}';
        }
    }
    //    Queue<CacheObject> priorityQueue;
    Map<Integer, CacheObject> cacheObjectMap;
    Map<Integer, Set<Integer>> frequencyMapKeys;
    int totalCapacity;
    public LFUCache(int capacity) {
        cacheObjectMap = new HashMap<>(capacity);
        frequencyMapKeys = new HashMap<>();
        totalCapacity = capacity;
    }

    public int get(int key) {
        CacheObject cacheObject = cacheObjectMap.getOrDefault(key, null);
        if (cacheObject != null) {
            // update the priority maps for current and future priority
            int currentPriority = cacheObject.usedTimes;
            Set<Integer> keysAtCurrentLevel = frequencyMapKeys.getOrDefault(currentPriority, new LinkedHashSet<>());
            keysAtCurrentLevel.remove(cacheObject.key);
            if (keysAtCurrentLevel.isEmpty()) {
                frequencyMapKeys.remove(currentPriority);
            } else {
                frequencyMapKeys.put(currentPriority, keysAtCurrentLevel);
            }

            // update prioritymap
            cacheObject.usedTimes++;
            bumpPriorityOfItem(cacheObject);

            return cacheObject.value;
        }
        return -1;
    }

    private void bumpPriorityOfItem(CacheObject cacheObject) {
        Set<Integer> keysAtNewLevel = frequencyMapKeys.getOrDefault(cacheObject.usedTimes, new LinkedHashSet<>());
        keysAtNewLevel.add(cacheObject.key);
        frequencyMapKeys.put(cacheObject.usedTimes, keysAtNewLevel);
    }

    public void put(int key, int value) {
        // needs to check for capacity and then remove oldest
        if (cacheObjectMap.size() == totalCapacity && !cacheObjectMap.containsKey(key)) {
            // at capacity, remove one to add one.
            // get oldest accessed item
            Integer oldestKey = frequencyMapKeys.keySet().stream().findFirst().get();
            Set<Integer> oldKeys = frequencyMapKeys.get(oldestKey);
            Integer keyToRemove = oldKeys.iterator().next();
            oldKeys.remove(keyToRemove);
            if (oldKeys.isEmpty()) frequencyMapKeys.remove(oldestKey);
            cacheObjectMap.remove(keyToRemove);
        }
        if (cacheObjectMap.containsKey(key)) {
            get(key);
        }

        CacheObject updatedObject = cacheObjectMap.getOrDefault(key, new CacheObject(key, value, 1));
        updatedObject.value = value;
        cacheObjectMap.put(key, updatedObject);
        bumpPriorityOfItem(updatedObject);
    }

    public static void main(String[] args) {
//        LRUCache lfuCache = new LRUCache(2);
        LFUCache lfuCache = new LFUCache(3);
        lfuCache.put(2,2);
        lfuCache.put(1,1);
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(2));
        lfuCache.put(3,3);
        lfuCache.put(4,4);
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(4));

    }
}