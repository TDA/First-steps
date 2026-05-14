package OpenAI_Interviews;

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

    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " – expected: " + expected + ", actual: " + actual);
        }
    }
}
