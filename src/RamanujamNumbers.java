
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/26/15.
 */
class Pair<R, R1> {

    public Pair(R r, R1 r1) {

    }
}
public class RamanujamNumbers {
    int x3;
    int y3;


    public RamanujamNumbers(int x3, int y3) {
        this.x3 = x3;
        this.y3 = y3;
    }

    public int computeSum() {
        return this.x3 + this.y3;
    }

    @Override
    public String toString() {
        return this.x3 + "," + this.y3;
    }

    // numbers which can be rep as the sum of 2 cubes, in 2 ways,
    // eg: 0,1,8,27,64,125,216,343,512,729,1000 ->
    static HashMap<Integer, Integer> hashMapSumOnes = new HashMap<>();
    static HashMap<Integer, RamanujamNumbers> hashMapSumTwos = new HashMap<>();
    static ArrayList<Pair<RamanujamNumbers, RamanujamNumbers>> RamanujamNumbers = new ArrayList<>();
    static int count;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int cube;
        int i;
        for (i = 0; (cube = i * i * i) <= n; i++) {
            // precompute a hashtable of cubes
            // System.out.println(cube);
            hashMapSumOnes.put(i, cube);
        }

        for (int j = 0; j < hashMapSumOnes.size(); j++) {
            // precompute the sums of cubes of each pair,
            // max no of computations is still phenomenally lesser than the bruteforce thing
            for (int k = j + 1; k < hashMapSumOnes.size(); k++) {
                count++;
                RamanujamNumbers ram = new RamanujamNumbers(hashMapSumOnes.get(j), hashMapSumOnes.get(k));
                int sumOfCubes = ram.computeSum();
                // now add to the second map, but check if it exists already => we found a pair.
                if (hashMapSumTwos.containsKey(sumOfCubes)) {
                    // we found a pair
                    Pair<RamanujamNumbers, RamanujamNumbers> pair = new Pair<>(ram, hashMapSumTwos.get(sumOfCubes));
                    RamanujamNumbers.add(pair);
                    // delete the key
                    hashMapSumTwos.remove(sumOfCubes);
                } else {
                    // havent seen yet, add to map
                    hashMapSumTwos.put(sumOfCubes, ram);
                }
            }
        }


        System.out.println("i is " + (i - 1));
        System.out.println(RamanujamNumbers.size());
        System.out.println(RamanujamNumbers);
        // what is the big O here?
        System.out.println(count);
    }
}
