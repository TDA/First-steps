import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by schandramouli on 8/21/15.
 */
public class TPBitFlip {
    public static void main(String [] args) {
        Character[] array = {
                'T',
                'P'
        };
        BitSet bitSet = new BitSet(10);
        for(int i = 0; i < 10; i++) {
            bitSet.set(i, true);
        }
        int length = bitSet.length();
        System.out.println(bitSet.cardinality() + " " + bitSet.length());
        bitSet.set(4, false);
        System.out.println(bitSet.size());
        for (int i = 0; i < length; i++) {
            int index = 0;
            if(bitSet.get(i)) {
                index = 1;
            }
            System.out.println(array[index]);
        }
        bitSet.flip(0,length);
        System.out.print("end index" + bitSet.length());
        System.out.println("flipped:");
        for (int i = 0; i < length; i++) {
            int index = 0;
            if(bitSet.get(i)) {
                index = 1;
            }
            System.out.println(array[index]);
        }

        BigInteger a = new BigInteger("10101", 2); //base 2 for binary
        BitSet aBits = BitSet.valueOf(a.toByteArray());

    }

}

