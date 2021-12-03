package arrays;

public class RotatedSortedArray {
    public int findMin(int[] nums) {
        return modifiedBinarySearch(nums, 0, nums.length - 1);
    }

    private int modifiedBinarySearch(int[] nums, int leftIndex, int rightIndex) {
        int midPoint = (leftIndex + rightIndex) / 2;
        System.out.println("Left, right, mid" + " " + leftIndex+ " " + rightIndex+ " " + midPoint);
        if (nums[midPoint] > nums[rightIndex]) {
            System.out.println("moving right");
            return modifiedBinarySearch(nums, midPoint + 1, rightIndex);
        } else if (nums[midPoint] < nums[leftIndex]) {
            System.out.println("moving left");
            return modifiedBinarySearch(nums, leftIndex, midPoint);
        } else {
            return nums[leftIndex];
        }
    }

    public static void main(String[] args) {
        RotatedSortedArray rotatedSortedArray = new RotatedSortedArray();
        int[] nums = {3,4,5,1,2};
        int[] nums2 = {4,5,6,7,0,1,2};

        System.out.println(rotatedSortedArray.findMin(nums));;
        System.out.println(rotatedSortedArray.findMin(nums2));;
    }
}


