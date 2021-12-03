package fb_recent;

public class SQRT {
    // Can use BS!!!
    public int mySqrt(int x) {
        int i = 1;
        for (; i <= x / 2; i++) {
            if (i > (x / i)) break;
        }
        if (i == x / i) return i;
        return i - 1;
    }

    public static void main(String[] args){
        SQRT sqrt = new SQRT();
        System.out.println(sqrt.mySqrt(2147395600));
    }
}
