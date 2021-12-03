package fb_recent;

public class MinimumRotatedSortedArray {
    public int search(int[] nums) {
        int[] min = {Integer.MAX_VALUE};
        return modifiedBinarySearch(nums, 0, nums.length - 1, min);
    }

    private int modifiedBinarySearch(int[] nums, int leftIndex, int rightIndex, int[] min) {
        int midPoint = (leftIndex + rightIndex) / 2;
//        System.out.println(String.format("Left %s, right %s , mid %s", leftIndex, rightIndex, midPoint));

        if (leftIndex > rightIndex) {
            return min[0];
        }

        min[0] = Math.min(nums[midPoint], min[0]);

        if (nums[midPoint] < nums[leftIndex]) {
            return modifiedBinarySearch(nums, leftIndex, midPoint - 1, min);
        }
        if (nums[midPoint] <= nums[rightIndex] && nums[midPoint] > nums[leftIndex]) {
            return modifiedBinarySearch(nums, leftIndex, midPoint - 1, min);
        }
        if (nums[midPoint] == nums[rightIndex] && nums[midPoint] == nums[leftIndex]) {
            return Math.min(modifiedBinarySearch(nums, leftIndex, midPoint - 1, min), modifiedBinarySearch(nums, midPoint + 1, rightIndex, min));
        }
        return modifiedBinarySearch(nums, midPoint + 1, rightIndex, min);
    }

    public static void main(String[] args){
        MinimumRotatedSortedArray minimumRotatedSortedArray = new MinimumRotatedSortedArray();
        int[] nums = {3,4,5,1,2};
        int[] nums2 = {4,5,6,7,0,1,2};
        int[] nums3 = {1,3};
        int[] nums4 = {1,2,3,4,5,6};
        int[] nums5 = {3,3,1,3};
        int[] nums6 = {3,1,3,3,3};
        int[] nums7 = {3,3,3,1};
        int[] nums8 = {3,3,3,1,3};
        int[] nums9 = {3,3,3,3,3};
        int[] nums10 = {1,3,3,3,3};
        int[] nums11 = {2,0,1,1,1};

        System.out.println(minimumRotatedSortedArray.search(nums));;
        System.out.println(minimumRotatedSortedArray.search(nums2));;
        System.out.println(minimumRotatedSortedArray.search(nums3));;
        System.out.println(minimumRotatedSortedArray.search(nums4));;
        System.out.println(minimumRotatedSortedArray.search(nums5));;
        System.out.println(minimumRotatedSortedArray.search(nums6));;
        System.out.println(minimumRotatedSortedArray.search(nums7));;
        System.out.println(minimumRotatedSortedArray.search(nums8));;
        System.out.println(minimumRotatedSortedArray.search(nums9));;
        System.out.println(minimumRotatedSortedArray.search(nums10));;
        System.out.println(minimumRotatedSortedArray.search(nums11));;
    }

}
