package OpenAI_Interviews;

// Full Problem: Given a current working directory and a cd argument, return the resolved absolute path.
// Handle ., .., ~, absolute paths, relative paths, trailing slashes, multiple slashes.

// Note: This is likely too simple for a Staff-level phone screen on its own.
// More likely to appear as part of Q10 (File Directory Utility) or as a warm-up. Still worth practicing for speed.

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

public class UnixCDCommand {
    String currentDirectory;
    String homeDirectory;
    Deque<String> directoryStack = new ArrayDeque<>();

    public UnixCDCommand(String currentDirectory, String homeDirectory) {
        this.currentDirectory = currentDirectory;
        this.homeDirectory = homeDirectory;
        for (String dir : currentDirectory.split("/")) {
            if (!dir.isEmpty()) {
                directoryStack.push(dir);
            }
        }
    }

    public String resolve(String path) {
        if (path.startsWith("/")) {
            processAbsolutePath(path);
        }

        processRelativePath(path);

        return constructPathFromStack();
    }

    private void processAbsolutePath(String path) {
        // this is an absolute path - the stack is useless, reset it with the new string
        this.currentDirectory = path;
        this.directoryStack = new ArrayDeque<>();
    }

    private void processRelativePath(String path) {
        var components = path.split("/");
        for (var c : components) {
            // traverse the directory structure for each component
            switch (c) {
                case "..":
                    // go up == pop if not empty
                    if (!directoryStack.isEmpty())
                        directoryStack.pop();
                    break;
                case ".":
                    // fall through
                case "":
                    // do nothing
                    break;
                case "~":
                    // pop everything
                    while (!directoryStack.isEmpty()) directoryStack.pop();
                    // insert home directory only
                    for (var h : homeDirectory.split("/")) {
                        if (!h.isEmpty()) {
                            directoryStack.push(h);
                        }
                    }
                    break;
                default:
                    directoryStack.push(c);
                    break;
            }
        }
    }

    
    private String constructPathFromStack() {
        if (directoryStack.isEmpty()) return "/";
        StringBuilder sb = new StringBuilder(("/"));
        var it = directoryStack.descendingIterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("/");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        runTest("should resolve ../ traversal n times till root", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/", cd.resolve("../../"));
            assertEquals("/Users/saipc/", cd.resolve("../../"));
            assertEquals("/", cd.resolve("../../"));
            assertEquals("/", cd.resolve("../../"));
        });

        runTest("should resolve relative path ../../another-dir correctly", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/another-dir/", cd.resolve("../../another-dir"));
        });

        runTest("should resolve relative path ./another-dir correctly", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/my/directory/another-dir/", cd.resolve("./another-dir"));
        });

        runTest("should resolve absolute path /Users/saipc/another-dir correctly", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/another-dir/", cd.resolve("/Users/saipc/another-dir"));
        });

        runTest("should resolve trailing slashes another-dir/ correctly", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory/", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/my/directory/another-dir/", cd.resolve("another-dir/"));
        });

        // we get this for free - Trailing empty strings are discarded: Java's standard split(regex) method automatically removes all trailing empty strings from the resulting array by default.
        runTest("should resolve multiple trailing slashes another-dir//// correctly", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory////", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/my/directory/another-dir/", cd.resolve("another-dir////"));
        });

        runTest("should keep current directory for empty path", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/this/is/my/directory/", cd.resolve(""));
        });

        runTest("should collapse repeated middle slashes", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc", "/Users/saipc");
            assertEquals("/Users/saipc/projects/openai/", cd.resolve("projects//openai"));
        });

        runTest("should normalize dot and dot dot inside absolute paths", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/bob/project/", cd.resolve("/Users/saipc/../bob/./project"));
        });

        runTest("should not traverse above root for absolute paths", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc", "/Users/saipc");
            assertEquals("/tmp/", cd.resolve("/../../tmp"));
        });

        runTest("should not traverse above root for relative paths", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc", "/Users/saipc");
            assertEquals("/tmp/", cd.resolve("../../../tmp"));
        });

        runTest("should resolve current directory components only", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc", "/Users/saipc");
            assertEquals("/Users/saipc/a/b/", cd.resolve("././a/./b"));
        });

        runTest("should resolve tilde to home directory", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/", cd.resolve("~"));
        });

        runTest("should resolve paths under tilde", () -> {
            UnixCDCommand cd = new UnixCDCommand("/Users/saipc/this/is/my/directory", "/Users/saipc");
            assertEquals("/Users/saipc/notes/", cd.resolve("~/projects/../notes"));
        });
    }
}
