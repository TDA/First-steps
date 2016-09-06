import java.util.Scanner;

/**
 * Created by schandramouli on 9/5/16.
 */
public class ChocolateCutting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int cuts = 0;
            if ((width == 0 || height == 0) || (width == 1 && height == 1)) {
                // do nothing and skip
            } else {
                if (width > 1 && height > 1) {
                    // this is the bulk of the logic,
                    // here each cut horizontally produces width-1 cuts
                    // more, so we need to sum them in that way
                    for (int j = 1; j < height; j++) {
                        // add one cut for the horizontal cut
                        cuts += 1;
                        // add width-1 cuts for vertical cuts
                        cuts += width - 1;
                    }
                    // finally add for the last row
                    cuts += width - 1;
                } else {
                    // we know one of the dimensions is 1, return the other-1
                    if (width == 1) {
                        cuts = height - 1;
                    } else {
                        cuts = width - 1;
                    }
                }
            }
            System.out.println(cuts);
        }
    }
}
