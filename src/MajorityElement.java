import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int floor = nums.length / 2;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > floor)
                return entry.getKey();
        }
        return -1;
    }
    public static int findMajority(int[] nums)
    {
        int count = 0, candidate = -1;
        // Finding majority candidate
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else {
                if (num == candidate)
                    count++;
                else
                    count--;
            }
        }
        count = 0;
        for (int num : nums) {
            if (num == candidate)
                count++;
        }
        if (count > (nums.length / 2))
            return candidate;
        return -1;
    }

    // Driver code
    public static void main(String[] args)
    {
        int arr[] = { 4, 1, 2, 3, 4, 1, 1, 1, 1, 1, 4 };
        int majority = findMajority(arr);
        System.out.println(" The majority element is : "
                + majority);
    }
}
