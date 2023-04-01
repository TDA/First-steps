package linkedlists;

import java.util.HashMap;

/**
 * Created by schandramouli on 9/18/15.
 */
public class ListCircular extends ListNode {
    HashMap<Integer, Integer> hm;

    public ListCircular() {
        super();
    }

    public ListCircular(int data) {
        super(data);
    }

    boolean isCircularList () {
        boolean b = false;
        ListNode n = this;
        hm = new HashMap<>();
        while (n != null) {
            if (hm.get(n.val) == null) {
                hm.put(n.val, 1);
            } else if (hm.get(n.val) == 1){
                hm.put(n.val, 2);
                b = true;
                break;
            }
            n = n.next;
        }
        return b;
    }

    int findCircularElement() {
        for (Integer key : hm.keySet()){
            if (hm.get(key) == 2) {
                return key;
            }
        }
        // not found
        return -1;
    }

    public static void main(String[] args) {
        ListCircular circ = new ListCircular('A');
        circ.appendTail('B');
        circ.appendTail('C');
        circ.appendTail('D');
        circ.appendTail('E');
        circ.appendTail('C');
        boolean b = circ.isCircularList();
        if (b) {
            int c = circ.findCircularElement();
            System.out.println("The list is circular ");
            System.out.println("The repeated elt is " + c);
        } else {
            System.out.println("Not circular");
        }
        circ.printLLNode();
    }
}
