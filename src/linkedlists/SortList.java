package linkedlists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortList {
    public ListNode sortList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        if(head == null) return null;
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        list.sort(Comparator.comparingInt(a -> a));
        ListNode newHead = new ListNode(list.get(0));
        ListNode headerPointer = newHead;
        for (int i = 1; i < list.size(); i++) {
            newHead.next = new ListNode(list.get(i));
            newHead = newHead.next;
        }
        return headerPointer;
    }

    public ListNode mergeSortList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode lastNodeInFirstHalf = null, slow = head, fast = head;
        // find slow and fast pointers to split list into 2 at each step
        while (fast != null && fast.next != null) {
            lastNodeInFirstHalf = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // cut off at half
        lastNodeInFirstHalf.next = null;
        ListNode l1 = mergeSortList(head);
        ListNode l2 = mergeSortList(slow);

        return mergeLists(l1, l2);
    }

    private ListNode mergeLists(ListNode l1, ListNode l2) {
        ListNode mergedListHead = new ListNode(0);
        ListNode mergedListPointer = mergedListHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                mergedListPointer.next = l1;
                l1 = l1.next;
            } else {
                mergedListPointer.next = l2;
                l2 = l2.next;
            }
            mergedListPointer = mergedListPointer.next;
        }
        // Add the remaining part fully
        if (l1 != null) {
            mergedListPointer.next = l1;
        }
        if (l2 != null) {
            mergedListPointer.next = l2;
        }
        return mergedListHead.next;
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(4);
        node.appendTail(2);
        node.appendTail(1);
        node.appendTail(3);
        System.out.println(new SortList().sortList(node));
        System.out.println(new SortList().mergeSortList(node));

    }
}
