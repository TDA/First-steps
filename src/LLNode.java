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
    }


}
