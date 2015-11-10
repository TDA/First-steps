import java.util.*;

/**
 * Created by schandramouli on 11/8/15.
 */
public class NextFactorWithExistingNos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[] primeFactorArray = {3, 5, 7};
        HashMap<Integer, Integer> primeFactorMapping = new HashMap<>();
        int numCopy = num;
        for (int i : primeFactorArray) {
            if (numCopy % i == 0) {
                while (numCopy % i == 0) {
                    // was a factor, update hashmap
                    if (primeFactorMapping.containsKey(i)) {
                        // update
                        primeFactorMapping.put(i, primeFactorMapping.get(i) + 1);
                    } else {
                        // set
                        primeFactorMapping.put(i, 1);
                    }
                    numCopy = numCopy / i;
                }
            } else {
                primeFactorMapping.put(i, 0);
            }
        }
        System.out.println(primeFactorMapping);
        // set an upper bound on the next number
        int worstCaseUpperBound = num * primeFactorArray[0];
        int upperBound = worstCaseUpperBound;
        System.out.printf("Next number divisible by these factors should be between %s and %s\n", num, upperBound);
        // lets try bruteforcing the prob
        // WKT, the numbers' exponents are to be changed in some way
        // so if 3^2 is given, then we can try reducing this by 1 to 3^1,
        // and increase another of the prime factors, in other words,
        // increase one factor, decrease all else. Branch and bound kinda
        // k is the max exponent to which we want to search, since this is kinda O(n2), we need to keep it small
        // basically setting a tighter upper bound can give us the best answer, with least amount of computations
        int k = (int) Math.sqrt(10);
        int x = 0;
        int computations = 0;
        while (x < k) {
            for (Map.Entry<Integer, Integer> entry : primeFactorMapping.entrySet()) {
                int newProduct = 1;
                int factor = entry.getKey();
                int newExponent = entry.getValue() + x;
                //primeFactorMapping.put(factor, newExponent);
                System.out.printf("current factor and new expo %s, %s\n", factor, newExponent);
                newProduct = newProduct * (int) Math.pow(factor, newExponent);
                for (Map.Entry<Integer, Integer> integerEntry : primeFactorMapping.entrySet()) {
                    int nextFactor = integerEntry.getKey();
                    int nextExponent = integerEntry.getValue();
                    if (entry.getKey().equals(integerEntry.getKey())) {
                        // skip calculating same numbers
                        nextExponent = 1;
                    }
                    System.out.printf("next factor and expo %s, %s\n", nextFactor, nextExponent);
                    System.out.printf("next new prod %s\n", newProduct * (int) Math.pow(nextFactor, nextExponent));
                    computations++;
                    while (newProduct * (int) Math.pow(nextFactor, nextExponent) > num && nextExponent >= 0) {
                        // do nothing, just iterate
                        --nextExponent;
                    }
                    // one step higher, cuz it would have reduced it below num
                    nextExponent++;
                    System.out.printf("next new prod %s\n", newProduct * (int) Math.pow(nextFactor, nextExponent));
                    if (newProduct * (int) Math.pow(nextFactor, nextExponent) > num && newProduct * (int) Math.pow(nextFactor, nextExponent) < upperBound) {
                        // reduce the upper bound on each iteration on finding
                        // a new num thats greater than number but less than upper bound
                        upperBound = newProduct * (int) Math.pow(nextFactor, nextExponent);
                        System.out.println("new upperbound set to " + upperBound);
                    }
                }
            }
            x++;
        }
        // this still doesnt work for 81 - 105 i think
        System.out.printf("Next number divisible by these factors should be between %s and %s\n", num, upperBound);
        System.out.println("Total computations is " + computations);

    }
    public static int[] factorize(int number) {
        ArrayList<Integer> primeFactorArrayList = new ArrayList<>();
        if (number % 2 == 0) {
            primeFactorArrayList.add(2);
        }
        for (int i = 3; i < Math.sqrt(number); i = i + 2) {
            if (number % i == 0) {
                primeFactorArrayList.add(i);
            }
        }
        int[] array = new int[primeFactorArrayList.size()];
        int i = 0;
        for (Integer integer : primeFactorArrayList) {
            array[i] = integer;
            i++;
        }
        return array;
    }
}
