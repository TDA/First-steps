const mergeIntervals = function(intervals = [[]]) {
    intervals.sort((a,b) => a[0] - b[0]);
    if (intervals.length <= 1) {
        return intervals;
    }
    let index = 1;
    const finalIntervals = [];

    let previousInterval = [...intervals[0]];
    while (index < intervals.length) {
        while (intervals[index] && previousInterval[1] >= intervals[index][0]) {
            // update the counters for overlaps
            previousInterval[0] = Math.min(intervals[index][0], previousInterval[0])
            previousInterval[1] = Math.max(intervals[index][1], previousInterval[1])
            index++;
        }
        console.log("After loop: ", previousInterval)
        finalIntervals.push([...previousInterval]);
        if (index !== intervals.length) {
            previousInterval = [...intervals[index]]
        }
    }
    
    return finalIntervals;
}

let intervals = [[1,3],[2,6],[8,10],[15,18]]
let Output = [[1,6],[8,10],[15,18]]
console.log("Actual", mergeIntervals(intervals))
console.log("Expected", Output)
console.log("-------------------------------------")
let intervals2 = [[1,3],[4,5], [5, 6], [6, 7], [100, 101]]
let Output2 = [[1,3],[4, 7], [100, 101]]
console.log("Actual", mergeIntervals(intervals2))
console.log("Expected", Output2)
console.log("-------------------------------------")
let intervals3 = [[1,4],[4,5], [5, 6], [6, 7]]
let Output3 = [[1,7]]
console.log("Actual", mergeIntervals(intervals3))
console.log("Expected", Output3)
