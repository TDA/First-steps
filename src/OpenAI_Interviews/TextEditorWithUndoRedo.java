package OpenAI_Interviews;

// Full Problem: Implement a text editor that supports basic operations with full undo/redo capability.
//
//Interface:
//
//insert(text) → void          // insert at cursor position
//
//delete(n) → void             // delete n characters before cursor
//
//moveCursor(position) → void  // move cursor to absolute position
//
//getText() → String           // return full text
//
//undo() → void                // reverse last operation
//
//redo() → void                // re-apply last undone operation
//
//Core Data Structures:
//
//StringBuilder for the text buffer
//int cursor for cursor position
//Deque<Command> undoStack — each command stores the operation type and enough info to reverse it
//Deque<Command> redoStack — cleared on any new operation
//
//Key Design Decisions:
//
//Command pattern: each operation creates a command object with execute() and undo() methods
//insert("hello") → undo is delete(5) at the same position
//delete(3) → undo is insert(deletedText) at the same position
//Any new operation clears the redo stack
//
//Extensions:
//
//Batch undo (group multiple operations into one undo step)
//Support select(start, end) and replaceSelection(text)
//Support copy() / paste() with clipboard
//Collaborative editing (operational transform — discussion only)

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

record Command (String command, String text, Integer startIndex, Integer endIndex) {

}

public class TextEditorWithUndoRedo {
    Integer cursorPosition = 0;
    StringBuilder textEditor = new StringBuilder();
    Deque<Command> undoStack = new ArrayDeque<>();
    Deque<Command> redoStack = new ArrayDeque<>();

    void insert(String text) {
        // insert -> undo -> delete
        undoStack.push(new Command("delete", text, cursorPosition, cursorPosition + text.length()));
        while (!redoStack.isEmpty()) redoStack.pop();
        applyInsert(cursorPosition, text);
    }

    private void applyInsert(Integer cursorPosition, String text) {
        textEditor.insert(cursorPosition, text);
        moveCursor(cursorPosition + text.length());
    }

    void delete(Integer n) {
        // boundary checks
        // cant delete more than currentCursor position
        if (n > cursorPosition) n = cursorPosition;
        // delete -> undo -> insert
        String textToDelete = textEditor.substring(cursorPosition - n, cursorPosition);
        undoStack.push(new Command("insert", textToDelete, cursorPosition - n, cursorPosition ));
        while (!redoStack.isEmpty()) redoStack.pop();
        applyDelete(cursorPosition - n, cursorPosition);
    }

    private void applyDelete(Integer startIndex, Integer endIndex) {
        textEditor.delete(startIndex, endIndex);
        moveCursor(startIndex);
    }

    void moveCursor(Integer position) {
        if (position < 0 || position > textEditor.length()) throw new IllegalArgumentException("Trying to move cursor beyond the text editor contents");
        cursorPosition = position;
    }

    String getText() {
        return textEditor.toString();
    }

    void undo() {
        doAction("undo");
    }

    void redo() {
        doAction("redo");
    }

    void beginBatch(String batchName) {
        undoStack.push(new Command("batch-begin", batchName, 0, 0));
    }

    void endBatch(String batchName) {
        undoStack.push(new Command("batch-end", batchName, 0, 0));
    }

    void doAction(String action) {
        Deque<Command> stackToPop;
        Deque<Command> stackToPush;
        switch (action) {
            case "undo":
                stackToPop = undoStack;
                stackToPush = redoStack;
                break;
            case "redo":
                stackToPop = redoStack;
                stackToPush = undoStack;
                break;
            default: throw new IllegalArgumentException("No such stack found");
        }

        if (stackToPop.isEmpty()) return; // nothing to undo or redo
        Command popped = stackToPop.pop();
        if (popped == null) return;
        var command = popped.command();
        var text = popped.text();
        var startPosition = popped.startIndex();
        var endPosition = popped.endIndex();

        switch (command) {
            case "insert":
                applyInsert(startPosition, text);
                stackToPush.push(new Command("delete", text, startPosition, endPosition));
                break;
            case "delete":
                applyDelete(startPosition, endPosition);
                stackToPush.push(new Command("insert", text, startPosition, endPosition));
                break;
            case "batch-end":
                // implement batch undo or redo logic
                performBatchOperation(popped.text(), stackToPop, stackToPush);
                break;
            default:
                throw new IllegalArgumentException("Invalid command name found");
        }
    }

