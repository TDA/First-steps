public class SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        return modifiedBS(nums, target, 0, nums.length);
    }

    private int modifiedBS(int[] nums, int target, int start, int end) {
        if (start == end) return start;
        int mid = (start + end)/2;
        if (nums[mid] == target)
            return mid;
        else if (nums[mid] > target) {
            end = mid;
        } else {
            start = mid + 1;
        }
        return modifiedBS(nums, target, start, end);
    }

    public static void main(String[] args) {
        System.out.println(new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 2));
        System.out.println(new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 7));
    }
}
