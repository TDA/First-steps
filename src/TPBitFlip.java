import java.math.BigInteger;
import java.util.*;

/**
 * Created by schandramouli on 8/21/15.
 */
public class TPBitFlip {
    static Character[] array = {
            'T',
            'P'
    };
    public static void main(String [] args) {

        BitSet bitSet = new BitSet(10);
        for(int i = 0; i < 10; i++) {
            bitSet.set(i, true);
        }
        int length = bitSet.length();
        //System.out.println(bitSet.cardinality() + " " + bitSet.length());
        bitSet.set(4, false);
        //System.out.println(bitSet.size());
        for (int i = 0; i < length; i++) {
            int index = 0;
            if(bitSet.get(i)) {
                index = 1;
            }
            //System.out.println(array[index]);
        }
        /*
        bitSet.flip(0,length);
        System.out.print("end index" + bitSet.length());
        System.out.println("flipped:");
        for (int i = 0; i < length; i++) {
            int index = 0;
            if(bitSet.get(i)) {
                index = 1;
            }
            //System.out.println(array[index]);
        }*/

        BigInteger a = new BigInteger("10111", 2); //base 2 for binary
        BitSet aBits = BitSet.valueOf(a.toByteArray());
        int length_abits = aBits.length();
        for (int i = 0; i < length_abits; i++) {
            System.out.println(aBits.get(i));
        }
        ArrayList<BitSet> bitVector = new ArrayList<BitSet>();
        for (int i = 0; i < 10; i++) {
            BigInteger aRowBits = new BigInteger("01", 2); //base 2 for binary
            BitSet aRow = BitSet.valueOf(aRowBits.toByteArray());
            bitVector.add(i, aRow);
        }
        /*
        for (int i = 0; i < 10; i++) {
            // Create a random assignment of P's and T's to the array of bitsets
            BitSet row = bitVector.get(i);
            for (int j = 0; j < 10; j++) {
                double rand = j / 10;
                if(rand > 0.5) {
                    row.flip(j);
                }
                int index = 0;
                if (row.get(j)) {
                    index = 1;
                }
            }
        }*/
        System.out.println("Just checking");
        printVector(bitVector, 10);
        System.out.println("After flipping col 4, 6, 8");
        bitVector.get(3).flip(0, 10);
        bitVector.get(5).flip(0, 10);
        bitVector.get(7).flip(0, 10);
        printVector(bitVector, 10);

        int max = 0;
        for (int i = 1; i < 10; i++) {
            int count = 0;
            if (shouldFlipRow(bitVector, i, 0)) {
                BitSet row = bitVector.get(i);
                row.flip(0, 10);
                for (int j = 0; j < 10; j++) {
                    if (isColumnSame(bitVector, j)) {
                        count++;
                    }
                }
                max = Integer.max(max, count);
                System.out.println("Flipped row " + (i + 1));
                System.out.println("No of same columns" + max);
                printVector(bitVector, 10);
                if(max == 10) {
                    break;
                }
            }
        }
        //isColumnSame(bitVector, 3);
    }
    static boolean shouldFlipRow(ArrayList<BitSet> bitVector, int i, int j) {
        // check if row should be flipped or not
        // do this by checking for existing columns, if
        // they match to a certain extent, and this row is the first
        // one thats not the same as before it.
        boolean bool = false;
        boolean trues_or_falses = bitVector.get(0).get(j);
        int count = 0;
        if (i > 1) {
            for (int k = 1; k <= i; k++) {
                BitSet row = bitVector.get(k);
                if (row.get(j) == trues_or_falses) {
                    count++;
                }
            }
        } else {
            if (bitVector.get(i).get(j) == trues_or_falses) {
                return false;
            }
            else {
                return true;
            }
        }
        if (count == i) {
            bool = false;
        } else {
            bool = true;
        }
        return bool;
    }
    static boolean isColumnSame(ArrayList<BitSet> bitVector, int column) {
        int size = bitVector.size();
        boolean bool = false;
        boolean trues_or_falses = bitVector.get(0).get(column);
        //System.out.println(trues_or_falses + " is the first in the column");
        for (int i = 1; i < size; i++) {
            BitSet row = bitVector.get(i);
            //System.out.println(row.get(column) + " is the next in the column");
            if(row.get(column) == trues_or_falses){
                bool = true;
            } else {
                bool = false;
                break;
            }
        }
        return bool;
    }
    static void printVector(ArrayList<BitSet> bitVector, int size) {
        for (int i = 0; i < size; i++) {
            BitSet row = bitVector.get(i);
            for (int j = 0; j < size; j++) {
                int index = 0;
                if (row.get(j)) {
                    index = 1;
                }
                System.out.print(" " + array[index] + " ");

            }
            System.out.println();
        }
    }
}

