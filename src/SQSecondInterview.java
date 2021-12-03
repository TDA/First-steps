import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//class Pancake {
//
//}
public class SQSecondInterview {
    List<Integer> pancakeSizes;
    List<Boolean> pancakeBurns;

    public SQSecondInterview(List<Integer> pancakeSizes, List<Boolean> pancakeBurns) {
        this.pancakeSizes = pancakeSizes;
        this.pancakeBurns = pancakeBurns;
    }

    public static void main(String[] args){
        SQSecondInterview sqSecondInterview = new SQSecondInterview(Arrays.asList(4, 1, 5, 3, 2), Arrays.asList(false, false, false, false, false));

        // 0-based indexing
//        sqSecondInterview.flip(2);
//        System.out.println(sqSecondInterview.pancakeSizes);
//        System.out.println(" 5 1 4 3 2");
//
//        SQSecondInterview sqSecondInterview2 = new SQSecondInterview(Arrays.asList(4, 1, 5, 3, 2));
//        sqSecondInterview2.flip(3);
//        System.out.println(sqSecondInterview2.pancakeSizes);
//        System.out.println(" 3 5 1 4 2");

        System.out.println(sqSecondInterview.pancakeSizes);
        sqSecondInterview.pancakeFlip();
        System.out.println(sqSecondInterview.pancakeSizes);

        SQSecondInterview sqSecondInterview2 = new SQSecondInterview(Arrays.asList(13, 6, 18, 9), Arrays.asList(false, false, false, false));
        sqSecondInterview2.pancakeFlip();
        System.out.println(sqSecondInterview2.pancakeSizes);

        SQSecondInterview sqSecondInterview3 = new SQSecondInterview(Arrays.asList(5, 4, 3, 2, 1), Arrays.asList(false, false, false, false, false));
        sqSecondInterview3.pancakeFlip();
        System.out.println(sqSecondInterview3.pancakeSizes);

        SQSecondInterview sqSecondInterview4 = new SQSecondInterview(Arrays.asList(5, 4, 3, 2, 1), Arrays.asList(false, false, false, true, false));
        sqSecondInterview4.pancakeFlipBurn();
        System.out.println(sqSecondInterview4.pancakeBurns);

        SQSecondInterview sqSecondInterview5 = new SQSecondInterview(Arrays.asList(5, 4, 3, 2, 1), Arrays.asList(true, true, true, true));
        sqSecondInterview5.pancakeFlipBurn();
        System.out.println(sqSecondInterview5.pancakeBurns);
        SQSecondInterview sqSecondInterview6 = new SQSecondInterview(Arrays.asList(5, 4, 3, 2, 1), Arrays.asList(true, true, true, false));
        sqSecondInterview6.pancakeFlipBurn();
        System.out.println(sqSecondInterview6.pancakeBurns);

    }

    void pancakeFlipBurn () {
        boolean isAllMatch = pancakeBurns.stream().allMatch(i -> i);
        while (!isAllMatch) {
            int pivotForFlip = findPivotForFlip(pancakeBurns);
            if (pivotForFlip != -1) {

                flipBooleans(pivotForFlip);
            }
            isAllMatch = pancakeBurns.stream().allMatch(i -> i);
            // special case, all false
            if (!isAllMatch) {
                flipBooleans(pancakeBurns.size() - 1);
            }
        }
    }

    private int findPivotForFlip(List<Boolean> pancakeBurns) {
        int startingIndex = 0;
        for (int i = 1; i < pancakeBurns.size(); i++) {
            Boolean newItemValue = pancakeBurns.get(i);
            if (newItemValue != pancakeBurns.get(startingIndex)) {
                // foudn mismatch, return start
                return startingIndex;
            }
            startingIndex = i;
        }
        return -1;
    }

    void pancakeFlip() {
        int endOfListPointer = pancakeSizes.size() - 1;
        while (endOfListPointer != 0) {
            // find largest
            int indexOfLargestNumber = findIndexOfLargestNumber(pancakeSizes, endOfListPointer);
            // This gets the biggest num to the beginning
            flip(indexOfLargestNumber);

            // flip again till endOfListPointer
            flip(endOfListPointer);
            endOfListPointer--;
        }

    }

    private int findIndexOfLargestNumber(List<Integer> pancakeSizes, int endOfListPointer) {
        int maxNumberIndex = 0;
        int currentMax = pancakeSizes.get(0);
        for (int i = 0; i <= endOfListPointer; i++) {
            if (pancakeSizes.get(i) > currentMax) {
                currentMax = pancakeSizes.get(i);
                maxNumberIndex = i;
            }
        }
        return maxNumberIndex;
    }

    private void flip(int index) {
        List<Integer> subPancakeSizesToReverse = pancakeSizes.subList(0, index + 1);
        Collections.reverse(subPancakeSizesToReverse);
    }

    private void flipBooleans(int index) {
        List<Boolean> subPancakeSizesToReverse = pancakeBurns.subList(0, index + 1);
        Collections.reverse(subPancakeSizesToReverse);
        for (int i = 0; i <= index; i++) {
            pancakeBurns.set(i, !pancakeBurns.get(i));
        }
    }
}
