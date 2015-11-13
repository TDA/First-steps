/**
 * Created by schandramouli on 11/12/15.
 */
public class MaxSubSet {
    // given an array, find the maximum continuous subset of it.
    static int[] array = {-100, 10, -10, 5, -12, 13, -16, -3000};

    public static void main(String[] args){
        int current_total = 0;
        int max = 0;

        max = array[0];
        // i dont believe this would work tho, or maybe the program has different constraints?
        for(int i = 0; i<array.length ; ++i){
            current_total+=array[i];
            if(current_total > max) max = current_total;
            // no need to check for equality, if its zero, its zero
            if(current_total < 0) current_total = 0;
        }
        System.out.println(max);
    }
}
