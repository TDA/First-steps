import java.util.Stack;

/**
 * Created by schandramouli on 8/26/15.
 */
public class LLNode {
    LLNode next;
    int data;

    public LLNode(int data) {
        this.data = data;
    }

    void appendTail(LLNode end) {
        LLNode n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = end;
    }

    void appendTail(int data) {
        LLNode end = new LLNode(data);
        appendTail(end);
    }


    LLNode moveTo (int position) {
        LLNode n = this;
        while(position > 0) {
            if(n.next != null) {
                n = n.next;
            }
            position--;
        }
        return n;
    }

    LLNode reverseFrom (int position) {
        LLNode n = this;
        LLNode reversePart = n.moveTo(position);
        //n = this;
        LLNode newList = new LLNode(n.data);
        while(position > 1) {
            // copy elements till position
            n = n.next;
            newList.appendTail(n.data);
            position--;
        }

        // Use a stack to hold the reverse parts
        Stack<LLNode> nodeStack = new Stack<>();
        while (reversePart.next != null) {
            nodeStack.push(reversePart);
            reversePart = reversePart.next;
        }
        nodeStack.push(reversePart);
        while (! nodeStack.empty()) {
            newList.appendTail(nodeStack.pop().data);

        }
        return newList;
    }

    void printLLNode () {
        System.out.println("Linked List: ");
        LLNode n = this;
        while (n.next != null) {
            System.out.print(n.data + "-->");
            n = n.next;
        }
        System.out.print(n.data);
        System.out.println();
    }

    int findKthFromLast (int k) {
        int len = this.findLengthofList();
        int pos = len - k;
        LLNode n = this;
        n = n.moveTo(pos);
        return n.data;
    }

    int findLengthofList() {
        // needed so that we can also extract last element
        int len = 1;
        LLNode n = this;
        while (n.next != null) {
            n = n.next;
            len++;
        }
        return len;
    }

    LLNode deleteAt (int pos) {
        LLNode n = this;
        LLNode x = new LLNode(n.data);
        int count = 1;
        while (n.next != null && count != pos - 1) {
            n = n.next;
            x.appendTail(n.data);
            count++;
        }
        // now skip an element
        n = n.next;
        // now copy the rest
        while (n.next != null) {
            n = n.next;
            x.appendTail(n.data);
        }
        // n.next = n.next.next;
        return x;
    }

    LLNode copyFromTo (int x, int y) {
        LLNode n = this;
        LLNode copy;
        int count = x;
        n = n.moveTo(x);
        copy = new LLNode(n.data);
        while (count < y && n.next != null) {
            n = n.next;
            copy.appendTail(n.data);
            count++;
        }

        return copy;
    }

    public static void main(String[] args) {
        LLNode node = new LLNode(1);
        node.appendTail(2);
        node.appendTail(3);
        node.appendTail(4);
        node.appendTail(5);
        node.printLLNode();


        LLNode newNode = node.moveTo(2);
        newNode.printLLNode();

        node.printLLNode();

        LLNode revList = node.reverseFrom(2);

        System.out.print("Half reversed: ");
        revList.printLLNode();

        node.printLLNode();
        System.out.println("3rd from last is " + node.findKthFromLast(3));
        System.out.println("2nd from last is " + node.findKthFromLast(2));
        System.out.println("5th from last is " + node.findKthFromLast(5));

        System.out.println("Deleted node at 3");
        LLNode x = node.deleteAt(3);
        x.printLLNode();

        System.out.println("Copied from 2 to 4");
        LLNode copy = node.copyFromTo(1, 3);
        copy.printLLNode();

    }


}
