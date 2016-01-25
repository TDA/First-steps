/**
 * Created by schandramouli on 1/25/16.
 */
public class ReverseNumbers {

    public static int reverse(int x) {
        if (x == 0) {
            return 0;
        }

        int q;
        int flag = 0;
        if (x < 0) {
            flag = 1;
            q = -x;
        } else {
            q = x;
        }

        StringBuilder sb = new StringBuilder();
        while (q != 0) {
            int r = q % 10;
            sb.append(r);
            q = q / 10;
        }
        try {
            int y = Integer.parseInt(sb.toString());

            if (flag == 1) {
                y = -y;
            }
            return y;
        } catch (NumberFormatException e) {
            return 0;
        }

    }
    public static void main(String[] args) {
        int x = 1534236469;
        System.out.println(reverse(x));
    }
}
