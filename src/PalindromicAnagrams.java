import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 10/7/15.
 */
public class PalindromicAnagrams {
    public static void main(String args[] ) throws Exception {

        // assuming linux fs and location
        File f1 = new File("src/input.txt");
        File f2 = new File("src/output.txt");
        BufferedReader buff = new BufferedReader(new FileReader(f1));
        BufferedWriter buff2 = new BufferedWriter(new FileWriter(f2));
        String a;
        while ((a = buff.readLine()) != null) {
            Boolean flag = false;
            Map<Character, Integer> map = new HashMap<Character, Integer>();

            // hashmap ftw!
            for (int i = 0; i < a.length(); i++) {
                char key = a.charAt(i);
                if (map.containsKey(key)) {
                    int x = map.get(key);
                    map.put(key, ++x);
                } else {
                    map.put(key, 1);
                }
            }

            int noOfOddChars = 0;
            int noOfEvenChars = 0;
            int noOfPals = 0;
            for (Character character : map.keySet()) {
                if (!(map.get(character) % 2 == 0)) {
                    // check how many chars are odd occurences
                    noOfOddChars++;
                } else {
                    // this contains the no of characters that occur even no of times and can be permuted
                    noOfEvenChars++;
                }
            }
            String str = "";
            if (noOfOddChars > 1) {
                // Not a palindrome, and noOfOddChars - 1 is the min no of elements
                // we need to remove, a palindrome can have at most one odd occuring elt :)
                str += (noOfOddChars - 1);
            } else {
                str += "0";
            }
            // calc the permutations

            noOfPals = findFactorial(noOfEvenChars);
            str += "," + noOfPals;
            // System.out.println(map);
            System.out.println(str);
            Integer[] array = new Integer[map.size()];
            int x = 0;
            for(Integer i :map.values()) {
                array[x] = i;
                x++;
            }
            //System.

            buff2.write(str + "\n");
        }
        buff.close();
        buff2.close();
    }

    public static int findFactorial(int x) {
        if (x <= 1)
            return 1;
        else
            return x * findFactorial(x-1);
    }
}
