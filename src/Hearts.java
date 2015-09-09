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

    public static String centerLine(String s, int len) {
        int spacing = (len - s.length()) / 2;
        String new_s = "";
        for (int i = 0; i < spacing; i++) {
            new_s += " ";
        }
        new_s += s;
        return new_s;
    }

    public String generateheart() {
        String s = "";
        int length = 21;
        int height = 12;
        for (int i = 0; i < (height / 2) - 2; i++){
            for (int j = 0; j < length; j++) {
                s +=  "\u2665";
            }
            s +=  "\n";
        }

        for (int i = 0; i < height / 2; i++){
            int j = length - (i * 4);
            String line = "";
            for (;j > 0; j--) {
                line = line + "\u2665";
            }
            // line and length anyone? :D
            s += centerLine(line, length);
            s +=  "\n";
        }


        return s;
    }

    public static void main(String[] args) {
        String s1 = "";
        String s2 = "";
        Scanner scanner = new Scanner(System.in);
        //s1 = scanner.nextLine();
        //s2 = scanner.nextLine();
        s1 = args[0];
        s2 = args[1];

        new Hearts().drawHeart(s1, s2);

    }
}
