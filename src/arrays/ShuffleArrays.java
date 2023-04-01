package arrays;

import java.util.Arrays;
import java.util.Random;

public class ShuffleArrays {
    private final int[] originalArray;
    private Random rng;
    public ShuffleArrays(int[] nums) {
        this.originalArray = nums.clone();
        rng = new Random();
    }

    public int[] reset() {
        return this.originalArray;
    }

    public int[] shuffle() {
        int length = this.originalArray.length;
        int[] shuffledArray = this.originalArray.clone();
        for (int i = 1; i < length; i++) {
            int jthPosition = rng.nextInt(i + 1);
            int temp = shuffledArray[i];
            shuffledArray[i] = shuffledArray[jthPosition];
            shuffledArray[jthPosition] = temp;
        }
        return shuffledArray;
    }

    public static void main(String[] args) {
        int[] array = new int[] {1, 2, 3, 4, 5};
        ShuffleArrays arrays = new ShuffleArrays(array);
        System.out.println(Arrays.toString(arrays.shuffle()));

    }
}
