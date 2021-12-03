package fb_recent;

import java.util.HashMap;
import java.util.Map;

public class SparseVector {
    Map<Integer, Integer> vectorValueMap = new HashMap<>();
    SparseVector(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num != 0)
                vectorValueMap.put(i, num);
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        if (vec.vectorValueMap.size() < this.vectorValueMap.size()) return vec.dotProduct(this);
        int dotProduct = 0;
        for (Integer index : this.vectorValueMap.keySet()) {
            if (vec.vectorValueMap.containsKey(index)) {
                dotProduct += this.vectorValueMap.get(index) * (vec.vectorValueMap.get(index));
            }
        }
        return dotProduct;
    }

    public static void main(String[] args){

    }
}
