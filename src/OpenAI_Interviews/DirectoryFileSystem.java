package OpenAI_Interviews;

import java.util.*;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

// Full Problem: Implement a virtual file system that supports Unix-like commands.
//
//Interface:
//
//mkdir(path) → void
//
//ls(path) → List<String>
//
//cd(path) → void
//
//pwd() → String
//
//touch(path) → void
//
//find(path, name) → List<String>
//
//Core Data Structures: Tree of Node objects where each node has String name, boolean isDirectory, Map<String, Node> children. Maintain a currentDirectory pointer.
//
//Key Design Decisions:
//
//Path resolution: split by /, traverse tree from root (absolute) or current directory (relative)
//Handle . and .. during traversal
//find is recursive DFS from the given path
//
//Extensions:
//
//Support rm and rm -r (recursive delete)
//Support symlinks
//Support cat (file contents) and echo "text" > file (write)
//Support glob patterns in ls (e.g., ls *.txt)
//
//Relationship to Q13 (Unix cd): Q13 is a subset of this problem (only path resolution). Q10 is the full file system. If you get Q10, the cd logic is one method within it.

class FileDir {
    boolean isDirectory;
    String name;
    FileDir parent;
    Map<String, FileDir> children;
    String currentPath; // optional, but reduces traversals since you know the path at creation time


    public FileDir(boolean isDirectory, String name, FileDir parent, Map<String, FileDir> children, String currentPath) {
        this.isDirectory = isDirectory;
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.currentPath = currentPath;
    }
}

// This is a clear tree based question. Even the methods themselves are natural tree traversals.
public class DirectoryFileSystem {
    // always have ROOT node
    FileDir ROOT = new FileDir(true, "", null, new HashMap<>(), "/");
    FileDir currentDirectory;

    public DirectoryFileSystem() {
        this.currentDirectory = ROOT;
    }

    void mkdir(String path) {
        // we treat this similar to `mkdir -p` - When a folder in the given pathway doesn't exist, we just create it and move on from there.
        // We could also treat it to the traditional MKDIR and throw an error if needed.
        resolvePath(path, true);
    }

    void touch(String path) {
        var paths = path.split("/");
        var fileName = paths[paths.length - 1];
        String folderPath = String.join("/", Arrays.copyOfRange(paths, 0, paths.length - 1));
        try {
            FileDir f = resolvePath(folderPath, false);
            var file = new FileDir(false, fileName, f, null, f.currentPath + "/" + fileName);
            f.children.put(fileName, file);
        } catch (IllegalArgumentException iae) {
            return;
        }
    }

    void cd(String path) {
        // This is the most critical thing to implement. we need to support both absolute and relative traversals. once we do this though everything else becomes super easy
        currentDirectory = resolvePath(path, false);
    }

    private FileDir resolvePath(String path, boolean shouldCreateIfMissing) {
        FileDir startingNode;
        if (path.startsWith("/") || path.startsWith("~")) {
            startingNode = ROOT;
        } else {
            startingNode = currentDirectory;
        }
        for (var p : path.split("/")) {
            switch (p) {
                case "": // fall through
                case ".": // do nothing;
                    continue;
                case "..":
                    // traverse to parent instead
                    if (startingNode.parent != null)
                        startingNode = startingNode.parent;
                    else
                        startingNode = ROOT;
                    // We don't have to throw here because if you do .. at the root level, you effectively stay at the root level.
                    continue;
                default:
                    if (startingNode.children.get(p) != null) {
                        startingNode = startingNode.children.get(p);
                    } else if (shouldCreateIfMissing) {
                        startingNode = createFileDir(p, startingNode);
                    } else {
                        // Not a valid traversal - blow up and throw
                        throw new IllegalArgumentException("Invalid path");
                    }
            }
        }
        return startingNode;
    }

    private FileDir createFileDir(String p, FileDir startingNode) {
        String newPath;
        // We can avoid this branching logic by just appending trailing slashes to everything. But that's a really minor nit.
        if (startingNode == ROOT) {
            newPath = startingNode.currentPath + p;
        } else {
            newPath = startingNode.currentPath + "/" + p;
        }
        var newDir = new FileDir(true, p, startingNode, new HashMap<>(), newPath);
        startingNode.children.put(p, newDir);
        startingNode = newDir;
        return startingNode;
    }

