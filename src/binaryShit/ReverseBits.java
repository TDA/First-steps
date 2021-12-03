package binaryShit;

public class ReverseBits {
    public static void main(String[] args){
        ReverseBits reverseBits = new ReverseBits();
        int number = 43261596;
        System.out.println(reverseBits.reverse(number));
    }

    private int reverse(int number) {
        int count = 0;
        while (number != 0) {
            // Get the ones digit
            count += number & 1;
            // Shift n
            number = number >>> 1;
            // Shift count
            count = count << 1;
        }
        return count;
    }

    public int reverseBits(int n) {
        // lift then shift
        int output = 0;
        for (int i = 0; i < 32;i++) {
            // Shift output
            output = output << 1;
            // Get the ones digit
            output += n & 1;
            // Shift n
            n = n >> 1;

        }
        return output;
    }
}
