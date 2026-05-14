package OpenAI_Interviews;

// Full Problem: Implement a spreadsheet where cells can contain integers or formulas referencing other cells.
//
//Interface:
//
//set(cell, value) → void       // e.g., set("A1", "5") or set("A1", "=B1+C2")
//
//get(cell) → int                // evaluate and return numeric value
//
//Core Data Structures: HashMap<String, String> for raw cell contents. Build a dependency graph on evaluation. Use DFS with 3-state cycle detection (UNVISITED, IN_PROGRESS, DONE).
//
//Key Design Decisions:
//
//Parse cell references with regex: Pattern.compile("[A-Z]+[0-9]+")
//Evaluate recursively: if cell contains a formula, parse it, resolve each cell reference recursively, compute result
//Cache evaluated values; invalidate on set()
//
//Extensions:
//
//Support SUM(A1:A5) — expand range to individual cell references
//Incremental re-evaluation — maintain reverse dependency graph, only recompute affected cells on change
//Support more operators (*, /, -)

import java.util.*;
import java.util.regex.Pattern;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

record CellRef(String formula, Integer value) {
}

public class ExcelSheet {
    Map<String, CellRef> cellValues = new HashMap<>();
    HashSet<String> dirtyValues = new HashSet<>();
    // Assume simple math operations for now - minus needs escape
    Pattern pattern = Pattern.compile("(.*)([+\\-*/])(.*)");

    // The value here can be overloaded to be a direct numeric value encoded as a string or a formula starting with the equal to symbol.
    // Assumptions: int values for now, can move to float later
    public void set(String cell, String value) {
        if (value.startsWith("=")) {
            // treat as formula
            Integer val = evaluate(value.substring(1));
            cellValues.put(cell, new CellRef(value, val));
        } else {
            try {
                var intValue = Integer.valueOf(value);
                // If we have an existing value here, means this value is now dirty and needs to re-compute for dependents
                if (cellValues.containsKey(cell)) {
                    dirtyValues.add(cell);
                }
                cellValues.put(cell, new CellRef(null, intValue));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Argument for cell has to be a number or a formula");
            }
        }
    }

    private Integer getValue(String reference) {
        // we are either getting a number or a cell reference
        var intValue = 0;
        try {
            intValue = Integer.parseInt(reference);
        } catch (NumberFormatException nfe) {
            // check if its an existing reference
            if (cellValues.containsKey(reference))
                intValue = get(reference);
            else {
                throw new IllegalArgumentException("Not a number or a cell reference");
            }
        }
        return intValue;
    }

    private Integer evaluate(String value) {
        try {
            // base case, we recursed to a value, now just need to compute and return
            return getValue(value);
        } catch (IllegalArgumentException iae) {
            // this means we need to continue parsing.
        }

        var matcher = pattern.matcher(value);
        if (!matcher.find()) {
            // we did not find the operands and the operator - only unary found
            // can build support later as extension, exit for now
            return 0;
        }
        int operand1 = evaluate(matcher.group(1));
        String operator = matcher.group(2);
        int operand2 = evaluate(matcher.group(3));
        return performMath(operand1, operator, operand2);
    }

    public Integer performMath(Integer operand1, String operator, Integer operand2) {
        switch (operator) {
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            default:
                throw new IllegalArgumentException("Unsupported operator");
        }
    }

    private List<String> findCellReferences(String value) {
        List<String> cellReferences = new ArrayList<>();
        String formula = value.substring(1);

        var items = formula.split("[+\\-/*]");
        for (var i : items) {
            try {
                // if parsing fails, its a cell reference - we have already split on the operations
                Integer.parseInt(i);
            } catch (IllegalArgumentException iae) {
                cellReferences.add(i);
            }
        }
        return cellReferences;
    }

    public Integer get(String cell) {
        // Extension: Recompute if values of dependents have changed
        // this is lazy re-evaluation
        var cellRef = cellValues.get(cell);
        if (cellRef.formula() != null) {
            var cellReferences = findCellReferences(cellRef.formula());
            for (var ref : cellReferences) {
                if (dirtyValues.contains(ref)) {
                    set(cell, cellRef.formula());
                }
            }
        }

        return cellValues.get(cell).value();
    }

    @Override
    public String toString() {
        return this.cellValues.toString();
    }

    static void main() {
        runTest("simple numbers should be stored", () -> {
            ExcelSheet sheet = new ExcelSheet();
            sheet.set("A1", "5");
            assertEquals(5, sheet.get("A1"), "Numbers do not match");
        });

        runTest("simple math with references should be stored", () -> {
            ExcelSheet sheet = new ExcelSheet();
            sheet.set("A1", "5");
            sheet.set("A2", "=A1+12");
            assertEquals(17, sheet.get("A2"), "Numbers do not match");
        });

        runTest("multiple references should be processed", () -> {
            ExcelSheet sheet = new ExcelSheet();
            sheet.set("A1", "5");
            sheet.set("B1", "12");
            sheet.set("A2", "=A1+B1");
            assertEquals(17, sheet.get("A2"), "Numbers do not match");
        });

        runTest("multiple operations in a single statement should be processed", () -> {
            ExcelSheet sheet = new ExcelSheet();
            sheet.set("A1", "5");
            sheet.set("B1", "12");
            sheet.set("C1", "=A1+B1+15");
            sheet.set("A2", "=A1+B1+C1");
            assertEquals(32, sheet.get("C1"), "Numbers do not match");
            assertEquals(49, sheet.get("A2"), "Numbers do not match");
        });

        // Need to figure this extension piece out
//        runTest("PEMDAS ordering for operations in a single statement should be processed", () -> {
//            ExcelSheet sheet = new ExcelSheet();
//            sheet.set("A1", "5");
//            sheet.set("B1", "12");
//            sheet.set("C1", "=A1*B1+15"); // will work
//            sheet.set("A2", "=A1+B1*15"); // PEMDAS not followed - execution is left to right like a calculator, not excel
//            assertEquals(75, sheet.get("C1"), "Numbers do not match");
//            assertEquals(185, sheet.get("A2"), "Numbers do not match");
//        });

        runTest("Invalidate values when overwrite happens", () -> {
            ExcelSheet sheet = new ExcelSheet();
            sheet.set("A1", "5");
            sheet.set("B1", "12");
            sheet.set("C1", "=A1+B1+15"); // will work
            assertEquals(32, sheet.get("C1"), "Numbers do not match");

            // update value of A1 - recompute will not be triggered automatically :( - is this something we need to implement for this?
            sheet.set("A1", "10");
            assertEquals(37, sheet.get("C1"), "Numbers do not match");
        });
    }
}
