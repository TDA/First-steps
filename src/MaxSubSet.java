/**
 * Created by schandramouli on 11/12/15.
 */
public class MaxSubSet {
    // given an array, find the maximum continuous subset of it.
    static int[] array = {-100, 10, -9, 13, -12, 13, -16, -3000};

    public static void main(String[] args){
        int current_total = BalancedSum.sum(array);
        // i dont believe this would work tho, or maybe the program has different constraints?
        // we need to be able to find a sum, and then check if its the most
        // lets sum the array, then keep removing -negatives from each end till we cant have anymore.
        // is bound to give a reasonably good result, except when the -negative in middle is HUGE
        int i = 0;
        while (array[i] < 0) {
            // addition by subtraction
            current_total = current_total - array[i];
            i++;
        }
        int j = array.length - 1;
        while (array[j] < 0) {
            // addition by subtraction
            current_total = current_total - array[j];
            j--;
        }

        // now we have potentially a big nice subset, now again we need to iterate
        // and remove positive+negative combinations that result in negative values
        // this will give us the answer, and its still O(n)
        while (i < j && (array[i] + array[i + 1]) < 0) {
            // addition by subtraction
            current_total = current_total - array[i] - array[i + 1];
            i = i + 2;
        }

        while (j > i && (array[j] + array[j - 1]) < 0) {
            // addition by subtraction
            current_total = current_total - array[j] - array[j - 1];
            j = j - 2;
        }

        System.out.println(current_total);
    }
}
