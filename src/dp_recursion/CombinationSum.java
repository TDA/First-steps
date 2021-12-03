package dp_recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(results, new ArrayList<>(), candidates, target, 0);
        return  results;
    }

    private void backtrack(List<List<Integer>> results, ArrayList<Integer> tempList, int[] candidates, int target, int start) {
        if (target < 0) {
            // cut out backtrack
            return;
        } else if (target == 0) {
            // found one, add
            results.add(new ArrayList<>(tempList));
        } else {
            // continue backtrack
            for (int i = start; i < candidates.length; i++) {
                int candidate = candidates[i];
                tempList.add(candidate);
                backtrack(results, tempList, candidates, target - candidate, i);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack2(results, new ArrayList<>(), candidates, target, 0);
        return results;
    }

    private void backtrack2(List<List<Integer>> results, ArrayList<Integer> tempList, int[] candidates, int target, int start) {
        if (target < 0) return;
        else if (target == 0) results.add(new ArrayList<>(tempList));
        else {
            for (int i = start; i < candidates.length && candidates[i] <= target; i++) {
                if (i > start && candidates[i] == candidates[i - 1]) continue;
                int candidate = candidates[i];
                tempList.add(candidate);
                backtrack2(results, tempList, candidates, target - candidate, i + 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    public int combinationSum4(int[] nums, int target) {
        int[] combinations = new int[target + 1];
        combinations[0] = 1;
        
        for (int i = 1; i < combinations.length; i++) {
            for (int num : nums) {
                if (i - num >= 0) {
                    combinations[i] += combinations[i - num];
                }
            }
        }
        System.out.println(Arrays.toString(combinations));
        return combinations[target];
    }

    public static void main(String[] args){
        CombinationSum combinationSum = new CombinationSum();
        int[] nums = {1,2,3};
        System.out.println(combinationSum.combinationSum4(nums, 4));
        System.out.println(combinationSum.combinationSum(nums, 4));
        System.out.println(combinationSum.combinationSum2(new int[] {10,1,2,7,6,1,5}, 8));
    }
}
