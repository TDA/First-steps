package fb_recent;

public class ContiguousSum {
//    ==Question==
//    Given a sequence of integers and an integer total target, return whether a contiguous sequence of integers sums up to the target.
//
//===Example===
//            [1, 3, 1, 4, 23], 8 : True (because 3 + 1 + 4 = 8)
//[1, 3, 1, 4, 23], 7 : False
//[1, 1, 1, 1, 1], 5 : True
//[1, 1, 1, 1, 1], 0 : False // will reutrn true


    boolean containsContiguousSequence(int[] numbers, int target) {
        if (target != 0 && numbers.length == 0) return false;

        // validate at least two

        int leftIndex = 0, rightIndex = 0;
        int lastIndex = numbers.length - 1;
        int runningCounter = numbers[leftIndex];

        // left = 0
        // right = 1
        // last = 4
        // [1, 3, 1, 4, 23], 7
        // target = 7
        while (leftIndex < lastIndex) { // 4 < 4
            if (target == runningCounter) { // 7 != 26
                return true;
            }
            if (target > runningCounter) {
                rightIndex++; // 23
                if (rightIndex >= lastIndex) break;
                runningCounter += numbers[rightIndex]; // 27
            } else { //
                runningCounter -= numbers[leftIndex]; // -1
                leftIndex++;
            }
            if (!(leftIndex < rightIndex)) break;
        }
        return false;
    }

    public static void main(String[] args){
        ContiguousSum contiguousSum = new ContiguousSum();
        int[] array = {1, 3, 1, 4, 23};
        System.out.println(contiguousSum.containsContiguousSequence(array, 8));
        System.out.println(contiguousSum.containsContiguousSequence(array, 33));
        System.out.println(contiguousSum.containsContiguousSequence(array, 7));
        System.out.println(contiguousSum.containsContiguousSequence(array, 5));
        System.out.println(contiguousSum.containsContiguousSequence(array, 0));
    }
}
