package dp_recursion;

import java.util.Arrays;



public class ContiguousSubArrays {
    int[] countSubarrays(int[] arr) {
        int[] directions = {-1, 1};
        int[] counts = new int[arr.length];
        Arrays.fill(counts, 1);
        // Write your code here
        int maxNumSeenSoFar = arr[0];
        int maxNumIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            int direction =  directions[1];
            int j = i;

            if (maxNumSeenSoFar < arr[i]) {
                counts[i] = i + 1;
            } else if (maxNumSeenSoFar > arr[i]) {
                counts[i] = i - maxNumIndex;
            }
            while (j + direction < arr.length && arr[j + direction] < arr[i]) {
                System.out.println("Checking arr[j + direction] < arr[j]  " + arr[j + direction] + "  " +  arr[i]);
                counts[i]++;
                if (arr[i] > maxNumSeenSoFar) {
                    maxNumSeenSoFar = arr[i];
                    maxNumIndex = i;
                }
                j = j + direction;
            }
            System.out.println("Max " + maxNumSeenSoFar + "  " + maxNumIndex);
        }

        System.out.println(Arrays.toString(counts));
        for (int i = arr.length - 1; i > 0; i--) {
            int direction =  directions[0];
            int j = i;

            while (j + direction > 0 && arr[j + direction] < arr[i]) {
                j = j + direction;
            }
            if (j > maxNumIndex  + 1) {
                // adjust for local mins
                System.out.println(j + "  " + (maxNumIndex+1));
                System.out.println(j - (maxNumIndex + 1) );
                counts[i] = counts[i] - (j - (maxNumIndex + 1));
            } else {
                // we can break after first element if the max on left was matched correctly
                break;
            }
        }

        System.out.println(Arrays.toString(counts));
        return counts;
    }


    // These are the tests we use to determine if the solution is correct.
    // You can add your own at the bottom.
    int test_case_number = 1;
    void check(int[] expected, int[] output) {
        int expected_size = expected.length;
        int output_size = output.length;
        boolean result = true;
        if (expected_size != output_size) {
            result = false;
        }
        for (int i = 0; i < Math.min(expected_size, output_size); i++) {
            result &= (output[i] == expected[i]);
        }
        char rightTick = '\u2713';
        char wrongTick = '\u2717';
        if (result) {
            System.out.println(rightTick + " Test #" + test_case_number);
        }
        else {
            System.out.print(wrongTick + " Test #" + test_case_number + ": Expected ");
            printIntegerArray(expected);
            System.out.print(" Your output: ");
            printIntegerArray(output);
            System.out.println();
        }
        test_case_number++;
    }
    void printIntegerArray(int[] arr) {
        int len = arr.length;
        System.out.print("[");
        for(int i = 0; i < len; i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(arr[i]);
        }
        System.out.print("]");
    }
    public void run() {
        int[] test_1 = {3, 4, 1, 6, 2};
        int[] expected_1 = {1, 3, 1, 5, 1};
        int[] output_1 = countSubarrays(test_1);
        check(expected_1, output_1);

        int[] test_2 = {2, 4, 7, 1, 5, 3};
        int[] expected_2 = {1, 2, 6, 1, 3, 1};
        int[] output_2 = countSubarrays(test_2);
        check(expected_2, output_2);

        int[] test_3 = {1, 7, 1, 5, 1, 3};
        int[] expected_3 = {1, 6, 1, 4, 1, 2};
        int[] output_3 = countSubarrays(test_3);
        check(expected_3, output_3);

        // Add your own test cases here

    }
    public static void main(String[] args) {
        new ContiguousSubArrays().run();
    }
}
