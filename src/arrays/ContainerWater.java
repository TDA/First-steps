package arrays;

public class ContainerWater {
    public int maxArea(int[] height) {
        int start = 0, end = height.length - 1;
        int maxArea = Integer.MIN_VALUE;
        while (start < end) {
            int maxHeightForInterval = Math.min(height[start], height[end]);
            int width = end - start;
            maxArea = Math.max(maxArea, calcArea(maxHeightForInterval, width));
            if (height[start] < height[end]) {
                start++;
            } else {
                end--;
            }
        }
        return maxArea;
    }

    int calcArea(int i, int j) {
        return (i) * (j);
    }

    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};
        ContainerWater containerWater = new ContainerWater();
        System.out.println(containerWater.maxArea(height));
    }
}
