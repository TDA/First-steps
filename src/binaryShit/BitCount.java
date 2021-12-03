package binaryShit;

public class BitCount {
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            // Get the ones digit
            count += n & 1;
            // Shift n
            n = n >>> 1;
        }
        return count;
    }

    public static void main(String[] args) {
        BitCount bitCount = new BitCount();
        System.out.println(bitCount.hammingWeight(2));
        System.out.println(bitCount.hammingWeight(3));
        System.out.println(bitCount.hammingWeight(4));
        System.out.println(bitCount.hammingWeight(5));
        System.out.println(bitCount.hammingWeight(8));
        System.out.println(bitCount.hammingWeight(16));
        System.out.println(bitCount.hammingWeight(32));
        System.out.println(bitCount.hammingWeight(Integer.MAX_VALUE));
    }
}
