function twoSum(nums, target) {
    sumMap = {};
    for (i = 0; i < nums.length; i++) {
        num = nums[i];
        if (sumMap[target - num] !== undefined) {
            return [sumMap[target - num], i];
        }
        sumMap[num] = i;
    }
    return [];
}

console.log(twoSum([2,7,11,15], 9));