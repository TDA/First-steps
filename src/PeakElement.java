public class PeakElement {
    public int findPeakElement(int[] nums) {
        return findPeakElementHelper(nums, 0, nums.length - 1);
    }

    private int findPeakElementHelper(int[] nums, int startIndex, int endIndex) {
        int length = endIndex - startIndex + 1;
        if (length <= 1) return endIndex;
        if (nums[startIndex] > nums[startIndex + 1]) return startIndex;
        if (nums[endIndex] > nums[endIndex - 1]) return endIndex;

        int mid = (startIndex + endIndex) / 2;
        if (nums[mid] > nums[mid + 1]) {
            // search left side (now right is strictly lower)
            return findPeakElementHelper(nums, startIndex+1, mid);
        } else {
            return findPeakElementHelper(nums, mid, endIndex - 1);
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[] {3,2,1,2,3};
        System.out.println(new PeakElement().findPeakElement(nums));
        int[] nums2 = new int[] {1,2,1,3,5,6,4};
        System.out.println(new PeakElement().findPeakElement(nums2));
    }
}
