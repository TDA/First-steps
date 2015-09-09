import java.util.Scanner;

/**
 * Created by schandramouli on 9/9/15.
 */
public class Hearts {

    public void drawHeart(String s1, String s2) {
        System.out.println(s1);
        System.out.println(this.generateheart());
        System.out.println(s2);
    }

    public String generateheart() {
        String s = "";
        int length = 23;
        int height = 10;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < length; j++) {
                s +=  "\u2665";
            }
            s +=  "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        String s1 = "";
        String s2 = "";
        Scanner scanner = new Scanner(System.in);
        s1 = scanner.nextLine();
        s2 = scanner.nextLine();

        new Hearts().drawHeart(s1, s2);

    }
}
