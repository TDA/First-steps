import java.util.HashMap;

/**
 * Created by schandramouli on 9/18/15.
 */
public class LLCircular extends LLNode {
    HashMap<Integer, Integer> hm;
    boolean isCircularList () {
        boolean b = false;
        LLNode n = this;
        hm = new HashMap<>();
        while (n != null) {
            if (hm.get(n.data) == null) {
                hm.put(n.data, 1);
            } else if (hm.get(n.data) == 1){
                hm.put(n.data, 2);
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

    public LLCircular() {
        super();
    }

    public LLCircular(int data) {
        super(data);
    }

    public static void main(String[] args) {
        LLCircular circ = new LLCircular('A');
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
