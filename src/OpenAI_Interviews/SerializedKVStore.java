package OpenAI_Interviews;

import java.util.*;

// Problem Statement: Serializable Key-Value Store
//Overview
//
//Implement an in-memory key-value store for string keys and string values.
//Task
//
//Your class should support:
//
//    reading the value for a key
//    inserting or overwriting a key-value pair
//    deleting a key
//    exporting the full store to a JSON string
//    restoring the store from a JSON string
//    returning all keys in sorted order
//
//If a key is missing, the read operation should return None / null depending on the language. Deleting a missing key should report failure.
//Constraints
//
//    Keys are strings.
//    Values are strings.
//    The store starts empty.
//    keys() must return keys in sorted order.
//    serialize() must produce valid JSON representing the full current store.
//    deserialize(data) replaces the current in-memory contents with the data from the provided JSON string.
//
//Example
//
//store = KVStore()
//store.set("theme", "dark")
//store.set("region", "eu")
//store.get("theme")        -> "dark"
//store.get("missing")      -> null
//store.keys()               -> ["region", "theme"]
//store.serialize()          -> a valid JSON string containing both pairs
//store.delete("theme")     -> true
//store.delete("theme")     -> false

public class SerializedKVStore {
    // Since we've already worked on a couple of KV store problems, the focus of this particular one
    // is purely going to be on the serialization and DC realization piece. we are just going to depend on the get, delete and
    // put properties of the underlying map. instead of trying to implement them from scratch.
    TreeMap<String, String> sortedMap = new TreeMap<>();

    // O(log n) - still needs search, no hashing function
    public String get(String key) {
        return sortedMap.get(key);
    }

    // O(log n) - treemap can binary search to find slot, then rebalance in log n as well
    public void set(String key, String value) {
        sortedMap.put(key, value);
    }

    // O(log n)
    public boolean delete(String key) {
        return sortedMap.remove(key) != null;
    }

