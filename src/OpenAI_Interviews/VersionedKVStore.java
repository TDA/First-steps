package OpenAI_Interviews;

//Time-based Key–Value Store: Problem Description
//
//You are building a time-based key–value store that supports saving multiple versions of a value for the same key, each tagged with a timestamp.
//
//Design a data structure TimeMap with two operations:
//
//class TimeMap {
//    void set(String key, String value, int timestamp)
//    String get(String key, int timestamp)   // returns null (or language equivalent) when no value exists
//}
//
//This is an illustrative interface, feel free to adapt to your programming language of choice
//Requirements
//set(key, value, timestamp)
//
//Store value for the given key at the provided timestamp.
//A key can be set multiple times with different timestamps, creating a history of values.
//For any single key, the timestamp values you receive in set() calls are strictly increasing.
//
//get(key, timestamp)
//
//Return the value for key whose stored timestamp is the largest timestamp less than or equal to the given timestamp.
//If key has no stored timestamp ≤ the given timestamp, return null (use this convention consistently).
//
//Constraints
//
//Up to 300,000 total set + get calls.
//Keys and values are short strings.
//
//Expected Behaviour
//
//Operations (in order), with expected return value shown inline:
//
//set("exchangeRate", "1.10", 2)   -> returns void
//set("exchangeRate", "1.12", 5)   -> returns void
//get("exchangeRate", 1)           -> returns null
//get("exchangeRate", 4)           -> returns "1.10"
//get("exchangeRate", 5)           -> returns "1.12"
//get("exchangeRate", 9)           -> returns "1.12"
//get("unknownKey", 3)            -> returns null
//
//Explanation
//
//At time 1, exchangeRate hasn’t been set yet → null.
//At time 4, the latest stored time ≤ 4 is 2 → "1.10".
//At time 9, the latest stored time ≤ 9 is 5 → "1.12".
//An unknown key always returns null.

import java.util.*;

record ValueRecord(String value, int timestamp) implements Comparable<ValueRecord> {

    @Override
    public int compareTo(ValueRecord o) {
        return this.timestamp -  o.timestamp;
    }
}

public class VersionedKVStore {

    // My thought process here is that we have two vectors of filtering.
    // one is just a key value lookup which typically some form of map is needed.
    // the other one is actually a sorted order based on timestamps where you need to traverse through a tree style item till you find something that's greater than or equal to the given value you stop at that point
    // Does java have a treemap hybrid that combines a priority queue with a hashmap lol
    // A: It indeed does!!

    // One thing to think about here is, the question actually says, it's already monotonically increasing.
    // "For any single key, the timestamp values you receive in set() calls are strictly increasing." -> which implies that we can just use a regular set here instead of a tree set. however using a tree set would mean that even if you get calls out of order it it will still maintain the overall insertion order based on the comparator that you're setting up.
    Map<String, List<ValueRecord>> map = new HashMap<>();

    void set(String key, String value, int timestamp) {
        // for this effectively we look up the existing key or create a new default reset if one doesn't exist and then We add the new value to the existing set and update the hash map.
        var existingSet = this.map.getOrDefault(key, new ArrayList<>());
        // Possible extension - out of order items, we need a sort here. Or we need to switch to TreeSets, either way insertion is now O(n log n) instead of O(1)
        existingSet.add(new ValueRecord(value, timestamp));
        Collections.sort(existingSet);
        this.map.put(key, existingSet);
        System.out.println(this.map);
    }

    // O (n)
    String get(String key, int timestamp) {
        // returns null (or language equivalent) when no value exists
        // The getter needs a little bit of work here. essentially we need to return the first item whose value you is greater than or equal to an existing timestamp.
        // Since we already have the logic for a comparator, Let's see if we can use it. Otherwise, a simple traversal through the list of records is sufficient. The nice thing here is we are guaranteed that it's in sorted order. so a simple modified binary search is sufficient to look up the value, which would just be log n
        List<ValueRecord> valueRecords = this.map.getOrDefault(key, null);
        if (valueRecords == null) return null;
        // First the O(n) option
        ValueRecord highestRecordBelowGivenTimestamp = null;
        for (ValueRecord record: valueRecords) {
            if (record.timestamp() <= timestamp)
                highestRecordBelowGivenTimestamp = record;
        }

        if (highestRecordBelowGivenTimestamp == null) return null;
        else return highestRecordBelowGivenTimestamp.value();
    }

