package fb_recent;

public class BadVersionControl {
    private int inputBad;

    public BadVersionControl(int i) {
        inputBad = i;
    }

    public int firstBadVersion(int n) {
        int mostRecentBad = n;
        // modified bs
        int left = 1, right = n;
        while (left < right) {
            int mid = (right - left) /2 + left;
            if (isBadVersion(mid)) {
                // record and move left
                mostRecentBad = mid;
                right = mid;
            } else {
                // move to the right
                left = mid + 1;
            }

        }
        return mostRecentBad;
    }

    private boolean isBadVersion(int mid) {
        return mid >= inputBad;
    }

    public static void main(String[] args){
        BadVersionControl badVersionControl = new BadVersionControl(4);
        System.out.println(badVersionControl.firstBadVersion(5));

        BadVersionControl badVersionControl2 = new BadVersionControl(1);
        System.out.println(badVersionControl2.firstBadVersion(1));

        BadVersionControl badVersionControl3 = new BadVersionControl(1);
        System.out.println(badVersionControl3.firstBadVersion(2));

        BadVersionControl badVersionControl4 = new BadVersionControl(1_702_766_719);
        System.out.println(badVersionControl4.firstBadVersion(2126753390));
    }
}
