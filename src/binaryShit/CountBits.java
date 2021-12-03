package binaryShit;

public class CountBits {
    public int[] countBits(int n) {
        int[] solnArray = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            solnArray[i] = hammingWeight(i);
        }
        return solnArray;
    }

    int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            // Get the ones digit
            count += n & 1;
            // Shift n
            n = n >>> 1;
        }
        return count;
    }
}
