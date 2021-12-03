package heaps;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MedianFinder {
    Queue<Integer> maxHeap; // e.g. 0 to 50, 50 on top
    Queue<Integer> minHeap; // e.g. 51 to 10, 51 on top
    boolean isEvenItems = true; // start with zero
    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        // reverse sort the first half
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    public void addNum(int num) {
        // By adding to minheap first, we know the top element is smallest in minHeap, then we move it to maxHeap
        minHeap.add(num);
        maxHeap.add(minHeap.poll());

        // finally balance them
        if (minHeap.size() < maxHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        isEvenItems = !isEvenItems;
    }

    public double findMedian() {
        if (minHeap.size() > maxHeap.size()) {
            return minHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    public static void main(String[] args){
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println(medianFinder.minHeap);
        System.out.println(medianFinder.maxHeap);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(4);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(5);
        System.out.println(medianFinder.findMedian());

    }
}
