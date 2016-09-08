import java.awt.*;

/**
 * Created by schandramouli on 9/7/16.
 */
public class Triangle {
    Point v1;
    Point v2;
    Point v3;
    float area;

    public Triangle(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.area = computeArea(v1, v2, v3);
        System.out.println("Area of triangle " + area);
    }

    public static void main(String[] args) {
        Triangle triangle = new Triangle(new Point(1,2), new Point(5,2), new Point(3,4));
        Point p1 = new Point(2,4);
        Point p2 = new Point(6,3);
        System.out.println(triangle.isPointInside(p1));
        System.out.println(triangle.isPointInside(p2));
    }

    public boolean isPointInside(Point p) {
        float t1Area = computeArea(v1, v2, p);
        float t2Area = computeArea(v1, p, v3);
        float t3Area = computeArea(p, v2, v3);
        float sumOfAreas = t1Area + t2Area + t3Area;
        return sumOfAreas == area;
    }

    public static float computeArea(Point point1, Point point2, Point point3) {
        float x1 = point1.x;
        float y1 = point1.y;

        float x2 = point2.x;
        float y2 = point2.y;

        float x3 = point3.x;
        float y3 = point3.y;

        float area = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))/2;
        System.out.println(area);
        return area;
    }
}
