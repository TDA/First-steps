package fb_recent;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KClosestPointsFromOrigin {
    public int[][] kClosest(int[][] points, int k) {
        int[][] results = new int[k][2];
        if (k > points.length) return points;
        PriorityQueue<Point> queue = new PriorityQueue<>();

        for (int[] point: points) {
            queue.add(new Point(point[0], point[1]));
        }

        while (k > 0) {
            Point polledPoint = queue.poll();
            assert polledPoint != null;
            results[k - 1][0] = polledPoint.x;
            results[k - 1][1] = polledPoint.y;
            k--;
        }
        return results;
    }

    class Point implements Comparable<Point> {
        int x;
        int y;
        double distance;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }

        @Override
        public int compareTo(Point point) {
            return Double.compare(this.distance, point.distance);
        }
    }

    public static void main(String[] args){
        KClosestPointsFromOrigin kClosestPointsFromOrigin = new KClosestPointsFromOrigin();
        int[][] points = {{1,3},{-2,2}};
        int k = 1;
        System.out.println(Arrays.deepToString(kClosestPointsFromOrigin.kClosest(points, k)));

        int[][] points2 = {{3,3},{5,-1},{-2,4}};
        int k2 = 2;
        System.out.println(Arrays.deepToString(kClosestPointsFromOrigin.kClosest(points2, k2)));
    }
}
