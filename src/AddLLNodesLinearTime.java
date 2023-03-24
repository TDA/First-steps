import java.util.List;

class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    ListNode(List<Integer> list) {
         ListNode l = new ListNode(list.get(0));
         for (int i = 1; i < list.size(); i++) {
             ListNode temp = new ListNode(list.get(i), l);
             l = temp;
         }
         this.val = l.val;
         this.next = l.next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
public class AddLLNodes {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        String totes = "";
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (sum > 9) {
                sum = sum % 10;
                carry = 1;
            } else {
                carry = 0;
            }
            totes += sum;
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        if (carry != 0) {
            totes += carry;
        }
        return constructList(totes);
    }

    ListNode constructList(String s) {
        StringBuilder sb = new StringBuilder(s);
        ListNode l = new ListNode(Integer.parseInt(String.valueOf(sb.charAt(s.length() - 1))));
        for (int i = s.length() - 2; i >= 0; i--) {
            ListNode temp = new ListNode(Integer.parseInt(String.valueOf(sb.charAt(i))), l);
            l = temp;
        }
        return l;
    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(List.of(1));
        System.out.println(l1);
        ListNode l2 = new ListNode(List.of(1, 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9));
        System.out.println(l2);
        System.out.println(new AddLLNodes().addTwoNumbers(l1, l2));
    }
}
