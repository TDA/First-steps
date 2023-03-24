package linkedlists;

/**
 * Created by schandramouli on 9/17/15.
 */
public class LLAddition extends LLNode{

    public LLAddition() {
        super();
    }

    public LLAddition(int a) {
        super(a);
    }

    LLNode add (LLNode y) {
        LLNode x = this;
        String s = "";
        // create a string from the list --> this will give us the number
        while (x != null) {
            s += x.data;
            x = x.next;
        }
        // create a string from the list --> this will give us the number
        String t = "";
        while (y != null) {
            t += y.data;
            y = y.next;
        }
        // convert them to integers and add them, then convert back to
        // string so we can add to list one at a time
        int value = Integer.parseInt(s) + Integer.parseInt(t);
        String newValue = "" + value;

        // get the character value and set them
        LLNode sum = new LLNode(Character.getNumericValue(newValue.charAt(0)));
        for (int i = 1; i < newValue.length(); i++) {
            sum.appendTail(Character.getNumericValue(newValue.charAt(i)));
        }
        return sum;
    }

    public static void main(String[] args) {
        LLAddition a = new LLAddition(7);
        a.appendTail(2);
        a.appendTail(3);
        a.printLLNode();

        LLNode b = new LLNode(4);
        b.appendTail(5);
        b.appendTail(6);
        b.printLLNode();

        LLNode c = a.add(b);
        c.printLLNode();
    }
}