    List<String> ls (String path) {
        FileDir node;
        List<String> names = new ArrayList<>();
        try {
            node = resolvePath(path, false);
            if (node.isDirectory) {
                var children = node.children;
                names.addAll(children.values().stream().map(f -> f.name).toList());
            } else {
                names.add(node.name);
            }
            return names;
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    List<String> find(String path, String name) {
        // Naive find implementation first - only children. This actually needs a depth first search starting from that directory. so we'll need to implement it separately.
        List<String> allMatches = new ArrayList<>();
        try {
            FileDir searchDir = resolvePath(path, false);
            search(name, allMatches, searchDir);
            return allMatches;
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    private void search(String name, List<String> allMatches, FileDir searchDir) {
        if (searchDir == null) return;
        if (searchDir.name.equals(name))
            allMatches.add(searchDir.currentPath);

        if (searchDir.isDirectory) {
            for (var c : searchDir.children.values())
                search(name, allMatches, c);
        }
    }

    String pwd() {
        return currentDirectory.currentPath;
    }

    public static void main(String[] args) {
        runTest("mkdir creates nested absolute directories and ls returns direct children", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/users/saipc/projects");
            fs.mkdir("/users/saipc/notes");

            assertEquals(List.of("users"), fs.ls("/"));
            assertEquals(List.of("saipc"), fs.ls("/users"));
            assertEquals(List.of("projects", "notes"), fs.ls("/users/saipc"));
        });

        runTest("cd supports absolute, relative, dot, and parent traversal", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/users/saipc/projects/openai");
            fs.cd("/users/saipc/projects");
            assertEquals("/users/saipc/projects", fs.pwd());

            fs.cd("./openai");
            assertEquals("/users/saipc/projects/openai", fs.pwd());

            fs.cd("../..");
            assertEquals("/users/saipc", fs.pwd());

            fs.cd("../../..");
            assertEquals("/", fs.pwd());
        });

        runTest("relative mkdir and touch operate from current directory", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/workspace");
            fs.cd("/workspace");
            fs.mkdir("src/OpenAI_Interviews");
            fs.touch("README.md");
            fs.touch("src/OpenAI_Interviews/DirectoryFileSystem.java");

            assertListEqualsIgnoreOrder(List.of("README.md", "src"), fs.ls("."));
            assertListEqualsIgnoreOrder(List.of("OpenAI_Interviews"), fs.ls("src"));
            assertListEqualsIgnoreOrder(List.of("DirectoryFileSystem.java"), fs.ls("src/OpenAI_Interviews"));
        });

        runTest("ls on file returns only that file name", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/tmp");
            fs.touch("/tmp/output.log");

            assertEquals(List.of("output.log"), fs.ls("/tmp/output.log"));
        });

        runTest("find returns matching files and directories under a path", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/repo/src/main");
            fs.mkdir("/repo/src/test");
            fs.mkdir("/repo/docs/main");
            fs.touch("/repo/src/main/App.java");
            fs.touch("/repo/src/test/App.java");
            fs.touch("/repo/docs/main/README.md");

            assertListEqualsIgnoreOrder(
                    List.of("/repo/docs/main", "/repo/src/main"),
                    fs.find("/repo", "main")
            );
            assertListEqualsIgnoreOrder(
                    List.of("/repo/src/main/App.java", "/repo/src/test/App.java"),
                    fs.find("/repo", "App.java")
            );
        });

        runTest("path normalization works for mkdir, touch, ls, and find", () -> {
            DirectoryFileSystem fs = new DirectoryFileSystem();

            fs.mkdir("/a//b/./c");
            fs.touch("/a/b/../notes.txt");

            assertEquals(List.of("b", "notes.txt"), fs.ls("/a"));
            assertEquals(List.of("/a/notes.txt"), fs.find("/a/b/..", "notes.txt"));
        });
    }

    private static void assertListEqualsIgnoreOrder(List<String> expected, List<String> actual) {
        List<String> sortedExpected = new ArrayList<>(expected);
        List<String> sortedActual = new ArrayList<>(actual);

        Collections.sort(sortedExpected);
        Collections.sort(sortedActual);

        assertEquals(sortedExpected, sortedActual);
    }
}
