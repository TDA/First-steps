function insertIntervals(intervals = [[]], newInterval = []) {
    const [startTime, endTime] = newInterval;
    const finalIntervals = [];
    let index = 0;
    // copy while no overlaps
    while(index < intervals.length && startTime > intervals[index][1]) {
        finalIntervals.push(intervals[index]);
        index++;
    }
    const intervalToInsert = [startTime, endTime];
    
    // move pointer to end of overlaps
    while(index < intervals.length && intervals[index][0] <= endTime) {
        intervalToInsert[0] = Math.min(intervals[index][0], intervalToInsert[0]);
        intervalToInsert[1] = Math.max(intervals[index][1], intervalToInsert[1]);
        index++;
    }

    // add the overlapping area
    finalIntervals.push(intervalToInsert);

    // copy rest
    while(index < intervals.length) {
        finalIntervals.push(intervals[index++]);
    }

    return finalIntervals;
}

let intervals = [[1,3], [6,9]];
let newInterval = [2,5];
console.log(insertIntervals(intervals, newInterval));
console.log([[1, 5], [6, 9]]);
console.log("-----------------------------------------------");
let intervals2 = [[1,2], [3,5], [6,7], [8,10], [12,16]];
let newInterval2 = [4,8];
console.log(insertIntervals(intervals2, newInterval2));
console.log([[1, 2], [3, 10], [12, 16]]);
console.log("-----------------------------------------------");
let intervals3 = [];
let newInterval3 = [5,7];
console.log(insertIntervals(intervals3, newInterval3));
console.log([[5, 7]]);
console.log("-----------------------------------------------");
let intervals4 = [[1,5]];
let newInterval4 = [2,3];
console.log(insertIntervals(intervals4, newInterval4));
console.log([[1, 5]]);
console.log("-----------------------------------------------");
let intervals5 = [[1,5]];
let newInterval5 = [2,7];
console.log(insertIntervals(intervals5, newInterval5));
console.log([[1, 7]]);
console.log("-----------------------------------------------");
let intervals6 = [[1,5]];
let newInterval6 = [1,7];
console.log(insertIntervals(intervals6, newInterval6));
console.log([[1, 7]]);
console.log("-----------------------------------------------");
let intervals7 = [[4,5]];
let newInterval7 = [1,3];
console.log(insertIntervals(intervals7, newInterval7));
console.log([[1, 3], [4, 5]]);
console.log("-----------------------------------------------");