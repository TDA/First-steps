package linkedlists;

import java.util.Stack;

/**
 * Created by schandramouli on 8/26/15.
 */
public class ListNode {
    ListNode next;
    int val;

    public ListNode() {
        val = -11111;
    }
    public ListNode(int val) {
        this.val = val;
    }


    void appendTail(ListNode end) {
        ListNode n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = end;
    }

    ListNode reverseList() {
        ListNode n = this;
        Stack<ListNode> nodeStack = new Stack<>();
        while (n.next != null) {
            nodeStack.push(n);
            n = n.next;
        }
        nodeStack.push(n);
        ListNode reverse = new ListNode(nodeStack.pop().val);
        while (! nodeStack.empty()) {
            int data = nodeStack.pop().val;
            reverse.appendTail(data);
        }
        return reverse;
    }

    void appendTail(int data) {
        ListNode end = new ListNode(data);
        appendTail(end);
    }


    ListNode moveTo (int position) {
        ListNode n = this;
        while(position > 0) {
            if(n.next != null) {
                n = n.next;
            }
            position--;
        }
        return n;
    }

    ListNode reverseFrom (int position) {
        ListNode n = this;
        ListNode reversePart = n.moveTo(position);
        //n = this;
        ListNode newList;
        newList = new ListNode(n.val);

        while(position > 1) {
            // copy elements till position
            n = n.next;
            newList.appendTail(n.val);
            position--;
        }

        // Use a stack to hold the reverse parts
        Stack<ListNode> nodeStack = new Stack<>();
        while (reversePart.next != null) {
            nodeStack.push(reversePart);
            reversePart = reversePart.next;
        }
        nodeStack.push(reversePart);
        while (! nodeStack.empty()) {
            int data = nodeStack.pop().val;
            newList.appendTail(data);
        }
        return newList;
    }

    void printLLNode () {
        System.out.println("Linked List: ");
        ListNode n = this;
        while (n.next != null) {
            System.out.print(n.val + "-->");
            n = n.next;
        }
        System.out.print(n.val);
        System.out.println();
    }

    int findKthFromLast (int k) {
        int len = this.findLengthofList();
        int pos = len - k;
        ListNode n = this;
        n = n.moveTo(pos);
        return n.val;
    }

    int findLengthofList() {
        // needed so that we can also extract last element
        int len = 1;
        ListNode n = this;
        while (n.next != null) {
            n = n.next;
            len++;
        }
        return len;
    }

    ListNode deleteAt (int pos) {
        // cannot delete first element
        ListNode n = this;
        if (pos == 0) {
            if (n.next != null) {
                n = n.next;
            } else {
                return null;
            }
            ListNode x = new ListNode(n.val);
            //copy everything else
            while (n.next != null) {
                n = n.next;
                x.appendTail(n.val);
            }
            return x;
        }
        ListNode x = new ListNode(n.val);
        int count = 1;
        while (n.next != null && count < pos - 1) {
            n = n.next;
            x.appendTail(n.val);
            count++;
        }
        // now skip an element
        n = n.next;
        // now copy the rest
        while (n.next != null) {
            n = n.next;
            x.appendTail(n.val);
        }
        // n.next = n.next.next;
        return x;
    }

    ListNode copyFromTo (int x, int y) {
        ListNode n = this;
        ListNode copy;
        int count = x;
        n = n.moveTo(x);
        copy = new ListNode(n.val);
        while (count < y && n.next != null) {
            n = n.next;
            copy.appendTail(n.val);
            count++;
        }

        return copy;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.appendTail(2);
        node.appendTail(3);
        node.appendTail(4);
        node.appendTail(5);
        node.printLLNode();


        ListNode newNode = node.moveTo(2);
        newNode.printLLNode();

        node.printLLNode();

        ListNode revList = node.reverseFrom(2);

        System.out.print("Half reversed: ");
        revList.printLLNode();

        node.printLLNode();
        System.out.println("3rd from last is " + node.findKthFromLast(3));
        System.out.println("2nd from last is " + node.findKthFromLast(2));
        System.out.println("5th from last is " + node.findKthFromLast(5));

        System.out.println("Deleted node at 3");
        ListNode x = node.deleteAt(3);
        x.printLLNode();

        System.out.println("Copied from 2 to 4");
        ListNode copy = node.copyFromTo(1, 3);
        copy.printLLNode();

        ListNode reversed = node.reverseList();
        reversed.printLLNode();

        System.out.println("Deleted node at 0");
        ListNode y = node.deleteAt(0);
        y.printLLNode();
    }


}