    String getModifiedBS(String key, int timestamp) {
        List<ValueRecord> valueRecords = this.map.getOrDefault(key, null);
        if (valueRecords == null) return null;
        ValueRecord highestRecordBelowGivenTimestamp = null;
        ValueRecord lookupRecord = new ValueRecord("0", timestamp);

        return modifiedBinarySearch(valueRecords, 0, valueRecords.size() - 1, lookupRecord, highestRecordBelowGivenTimestamp);
    }

    // O (log n)
    private String modifiedBinarySearch(List<ValueRecord> valueRecords, int start, int end, ValueRecord lookupRecord, ValueRecord highestRecordBelowGivenTimestamp) {
        if (start > end) {
            return highestRecordBelowGivenTimestamp != null ? highestRecordBelowGivenTimestamp.value() : null;
        }
        int mid = (start + end ) / 2;
//        System.out.println("mid " + mid);
//        System.out.println("Comparing " + valueRecords.get(mid) + " with " + lookupRecord + " : " + valueRecords.get(mid).compareTo(lookupRecord));
//        System.out.println("Current highest value "  + highestRecordBelowGivenTimestamp);
        if (valueRecords.get(mid).compareTo(lookupRecord) == 0) {
            // found an exact record, we are done
//            System.out.println("found an exact record, we are done: " + valueRecords.get(mid).value());
            return valueRecords.get(mid).value();
        } else if (valueRecords.get(mid).compareTo(lookupRecord) > 0) {
//            System.out.println("went over, dont touch highestRecordBelowGivenTimestamp, go left of binary tree: "  + (start) + " -> " + (mid - 1));
            // went over, dont touch highestRecordBelowGivenTimestamp, go left of binary tree
            return modifiedBinarySearch(valueRecords, start, mid - 1, lookupRecord, highestRecordBelowGivenTimestamp);
        } else {
//            System.out.println("found a record lower or equal than lookupRecord, traverse right of tree: " + (mid+1) + " -> " + end);
            // found a record lower or equal than lookupRecord, traverse right of tree after setting value
            return modifiedBinarySearch(valueRecords, mid + 1, end, lookupRecord, valueRecords.get(mid));
        }
    }

    public static void main() {
        VersionedKVStore store = new VersionedKVStore();
        store.set("exchangeRate", "1.10", 2);   // -> returns void
        store.set("exchangeRate", "1.12", 5);   // -> returns void
        store.set("exchangeRate", "1.14", 7);   // -> returns void
        // Updated test case - send an out of order item
        store.set("exchangeRate", "1.19", 0);   // -> returns void
        System.out.println("Testing O(n)");
        System.out.println(store.get("exchangeRate", 1));           // -> returns 1.19
        System.out.println(store.get("exchangeRate", 4));           // -> returns "1.10"
        System.out.println(store.get("exchangeRate", 5));           // -> returns "1.12"
        System.out.println(store.get("exchangeRate", 9));           // -> returns "1.12"
        System.out.println(store.get("unknownKey", 3));            // -> returns null


        System.out.println("------------------------");
        System.out.println("Testing O(log n)");
        System.out.println(store.getModifiedBS("exchangeRate", 1));           // -> returns 1.19
        System.out.println(store.getModifiedBS("exchangeRate", 4));           // -> returns "1.10"
        System.out.println(store.getModifiedBS("exchangeRate", 5));           // -> returns "1.12"
        System.out.println(store.getModifiedBS("exchangeRate", 9));           // -> returns "1.12"
        System.out.println(store.getModifiedBS("unknownKey", 3));            // -> returns null

    }
}
