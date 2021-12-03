package Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SieveOfEratosthenes {
    List<Integer> generatePrimes(int limit) {
        int count = 0;
        List<Integer> primes = new ArrayList<>();
        int max = limit * 20;
        boolean[] isPrimeList = new boolean[max];
        Arrays.fill(isPrimeList, true);
        int prime = 2;
        primes.add(prime);
        System.out.println(System.currentTimeMillis());
        while (prime < max) {
            for (int multiplier = 1; prime * multiplier < max; multiplier++) {
                isPrimeList[prime * multiplier] = false;
            }
            prime = findNextPrimeNumber(isPrimeList, prime);
            primes.add(prime);
            count++;
            if (count >= limit) break;
        }
        System.out.println(System.currentTimeMillis());
        return primes;
    }

    private int findNextPrimeNumber(boolean[] isPrimeList, int num) {
        for (int i = num + 1; i < isPrimeList.length; i++) {
            if (isPrimeList[i]) return i;
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args){
        SieveOfEratosthenes sieveOfEratosthenes = new SieveOfEratosthenes();
        System.out.println(sieveOfEratosthenes.generatePrimes(50000));
        System.out.println(sieveOfEratosthenes.generatePrimes(100));
    }
}
