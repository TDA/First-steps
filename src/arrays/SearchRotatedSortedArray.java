package arrays;

public class SearchRotatedSortedArray {
    public int search(int[] nums, int target) {
        return modifiedBinarySearch(nums, 0, nums.length - 1, target);
    }

    private int modifiedBinarySearch(int[] nums, int leftIndex, int rightIndex, int target) {
        int midPoint = (leftIndex + rightIndex) / 2;
//        System.out.println(String.format("Left %s, right %s , mid %s", leftIndex, rightIndex, midPoint));

        if (leftIndex > rightIndex) {
            return -1;
        }

        if (target == nums[midPoint]) {
            return midPoint;
        }


        if (nums[midPoint] <= nums[rightIndex]) {
            if (target > nums[rightIndex] || target < nums[midPoint]) {
//                System.out.println("moving left");
                return modifiedBinarySearch(nums, leftIndex, midPoint - 1, target);
            } else {
//                System.out.println("moving right");
                return modifiedBinarySearch(nums, midPoint + 1, rightIndex, target);
            }
        } else {
            if (nums[leftIndex] > target || target > nums[midPoint]) {
//                System.out.println("moving right");
                return modifiedBinarySearch(nums, midPoint + 1, rightIndex, target);
            } else {
//                System.out.println("moving left");
                return modifiedBinarySearch(nums, leftIndex, midPoint - 1, target);
            }
        }
    }

    public static void main(String[] args) {
        SearchRotatedSortedArray rotatedSortedArray = new SearchRotatedSortedArray();
        int[] nums = {3,4,5,1,2};
        int[] nums2 = {4,5,6,7,0,1,2};
        int[] nums3 = {1,3};
        int[] nums4 = {1,2,3,4,5,6};

        System.out.println(rotatedSortedArray.search(nums, 22));;
        System.out.println(rotatedSortedArray.search(nums2, 6));;
        System.out.println(rotatedSortedArray.search(nums2, 0));;
        System.out.println(rotatedSortedArray.search(nums4, 4));;
        System.out.println(rotatedSortedArray.search(nums3, 3));;
    }
}



