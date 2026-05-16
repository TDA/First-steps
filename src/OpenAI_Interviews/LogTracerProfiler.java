package OpenAI_Interviews;

// Core requirements:
//
//Given a log of function enter/exit events with timestamps, build a call tree
//Calculate each function's self-time (exclusive) and total time (inclusive)
//Output a formatted trace or summary
//
//Sample input:
//
//{"event": "enter", "function": "main", "timestamp": 0}
//
//{"event": "enter", "function": "foo", "timestamp": 5}
//
//{"event": "exit", "function": "foo", "timestamp": 15}
//
//{"event": "enter", "function": "bar", "timestamp": 20}
//
//{"event": "exit", "function": "bar", "timestamp": 30}
//
//{"event": "exit", "function": "main", "timestamp": 35}
//
// Output: main: total=35, self=15 | foo: total=10, self=10 | bar: total=10, self=10

//Likely extensions:
//
//Handle recursive functions (aggregate across invocations)
//Output a flame graph format
//Handle concurrent/interleaved traces (multiple threads)
//
//Java-specific notes: Use a record or inner class for Frame: record Frame(String function, long startTime, long childTime) {}. Use ArrayDeque for the stack.

import java.util.*;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

record Function(String functionName, String event, Integer timestamp) {}
class FunctionFrame {
    String functionName;
    String event;
    Integer timestamp;
    Integer depth;
    Integer childTime;

    public FunctionFrame(String functionName, String event, Integer timestamp, Integer depth, Integer childTime) {
        this.functionName = functionName;
        this.event = event;
        this.timestamp = timestamp;
        this.depth = depth;
        this.childTime = childTime;
    }
}
record FunctionGlobalTime(Integer calls, Integer totalTime, Integer selfTime){}
public class LogTracerProfiler {
    public String trace(List<Function> functionCalls) {
        Deque<FunctionFrame> callStack = new ArrayDeque<>();
        Map<Integer, String> levelStrings = new HashMap<>();
        var depth = 0;
        for (Function f : functionCalls) {
            StringBuilder subStringBuilder = new StringBuilder();
            if ("enter".equals(f.event())) {
                // push on to stack
                callStack.push(new FunctionFrame(f.functionName(), f.event(), f.timestamp(), ++depth, 0));
            } else if ("exit".equals(f.event())) {
                // pop most recent same event, if events dont match - throw error
                if (callStack.isEmpty())
                    throw new IllegalArgumentException("Exit called on function but no functions remain on callstack");
                FunctionFrame popped = callStack.pop();
                if (!"enter".equals(popped.event))
                    throw new IllegalArgumentException("No Entry for this function found in callstack");
                if (!f.functionName().equals(popped.functionName)) throw new IllegalArgumentException("No matching function found");

                // We have a match! calc self time and propagate up the chain
                var totalTime = f.timestamp() - popped.timestamp;
                var selfTime =  totalTime - popped.childTime;

                var parent = callStack.peek();
                if (parent != null)
                    parent.childTime += totalTime;

                subStringBuilder.append(f.functionName());
                subStringBuilder.append(": ");
                subStringBuilder.append("total=");
                subStringBuilder.append(totalTime);
                subStringBuilder.append(", self=");
                subStringBuilder.append(selfTime);
                var functionString = subStringBuilder.toString();
                var levelString = levelStrings.getOrDefault(depth, "");
                if ("".equals(levelString)) {
                    levelString += functionString;
                } else {
                    levelString = String.join(" | ", levelString, functionString);
                }
                levelStrings.put(depth, levelString);
                --depth;
            }
        }

        return String.join(" | ", levelStrings.values());
    }