    public String serialize() {
        // So for this particular problem, since it's a key value map of string and string, it's always going to result
        // in JSON that is one level deep. So, both serialization and deserialization are somewhat reasonably bounded.
        // The complexity would increase if we were to support nested objects or structures in which case we would ideally
        // want to use a centralized well-tested library instead of rolling out our own implementation.
        // If we had to roll out our own implementation for a nested JSON structure, we would definitely need some level of recursion to make sure we capture all levels accurately.
        // Let's start with a simple linear scan and then we'll go from there.
        StringBuilder sb = new StringBuilder();
        // build outside structure first
        sb.append("{");
        var lastEntry = sortedMap.lastEntry();
        for (var entry : sortedMap.entrySet()) {
            // each key and value should be quoted, and represented by a `:` for assignment
            sb.append("\"").append(escapeJson(entry.getKey())).append("\"");
            sb.append(": ");
            sb.append("\"").append(escapeJson(entry.getValue())).append("\"");
            // trailing comma is not valid JSON
            if (!entry.equals(lastEntry)) {
                sb.append(",");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    private String escapeJson(String data) {
        return data.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }

    private String deEscapeJson(String data) {
        return data.replace("\\\\", "\\")
                .replace("\\\"", "\"");
    }

    public void deserialize(String data) {
        // We have here a regular JSON blob with only one level of nesting. The key constraint here is that we need to
        // **replace** the current in-memory contents with the data from the provided string.

        // parse boundaries first - this is very brittle and can break with invalid JSON.
        // A better idea would be to build our JSON parsing with a tokenizer + lexer.
        var trimmedData = data.trim();
        var startIndex = trimmedData.indexOf('{');
        var substringToParse = trimmedData.substring(startIndex);

        // The important items to parse out would be quotes, comma, colon, backslashes. because these hold special meanings in JSON.
        // Sample JSON: {"address":"New York, NY","time":"12:30:45","quote":"he said \"hello\"","path":"C:\\Users\\test","my:key":"colon:in:value","my,key":"comma,in,value","empty":"","normal":"plain value","nested_escape":"tab\there\nnewline","unicode":"caf\u00e9"}
        int i = 0;
        boolean isKey = true;
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        System.out.println("trimmed data: " + trimmedData);
        while (i < substringToParse.length()) {
            if (substringToParse.charAt(i) == '"') {
                // if previous char was escapeChar, this should be skipped as well
                var prevChar = substringToParse.charAt(i - 1);
                var start = ++i;
                while (i < substringToParse.length() && (substringToParse.charAt(i) != '\"' || prevChar == '\\')) {
                    ++i;
                    prevChar = substringToParse.charAt(i - 1);
                }
                String extracted = substringToParse.substring(start, i);
                if (isKey) {
                    keys.add(deEscapeJson(extracted));
                } else {
                    values.add(deEscapeJson(extracted));
                }
                isKey = !isKey;
            }
            // skip all other characters
            ++i;
        }

        TreeMap<String, String> tempMap = new TreeMap<>();
        if (keys.size() != values.size())
            throw new Error("rip");
        for (int j = 0; j < keys.size(); j++) {
            tempMap.put(keys.get(j), values.get(j));
        }
        sortedMap = tempMap;
    }

    private String getKey(String entry) {
        return entry.split(":")[0].replace("\"", "");
    }

    private String getValue(String entry) {
        return entry.split(":")[1].replace("\"", "");
    }

    // O(1) - Treemap keeps things sorted
    public List<String> keys() {
        return sortedMap.keySet().stream().toList();
    }

    public static void main(String[] args) {
        testEmptyStore();
        testPromptExample();
        testSetGetOverwriteAndSortedKeys();
        testDelete();
        testSerializeProducesJsonForFullStore();
        testDeserializeFromJson();
        testDeserializeReplacesExistingData();
        testEmptyStoreRoundTrip();
        testValueContainingComma();
        testValueContainingColon();
        testValueContainingQuotes();
        testKeyContainingSpecialCharacters();
        testSingleEntry();
        testEmptyStringValue();
        testLargeRoundTrip();

        System.out.println("All SerializedKVStore tests passed!");
    }

    // === Edge Case Tests ===

    private static void testEmptyStoreRoundTrip() {
        SerializedKVStore store = new SerializedKVStore();
        String serialized = store.serialize();
        // Should produce "{}" or "{ }"

        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);
        assertKeys(copy.keys(), List.of(), "empty store should survive serialize/deserialize round trip");
        assertNull(copy.get("anything"), "empty deserialized store should return null for any key");
    }

    private static void testValueContainingComma() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("address", "New York, NY");
        store.set("name", "Alice");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("New York, NY", copy.get("address"), "values containing commas should survive round trip");
        assertEquals("Alice", copy.get("name"), "other entries should be unaffected by comma in sibling value");
    }

    private static void testValueContainingColon() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("time", "12:30:45");
        store.set("label", "key:value");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("12:30:45", copy.get("time"), "values containing colons should survive round trip");
        assertEquals("key:value", copy.get("label"), "values containing colons should not confuse key-value splitting");
    }

