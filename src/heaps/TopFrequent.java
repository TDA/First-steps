package heaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        int[] ints = new int[k];
        if (k > nums.length) return ints;
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        System.out.println(frequencyMap);
        Queue<Tuple> prio = new PriorityQueue<>((Comparator.comparingInt(tuple -> -tuple.frequency)));
        for (int key: frequencyMap.keySet()) {
            Tuple tuple = new Tuple(frequencyMap.get(key), key);
            prio.add(tuple);
        }
        System.out.println(prio);

        for (int i = 0; i < k; i++) {
            ints[i] = prio.poll().value;
        }
        return ints;
    }

    public static void main(String[] args){
        TopFrequent topFrequent = new TopFrequent();
        int[] nums = {1,1,1,2,2,3}; int k = 2;
        System.out.println(Arrays.toString(topFrequent.topKFrequent(nums, k)));
    }

    private class Tuple {
        int frequency;
        int value;

        public Tuple(int frequency, int value) {
            this.frequency = frequency;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "frequency=" + frequency +
                    ", value=" + value +
                    '}';
        }
    }
}