    public Map<String, FunctionGlobalTime> aggregateByFunction(List<Function> functionCalls) {
        Deque<FunctionFrame> callStack = new ArrayDeque<>();
        Map<String, FunctionGlobalTime> globalAccumulation = new HashMap<>();
        var depth = 0;
        for (Function f : functionCalls) {
            if ("enter".equals(f.event())) {
                // push on to stack
                callStack.push(new FunctionFrame(f.functionName(), f.event(), f.timestamp(), ++depth, 0));
            } else if ("exit".equals(f.event())) {
                // pop most recent same event, if events dont match - throw error
                if (callStack.isEmpty())
                    throw new IllegalArgumentException("Exit called on function but no functions remain on callstack");
                FunctionFrame popped = callStack.pop();
                if (!"enter".equals(popped.event))
                    throw new IllegalArgumentException("No Entry for this function found in callstack");
                if (!f.functionName().equals(popped.functionName)) throw new IllegalArgumentException("No matching function found");

                // We have a match! calc self time and propagate up the chain
                var totalTime = f.timestamp() - popped.timestamp;
                var selfTime =  totalTime - popped.childTime;

                var parent = callStack.peek();
                if (parent != null)
                    parent.childTime += totalTime;

                var acc = globalAccumulation.getOrDefault(f.functionName(), new FunctionGlobalTime(0,0, 0));
                globalAccumulation.put(f.functionName(), new FunctionGlobalTime(acc.calls() + 1, acc.totalTime() + totalTime, acc.selfTime() + selfTime));
            }
        }
        return globalAccumulation;
    }

    public static void main(String[] args) {
        runTest("single function has equal total and self time", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    "main: total=10, self=10",
                    profiler.trace(List.of(
                            new Function("main", "enter", 0),
                            new Function("main", "exit", 10)
                    )),
                    "single function trace should include total and self time"
            );
        });

        runTest("nested function subtracts child time from parent self time", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    "main: total=10, self=6 | foo: total=4, self=4",
                    profiler.trace(List.of(
                            new Function("main", "enter", 0),
                            new Function("foo", "enter", 2),
                            new Function("foo", "exit", 6),
                            new Function("main", "exit", 10)
                    )),
                    "parent self time should exclude nested child duration"
            );
        });

        runTest("deeply nested function subtracts child time from parent self time", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    "main: total=10, self=6 | foo: total=4, self=3 | bar: total=1, self=1",
                    profiler.trace(List.of(
                            new Function("main", "enter", 0),
                            new Function("foo", "enter", 2),
                            new Function("bar", "enter", 4),
                            new Function("bar", "exit", 5),
                            new Function("foo", "exit", 6),
                            new Function("main", "exit", 10)
                    )),
                    "parent self time should exclude nested child duration"
            );
        });

        runTest("multiple sibling calls accumulate child time for parent", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    "main: total=35, self=15 | foo: total=10, self=10 | bar: total=10, self=10",
                    profiler.trace(List.of(
                            new Function("main", "enter", 0),
                            new Function("foo", "enter", 5),
                            new Function("foo", "exit", 15),
                            new Function("bar", "enter", 20),
                            new Function("bar", "exit", 30),
                            new Function("main", "exit", 35)
                    )),
                    "parent self time should exclude all sibling child calls"
            );
        });

        runTest("recursive calls are treated as distinct stack frames", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    "fib: total=10, self=6 | fib: total=4, self=4",
                    profiler.trace(List.of(
                            new Function("fib", "enter", 0),
                            new Function("fib", "enter", 3),
                            new Function("fib", "exit", 7),
                            new Function("fib", "exit", 10)
                    )),
                    "recursive calls should compute each invocation using stack frames"
            );
        });

        runTest("aggregate by function name combines totals globally across invocations", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    Map.of(
                            "main", new FunctionGlobalTime(1, 30, 10),
                            "db", new FunctionGlobalTime(2, 13, 13),
                            "render", new FunctionGlobalTime(1, 7, 7)
                    ),
                    profiler.aggregateByFunction(List.of(
                            new Function("main", "enter", 0),
                            new Function("db", "enter", 2),
                            new Function("db", "exit", 7),
                            new Function("render", "enter", 10),
                            new Function("render", "exit", 17),
                            new Function("db", "enter", 20),
                            new Function("db", "exit", 28),
                            new Function("main", "exit", 30)
                    )),
                    "aggregation should combine all invocations of the same function name"
            );
        });

        runTest("aggregate by function name combines recursive invocations", () -> {
            LogTracerProfiler profiler = new LogTracerProfiler();

            assertEquals(
                    Map.of("fib", new FunctionGlobalTime(2, 14, 10)),
                    profiler.aggregateByFunction(List.of(
                            new Function("fib", "enter", 0),
                            new Function("fib", "enter", 3),
                            new Function("fib", "exit", 7),
                            new Function("fib", "exit", 10)
                    )),
                    "recursive invocations should aggregate under the same function name"
            );
        });
    }
}
