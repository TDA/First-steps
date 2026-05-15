package OpenAI_Interviews;

// Design SQL (In-Memory Database)
//Table data modeling, predicate parsing, filtering
//Target Time
//Core 35-40 min, extension 15 min
//
//
//Full Problem: Implement a SQL class that supports basic database operations.
//
//Interface:
//
//createTable(tableName, columns) → void
//
//insertRow(tableName, values) → void
//
//deleteRows(tableName, condition) → int (rows deleted)
//
//select(tableName, columns, condition) → List<List<String>>
//
//Example:
//
//createTable("users", ["id", "name", "age"])
//
//insertRow("users", ["1", "Alice", "30"])
//
//insertRow("users", ["2", "Bob", "25"])
//
//select("users", ["name"], "age > 27") → [["Alice"]]
//
//deleteRows("users", "id = 2") → 1
//
//Core Data Structures:
//
//Map<String, Table> where Table has List<String> columns and List<Map<String, String>> rows
//Condition parser: split on operator (=, >, <, >=, <=, !=), extract column and value, evaluate per row
//
//Key Design Decisions:
//
//All values stored as strings; parse to int/double only for comparison operators
//Condition evaluation: start with single conditions, mention you'd extend to AND/OR with a recursive descent parser
//Column projection in select: filter the output maps to only requested columns
//
//Extensions:
//
//Support JOIN(table1, table2, on_condition) — nested loop join or hash join
//Support ORDER BY column [ASC|DESC]
//Support GROUP BY with aggregate functions (COUNT, SUM, AVG)
//Add indexing (HashMap index on a column for O(1) lookups)
//Add transactions (same pattern as In-Memory Database with Transactions — scope stack)


import java.util.*;
import java.util.regex.Pattern;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.assertRows;
import static OpenAI_Interviews.TestHelpers.assertTrue;
import static OpenAI_Interviews.TestHelpers.runTest;

class Table {
    LinkedHashSet<String> columns;
    List<Map<String, String>> rows = new ArrayList<>();

    public Table(List<String> columns) {
        this.columns = new LinkedHashSet<>(columns);
    }

    public HashSet<String> getColumns() {
        return columns;
    }

    public void addRows(HashMap<String, String> newRow) {
        rows.add(newRow);
    }
}

public class InMemorySQLDatabase {

    Map<String, Table> tables = new HashMap<>();

    public void createTable(String tableName, List<String> columns) {
        var emptyTable = new Table(columns);
        var existingTable = tables.putIfAbsent(tableName, emptyTable);
        if (existingTable != null) {
            System.out.println("Table exists, choose a different name");
        }
    }

    // All values are strings and can be parsed to int/double for comparison operators as per the question.
    public void insertRow(String tableName, List<String> values) {
        var table = tables.get(tableName);
        if (table == null) {
            System.out.println("Invalid query: Table does not exist, choose a different name");
            return;
        }
        var columnNames = table.getColumns();
        if (columnNames.size() != values.size()) {
            System.out.println("Invalid query: Number of values does not match number of columns");
            return;
        }
        // Construct the new row
        var newRow = new HashMap<String, String>();
        var columnNameIterator = columnNames.iterator();
        for (String value : values) {
            newRow.put(columnNameIterator.next(), value);
        }
        table.addRows(newRow);
    }

    public int deleteRows(String tableName, String condition) {
        var table = tables.get(tableName);
        if (table == null) {
            System.out.println("Invalid query: Table does not exist, choose a different name");
            return 0;
        }
        var matchingRows = getMatchingRows(condition, table);
        if (matchingRows == null) {
            return 0;
        }
        table.rows.removeAll(matchingRows);
        return matchingRows.size();
    }

    public List<List<String>> select(String tableName, List<String> columns, String condition) {
        var table = tables.get(tableName);
        if (table == null) {
            System.out.println("Invalid query: Table does not exist, choose a different name");
            return null;
        }
        var columnNames = table.getColumns();
        if (!columnNames.containsAll(columns)) {
            System.out.println("Invalid column names");
            return null;
        }
        var matchingRows = getMatchingRows(condition, table);
        if (matchingRows == null) return null;

        List<List<String>> filteredCellValues = new ArrayList<>();
        for (var row: matchingRows) {
            var cellValues = new ArrayList<String>();
            for (var column: columns) {
                cellValues.add(row.get(column));
            }
            filteredCellValues.add(cellValues);
        }
        return filteredCellValues;
    }

    private List<Map<String, String>> getMatchingRows(String condition, Table table) {
        // Why regex? for a string parsing item like this where only a few combinations are present, it's easier/faster to
        // write a regular expression in an interview setting. However, if this was production, we would obviously go via
        // the route of an actual tokenizer plus capture individual groups with additional rigor rather than writing a
        // regular expression because when you get paged, you don't really want to sit down and parse the regular expression.
        var matcher = Pattern.compile("(\\w+)\\s*(<=|>=|!=|[<>=])\\s*(\\w+)").matcher(condition);

        // Another thing to discuss here is a simple string dot split on spaces can actually get you the exact items.
        // however for both of these approaches whether it's a regular expression or a string.split to extend to AND/OR
        // you would need a recursive descent parser
        // condition.trim().split(" "); -> ["age", ">=", "30"]
        if (!matcher.find()) {
            return null;
        }

        var columnToFilterBy = matcher.group(1);
        var relation = matcher.group(2);
        var valueToCompare = matcher.group(3);

        return table.rows.stream().filter(r -> {
            var cellValue = r.get(columnToFilterBy);
            switch (relation) {
                case "=": return cellValue.equals(valueToCompare);
                case "!=": return !cellValue.equals(valueToCompare);
                case ">=": return getNumericValue(cellValue) >= getNumericValue(valueToCompare);
                case "<=": return getNumericValue(cellValue) <= getNumericValue(valueToCompare);
                case ">": return getNumericValue(cellValue) > getNumericValue(valueToCompare);
                case "<": return getNumericValue(cellValue) < getNumericValue(valueToCompare);
                default:
                    throw new IllegalArgumentException("Unsupported relation");
            }
        }).toList();
    }

