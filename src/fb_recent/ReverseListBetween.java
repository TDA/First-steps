package fb_recent;

import java.util.List;

public class ReverseListBetween {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode headCopy = head;
        int k = 1;
        while (headCopy != null) {
            k++;
            headCopy = headCopy.next;
        }
        // cannot reverse
        if (k < right) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        headCopy = head;
        k = 1;
        while (k < left - 1) {
            k++;
            headCopy = headCopy.next;
        }
        // reached start point of reversal, go till end and reverse shit
        ListNode previous = headCopy;
        ListNode reversedList;
        if (left == 1) {
            reversedList = reverseList(previous, left, right);
            dummy.next = reversedList;
        } else {
            reversedList = reverseList(previous.next, left, right);
            previous.next = reversedList;
        }
        return dummy.next;
    }

    private ListNode reverseList(ListNode current, int start, int end) {
        ListNode next = null;
        while (start <= end && current != null) {
            ListNode tempNode = new ListNode(current.val);
            tempNode.next = next;
            next = tempNode;
            current = current.next;
            start++;
        }
        ListNode tail = next;
        while (tail.next != null) tail = tail.next;
        tail.next = current;
        return next;
    }

    public static void main(String[] args){
        ReverseListBetween reverseListBetween = new ReverseListBetween();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(4);
//        head.next.next.next.next = new ListNode(5);
        System.out.println(head);
        System.out.println(reverseListBetween.reverseBetween(head, 1, 2));
    }
}
