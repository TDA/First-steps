const _ = require('lodash');

const libEraseOverlapIntervals = function (intervals) {
    intervals.sort((a,b) => a[1] - b[1]);
    console.log(intervals)
    const x = _.intersectionWith([...intervals], (a1, a2) => {
        console.log(a1, a2)
        if (a1[0] > a2[1]) return true
    })

    console.log(x)
    console.log(intervals)
}

// Meeting room problem is very similar, need to sort on start
const  eraseOverlapIntervals = function(intervals) {
    intervals.sort((a,b) => a[1] - b[1]);
    if (intervals.length <= 1) {
        return 0;
    }
    const finalIntervals = [[...intervals[0]]];
    let counter = -1;

    for (let [start, end] of intervals) {
        console.log(start, end)
        if (start < finalIntervals[finalIntervals.length - 1][1]) {
            // overlap
            counter++;
        } else { 
            // no overlap, add to list and move on
            finalIntervals.push([start, end])
        }
    }
    console.log(finalIntervals)
    return counter;
};

const intervals = [[1,2],[2,3],[3,4],[1,3]]
console.log(eraseOverlapIntervals(intervals))
const intervals2 = [[1,2],[1,2],[1,2]]
console.log(eraseOverlapIntervals(intervals2))
const intervals3 = [[1,2],[2,3]]
console.log(eraseOverlapIntervals(intervals3))
const intervals4 = [[1,2]]
console.log(eraseOverlapIntervals(intervals4))
const intervals5 = [[1,100],[11,22],[1,11],[2,12]]
console.log(eraseOverlapIntervals(intervals5))
const intervals6 = [[0,30],[5,10],[15,20]]
console.log(eraseOverlapIntervals(intervals6))
console.log("--------------------------")
console.log(libEraseOverlapIntervals(intervals6))
