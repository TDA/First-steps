package OpenAI_Interviews;

import java.util.List;
import java.util.Objects;

public class TestHelpers {
    @FunctionalInterface
    public interface TestCase {
        void run();
    }

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void runTest(String name, TestCase testCase) {
        testsRun++;
        try {
            testCase.run();
            testsPassed++;
            System.out.println("[PASS] " + name);
        } catch (AssertionError e) {
            System.err.println("[FAIL] " + name + ": " + e.getMessage());
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) throw new AssertionError(message);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, "Values do not match");
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + " – expected: " + expected + ", actual: " + actual);
        }
    }

    public static void assertNull(Object actual, String message) {
        if (actual != null) {
            throw new AssertionError(message + " – expected null, actual: " + actual);
        }
    }

    public static void assertNotNull(Object actual, String message) {
        if (actual == null) {
            throw new AssertionError(message + " – expected non-null value");
        }
    }

    public static void assertRows(List<List<String>> expected, List<List<String>> actual) {
        assertEquals(expected, actual, "Rows do not match");
    }

    public static void assertKeys(List<String> actual, List<String> expected, String message) {
        assertNotNull(actual, message);
        assertEquals(expected, actual, message);
    }
}
