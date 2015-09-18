/**
 * Created by schandramouli on 9/18/15.
 */
public class LLCircular extends LLNode {
    boolean isCircularList () {
        boolean b = false;
        LLNode n = this;
        return b;
    }

    int findCircularElement() {
        int x = -1;
        return x;
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
            int c = circ.data;
            System.out.println("The list is circular ");
            System.out.println("The repeated elt is " + c);
        } else {
            System.out.print("Not circular");
        }
        circ.printLLNode();
    }
}