    private void performBatchOperation(String batchName, Deque<Command> stackToPop, Deque<Command> stackToPush) {
        // collect all actions in this batch and then undo / redo them
        List<Command> commandsToPerform = new ArrayList<>();
        if (stackToPop.isEmpty()) return;
        Command nextCommand = stackToPop.pop();
        while (nextCommand != null && !(nextCommand.command().equals("batch-begin") && nextCommand.text().equals(batchName))) {
            // pop till we find the batch-begin
            switch (nextCommand.command()) {
                case "insert":
                    applyInsert(nextCommand.startIndex(), nextCommand.text());
                    commandsToPerform.add(new Command("delete", nextCommand.text(), nextCommand.startIndex(), nextCommand.endIndex()));
                    break;
                case "delete":
                    applyDelete(nextCommand.startIndex(), nextCommand.endIndex());
                    commandsToPerform.add(new Command("insert", nextCommand.text(), nextCommand.startIndex(), nextCommand.endIndex()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command name found");
            }
            nextCommand = stackToPop.pop();
        }
        // Collected all items.
        stackToPush.push(nextCommand);
        for (var c : commandsToPerform) {
            stackToPush.push(c);
        }
        stackToPush.push(new Command("batch-end", batchName, 0, 0));
    }


    public static void main(String[] args) {
        runTest("Should insert data and return it", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("sai pc is studying");
            assertEquals("sai pc is studying" , editor.getText());
        });


        runTest("Should delete data", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("sai pc is studying");
            editor.delete(editor.getText().length());
            assertEquals("" , editor.getText());
        });


        runTest("Should delete data at end by default", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("sai pc is studying");
            editor.delete(8);
            assertEquals("sai pc is " , editor.getText());
        });

        runTest("move + insert at right spot", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("sai pc is studying");
            editor.moveCursor(6);
            editor.insert(" and li pc");
            assertEquals("sai pc and li pc is studying" , editor.getText());
        });

        runTest("move + delete at right spot", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("sai pc is studying");
            editor.moveCursor(6);
            editor.delete(10);
            editor.insert("li pc");
            assertEquals("li pc is studying" , editor.getText());
        });

        runTest("undo reverses insert", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello");

            editor.undo();

            assertEquals("", editor.getText());
        });

        runTest("undo reverses delete and restores cursor for follow-up insert", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello world");
            editor.delete(5);

            editor.undo();
            editor.insert("!");

            assertEquals("hello world!", editor.getText());
        });

        runTest("undo only reverses the most recent edit", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello");
            editor.insert(" world");

            editor.undo();

            assertEquals("hello", editor.getText());
        });

        runTest("redo reapplies an undone insert", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello");
            editor.undo();

            editor.redo();

            assertEquals("hello", editor.getText());
        });

        runTest("redo reapplies an undone delete", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello world");
            editor.delete(5);
            editor.undo();

            editor.redo();

            assertEquals("hello ", editor.getText());
        });

        runTest("new edit clears redo history", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("hello");
            editor.undo();
            editor.insert("bye");

            editor.redo();

            assertEquals("bye", editor.getText());
        });

        runTest("batch undo reverses grouped edits as one operation", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();

            editor.beginBatch("batch-A");
            editor.insert("hello");
            editor.insert(" ");
            editor.insert("world");
            editor.endBatch("batch-A");

            assertEquals("hello world", editor.getText());
            editor.undo();
            assertEquals("", editor.getText());
        });

        runTest("batch redo reapplies grouped edits as one operation", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();

            editor.beginBatch("batch-A");
            editor.insert("hello");
            editor.insert(" ");
            editor.insert("world");
            editor.endBatch("batch-A");
            editor.undo();

            assertEquals("", editor.getText());
            editor.redo();
            assertEquals("hello world", editor.getText());
        });

        runTest("batch undo preserves prior non-batched edits", () -> {
            TextEditorWithUndoRedo editor = new TextEditorWithUndoRedo();
            editor.insert("prefix ");

            editor.beginBatch("batch-A");
            editor.insert("hello");
            editor.insert(" ");
            editor.insert("world");
            editor.endBatch("batch-A");

            editor.undo();

            assertEquals("prefix ", editor.getText());
        });
    }
}
