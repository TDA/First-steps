package fb_recent;

import java.util.PriorityQueue;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
public class KSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> allSortedNodes = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode ll: lists) {
            while (ll != null) {
                allSortedNodes.add(ll);
                ll = ll.next;
            }
        }

        ListNode result = new ListNode();
        ListNode headPointer = result;
        while (!allSortedNodes.isEmpty()) {
            result.next = new ListNode(allSortedNodes.poll().val);
            result = result.next;
        }
        return headPointer.next;
    }
}
