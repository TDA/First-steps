import java.util.ArrayList;

/**
 * Created by schandramouli on 9/22/16.
 */
public class AddOneToArray {

    public static ArrayList<Integer> plusOne(ArrayList<Integer> a) {
        // we need to add one to the array
        // we need to iterate from right to left
        int carry = 0;
        int num = a.get(a.size() - 1);
        num = num + 1;

        if (num >= 10) {
            // we need to propagate the carry
            num = num % 10;
            carry = 1;
            a.set(a.size() - 1, num);
            for (int i = a.size() - 2; i >= 0 ; i--) {
                if (carry == 0) {
                    break;
                }
                int numPosI = a.get(i);
                numPosI = numPosI + carry;
                if (numPosI >= 10) {
                    carry = 1;
                    numPosI = numPosI % 10;
                    // set the number back
                } else {
                    carry = 0;
                }
                a.set(i, numPosI);
            }
            if (carry != 0) {
                // we need an additional element
                a.add(0, carry);
            }
        } else {
            a.set(a.size() - 1, num);
        }
        // no issues, figure out how to display the array
        // now remove all zeroes to the left
        for (int i = 0; i < a.size(); i++) {
            if (a.get(0) == 0) {
                a.remove(0);
            } else {
                break;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(9);
        a.add(9);
        a.add(9);
        a.add(9);
        a.add(9);
        System.out.println(plusOne(a));
    }
}