    private int getNumericValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Relational operator cannot be applied to Strings");
        }
    }

    public static void main(String[] args) {
        runTest("createTable adds a new table", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));

            assertTrue(db.tables.containsKey("users"), "Expected users table to exist");
        });

        runTest("createTable does not replace an existing table", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            Table originalTable = db.tables.get("users");

            db.createTable("users", List.of("email"));

            assertTrue(db.tables.get("users") == originalTable, "Expected original users table to be kept");
        });

        runTest("insertRow adds one row to an existing table", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));

            db.insertRow("users", List.of("1", "Alice", "30"));

            assertEquals(1, db.tables.get("users").rows.size());
        });

        runTest("insertRow maps values to columns in table order", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));

            db.insertRow("users", List.of("1", "Alice", "30"));

            Map<String, String> row = db.tables.get("users").rows.get(0);
            assertEquals("1", row.get("id"));
            assertEquals("Alice", row.get("name"));
            assertEquals("30", row.get("age"));
        });

        runTest("insertRow rejects rows with the wrong number of values", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));

            db.insertRow("users", List.of("1", "Alice"));

            assertEquals(0, db.tables.get("users").rows.size());
        });

        runTest("select projects one column after numeric greater-than filter", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));

            assertRows(
                    List.of(List.of("Alice")),
                    db.select("users", List.of("name"), "age > 27")
            );
        });

        runTest("select preserves insertion order for matching rows", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));
            db.insertRow("users", List.of("3", "Cara", "31"));

            assertRows(
                    List.of(List.of("Alice"), List.of("Cara")),
                    db.select("users", List.of("name"), "age >= 30")
            );
        });

        runTest("select projects multiple columns in requested order", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));

            assertRows(
                    List.of(List.of("30", "1")),
                    db.select("users", List.of("age", "id"), "name = Alice")
            );
        });

        runTest("equals condition can match string values", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));

            assertRows(
                    List.of(List.of("2", "25")),
                    db.select("users", List.of("id", "age"), "name = Bob")
            );
        });

        runTest("not-equals condition excludes exact matches", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));
            db.insertRow("users", List.of("3", "Cara", "25"));

            assertRows(
                    List.of(List.of("Alice")),
                    db.select("users", List.of("name"), "age != 25")
            );
        });

        runTest("less-than and less-than-or-equal numeric filters work", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("products", List.of("sku", "name", "price"));
            db.insertRow("products", List.of("A1", "Pen", "2"));
            db.insertRow("products", List.of("B2", "Notebook", "5"));
            db.insertRow("products", List.of("C3", "Bag", "30"));

            assertRows(
                    List.of(List.of("Pen"), List.of("Notebook")),
                    db.select("products", List.of("name"), "price <= 5")
            );
            assertRows(
                    List.of(List.of("Pen")),
                    db.select("products", List.of("name"), "price < 5")
            );
        });

        runTest("greater-than-or-equal includes boundary value", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("orders", List.of("id", "customer", "total"));
            db.insertRow("orders", List.of("100", "Alice", "50"));
            db.insertRow("orders", List.of("101", "Bob", "75"));
            db.insertRow("orders", List.of("102", "Cara", "125"));

            assertRows(
                    List.of(List.of("Bob"), List.of("Cara")),
                    db.select("orders", List.of("customer"), "total >= 75")
            );
        });

        runTest("condition with no matches returns empty list", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));

            assertRows(
                    List.of(),
                    db.select("users", List.of("name"), "age > 99")
            );
        });

        runTest("deleteRows returns count and removes matching rows", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));
            db.insertRow("users", List.of("3", "Cara", "25"));

            assertEquals(2, db.deleteRows("users", "age = 25"));
            assertRows(
                    List.of(List.of("1", "Alice")),
                    db.select("users", List.of("id", "name"), "age >= 0")
            );
        });

        runTest("deleteRows returns zero when nothing matches", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name", "age"));
            db.insertRow("users", List.of("1", "Alice", "30"));
            db.insertRow("users", List.of("2", "Bob", "25"));

            assertEquals(0, db.deleteRows("users", "id = 999"));
            assertRows(
                    List.of(List.of("Alice"), List.of("Bob")),
                    db.select("users", List.of("name"), "age >= 0")
            );
        });

        runTest("tables are independent", () -> {
            var db = new InMemorySQLDatabase();
            db.createTable("users", List.of("id", "name"));
            db.createTable("companies", List.of("id", "name"));
            db.insertRow("users", List.of("1", "Alice"));
            db.insertRow("companies", List.of("1", "OpenAI"));

            assertRows(
                    List.of(List.of("Alice")),
                    db.select("users", List.of("name"), "id = 1")
            );
            assertRows(
                    List.of(List.of("OpenAI")),
                    db.select("companies", List.of("name"), "id = 1")
            );
        });
    }
}