    private static void testValueContainingQuotes() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("quote", "he said \"hello\"");
        store.set("path", "C:\\Users\\test");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("he said \"hello\"", copy.get("quote"), "values containing escaped quotes should survive round trip");
        assertEquals("C:\\Users\\test", copy.get("path"), "values containing backslashes should survive round trip");
    }

    private static void testKeyContainingSpecialCharacters() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("my:key", "value1");
        store.set("my,key", "value2");
        store.set("my\"key", "value3");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("value1", copy.get("my:key"), "keys containing colons should survive round trip");
        assertEquals("value2", copy.get("my,key"), "keys containing commas should survive round trip");
        assertEquals("value3", copy.get("my\"key"), "keys containing quotes should survive round trip");
    }

    private static void testSingleEntry() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("only", "one");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("one", copy.get("only"), "single entry store should survive round trip");
        assertKeys(copy.keys(), List.of("only"), "single entry store should have exactly one key");
    }

    private static void testEmptyStringValue() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("empty", "");
        store.set("normal", "value");

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        assertEquals("", copy.get("empty"), "empty string values should survive round trip");
        assertEquals("value", copy.get("normal"), "normal values should be unaffected by empty sibling");
    }

    private static void testLargeRoundTrip() {
        SerializedKVStore store = new SerializedKVStore();
        for (int i = 0; i < 100; i++) {
            store.set("key" + i, "value" + i);
        }

        String serialized = store.serialize();
        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(serialized);

        for (int i = 0; i < 100; i++) {
            assertEquals("value" + i, copy.get("key" + i), "entry " + i + " should survive round trip");
        }
        assertEquals(100, copy.keys().size(), "all 100 entries should survive round trip");
    }

    private static void testEmptyStore() {
        SerializedKVStore store = new SerializedKVStore();

        assertNull(store.get("missing"), "missing keys should return null");
        assertFalse(store.delete("missing"), "deleting a missing key should return false");
        assertKeys(store.keys(), List.of(), "new store should have no keys - keys() should return an empty list, not null");

        SerializedKVStore copy = new SerializedKVStore();
        copy.deserialize(store.serialize());
        assertKeys(copy.keys(), List.of(), "empty store should survive serialize/deserialize");
        assertNull(copy.get("missing"), "missing key should still be missing after round trip");
    }

    private static void testPromptExample() {
        SerializedKVStore store = new SerializedKVStore();

        store.set("theme", "dark");
        store.set("region", "eu");

        assertEquals("dark", store.get("theme"), "prompt example: get should return inserted value");
        assertNull(store.get("missing"), "prompt example: missing key should return null");
        assertKeys(store.keys(), List.of("region", "theme"), "prompt example: keys should be sorted");
        assertTrue(store.delete("theme"), "prompt example: first delete should return true");
        assertFalse(store.delete("theme"), "prompt example: second delete should return false");
    }

    private static void testSetGetOverwriteAndSortedKeys() {
        SerializedKVStore store = new SerializedKVStore();

        store.set("delta", "four");
        store.set("alpha", "one");
        store.set("charlie", "three");
        store.set("beta", "two");

        assertEquals("one", store.get("alpha"), "get should return the inserted value");
        assertEquals("two", store.get("beta"), "get should return the inserted value for a second key");
        assertNull(store.get("missing"), "unknown key should return null");
        assertKeys(store.keys(), List.of("alpha", "beta", "charlie", "delta"), "keys should be returned in sorted order");

        store.set("alpha", "updated");
        assertEquals("updated", store.get("alpha"), "set should overwrite an existing key");
        assertKeys(store.keys(), List.of("alpha", "beta", "charlie", "delta"), "overwriting should not duplicate keys");
    }

    private static void testDelete() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("alpha", "one");
        store.set("beta", "two");

        assertTrue(store.delete("alpha"), "delete should return true for an existing key");
        assertNull(store.get("alpha"), "deleted key should no longer be retrievable");
        assertEquals("two", store.get("beta"), "deleting one key should not affect another key");
        assertKeys(store.keys(), List.of("beta"), "deleted keys should be removed from keys()");

        assertFalse(store.delete("alpha"), "deleting the same key again should return false");
    }

    private static void testSerializeProducesJsonForFullStore() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("theme", "dark");
        store.set("region", "eu");

        String serialized = store.serialize();
        System.out.println(serialized);
        Map<String, String> parsed = parseJsonObject(serialized);
        System.out.println(parsed);

        assertEquals(2, parsed.size(), "serialize should include the full current store");
        assertEquals("dark", parsed.get("theme"), "serialize should include theme");
        assertEquals("eu", parsed.get("region"), "serialize should include region");
    }

    private static void testDeserializeFromJson() {
        SerializedKVStore store = new SerializedKVStore();

        store.deserialize("{\"theme\":\"dark\",\"region\":\"eu\"}");
        System.out.println(store.sortedMap);

        assertEquals("dark", store.get("theme"), "deserialize should restore theme");
        assertEquals("eu", store.get("region"), "deserialize should restore region");
        assertNull(store.get("missing"), "missing key should still return null after deserialize");
        assertKeys(store.keys(), List.of("region", "theme"), "deserialize should restore sorted keys");
    }

    private static void testDeserializeReplacesExistingData() {
        SerializedKVStore store = new SerializedKVStore();
        store.set("stale", "old");
        store.deserialize("{\"fresh\":\"data\"}");

        assertEquals("data", store.get("fresh"), "deserialize should load serialized data");
        assertNull(store.get("stale"), "deserialize should replace existing data, not merge with it");
        assertKeys(store.keys(), List.of("fresh"), "keys should match the deserialized data only");
    }

    private static void assertKeys(List<String> actual, List<String> expected, String message) {
        if (actual == null) {
            throw new AssertionError(message);
        }
        assertEquals(expected, actual, message);
    }

    private static Map<String, String> parseJsonObject(String json) {
        assertNotNull(json, "serialize should not return null");
        JsonObjectParser parser = new JsonObjectParser(json);
        return parser.parse();
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + " - expected <" + expected + "> but got <" + actual + ">");
        }
    }

    private static void assertNull(Object actual, String message) {
        if (actual != null) {
            throw new AssertionError(message + " - expected <null> but got <" + actual + ">");
        }
    }

    private static void assertNotNull(Object actual, String message) {
        if (actual == null) {
            throw new AssertionError(message + " - expected non-null value");
        }
    }

    private static void assertTrue(boolean actual, String message) {
        if (!actual) {
            throw new AssertionError(message + " - expected <true> but got <false>");
        }
    }

    private static void assertFalse(boolean actual, String message) {
        if (actual) {
            throw new AssertionError(message + " - expected <false> but got <true>");
        }
    }

    private static class JsonObjectParser {
        private final String json;
        private int index;

        JsonObjectParser(String json) {
            this.json = json;
        }

        Map<String, String> parse() {
            Map<String, String> result = new HashMap<>();
            skipWhitespace();
            expect('{');
            skipWhitespace();
            if (peek('}')) {
                index++;
                skipTrailingWhitespace();
                return result;
            }

            while (true) {
                String key = parseString();
                skipWhitespace();
                expect(':');
                skipWhitespace();
                String value = parseString();
                result.put(key, value);
                skipWhitespace();

                if (peek('}')) {
                    index++;
                    skipTrailingWhitespace();
                    return result;
                }
                expect(',');
                skipWhitespace();
            }
        }

        private String parseString() {
            expect('"');
            StringBuilder value = new StringBuilder();
            while (index < json.length()) {
                char current = json.charAt(index++);
                if (current == '"') {
                    return value.toString();
                }
                if (current == '\\') {
                    if (index >= json.length()) {
                        throw new AssertionError("serialize should produce valid JSON - dangling escape");
                    }
                    char escaped = json.charAt(index++);
                    switch (escaped) {
                        case '"':
                        case '\\':
                        case '/':
                            value.append(escaped);
                            break;
                        case 'b':
                            value.append('\b');
                            break;
                        case 'f':
                            value.append('\f');
                            break;
                        case 'n':
                            value.append('\n');
                            break;
                        case 'r':
                            value.append('\r');
                            break;
                        case 't':
                            value.append('\t');
                            break;
                        case 'u':
                            value.append(parseUnicodeEscape());
                            break;
                        default:
                            throw new AssertionError("serialize should produce valid JSON - invalid escape \\" + escaped);
                    }
                } else {
                    value.append(current);
                }
            }
            throw new AssertionError("serialize should produce valid JSON - unterminated string");
        }

        private char parseUnicodeEscape() {
            if (index + 4 > json.length()) {
                throw new AssertionError("serialize should produce valid JSON - incomplete unicode escape");
            }
            String hex = json.substring(index, index + 4);
            index += 4;
            try {
                return (char) Integer.parseInt(hex, 16);
            } catch (NumberFormatException e) {
                throw new AssertionError("serialize should produce valid JSON - invalid unicode escape " + hex);
            }
        }

        private void skipWhitespace() {
            while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
                index++;
            }
        }

        private void skipTrailingWhitespace() {
            skipWhitespace();
            if (index != json.length()) {
                throw new AssertionError("serialize should produce valid JSON - trailing content after object");
            }
        }

        private boolean peek(char expected) {
            return index < json.length() && json.charAt(index) == expected;
        }

        private void expect(char expected) {
            if (!peek(expected)) {
                String actual = index < json.length() ? Character.toString(json.charAt(index)) : "end of input";
                throw new AssertionError("serialize should produce valid JSON - expected <" + expected + "> but got <" + actual + ">");
            }
            index++;
        }
    }
}
