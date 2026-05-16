package OpenAI_Interviews;

// Problem Statement
//
//Build an in-memory database that supports a small subset of SQL-like querying over flexible (schema-less) records.
//
//You will support:
//
//    INSERT of flexible records (arbitrary key-value pairs)
//    SELECT with WHERE clauses (AND logic)
//    ORDER BY on any field (ASC/DESC)
//
//Your design should be practical for large inputs. Implement indexing to avoid full scans when possible.
//Interface (implement in your language)
//
//class InMemoryDb {
//    void insert(Map<String, Value> record)
//    List<Map<String, Value>> query(String queryString)
//}
//
//Where:
//
//    A record is a map/dictionary from fieldName -> value.
//    Value may be one of: integers, floats, strings, booleans (choose a reasonable set and be consistent).
//
//Supported Query Format
//INSERT
//
//Example usage:
//
//    insert({id: 1, name: 'Alice', age: 30})
//
//Notes:
//
//    You do not need to parse INSERT ... strings inside query(...).
//    Any INSERT {...} notation is purely illustrative of calling insert(record).
//
//SELECT
//
//Input format example:
//
//SELECT * WHERE age > 25 ORDER BY name ASC
//
//Requirements:
//
//    WHERE supports multiple conditions combined with AND.
//    ORDER BY supports any field with ASC or DESC.
//
//Operators supported in WHERE
//
//    =, !=, <, >, <=, >=
//
//Semantics
//Storage
//
//    Records may have different schemas (not all fields appear in all records).
//    Your system assigns each inserted record an internal identifier (or equivalent) so it can be indexed.
//
//WHERE evaluation
//
//    A record matches a condition if the record has the referenced field and the comparison succeeds.
//    If a record does not have the field referenced in a condition, treat that condition as false for that record.
//
//ORDER BY
//
//    Sort matching records by the given field.
//    If a record is missing the sort field, treat it consistently. For this problem, define: missing sort fields always sort last.
//
//Performance Requirement (Indexing)
//
//You must implement field-based inverted indexes to speed up lookups.
//
//At minimum, efficiently accelerate equality lookups like field = value using an index mapping:
//
//field -> value -> set/list of recordIds
//
//For non-equality comparisons (<, >, <=, >=), you may:
//
//    fall back to scanning candidate records, or
//    use a more advanced index (optional improvement).
//
//Example
//Input
//
//    insert({id: 1, name: 'Alice', age: 30})
//    insert({id: 2, name: 'Bob', age: 25})
//    query("SELECT * WHERE age > 25 ORDER BY name ASC")
//
//Output
//
//    [{id: 1, name: 'Alice', age: 30}]

public class InMemoryNoSQLDatabase {
}
