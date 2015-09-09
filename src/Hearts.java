import java.util.Scanner;

/**
 * Created by schandramouli on 9/9/15.
 */
public class Hearts {

    public void drawHeart(String s1, String s2) {
        System.out.println(s1);
        System.out.println(this.generateHeart());
        System.out.println(s2);
    }

    public static String centerLine (String s, int len) {
        // utility to center a line while adding padding of spaces at both ends (not really, but yeah)
        // will be useful some other time prusse
        int spacing = (len - s.length()) / 2;
        String new_s = "";
        for (int i = 0; i < spacing; i++) {
            new_s += " ";
        }
        new_s += s;
        return new_s;
    }

    public static String cleaveLine (String s, int len) {
        // pad spaces *between* a line, like from saipc to sai   pc.
        int spacing = len - s.length();
        int space_start = s.length() / 2;
        String new_s = "";
        new_s += s.substring(0, space_start);
        for (int i = 0; i < spacing; i++) {
            new_s += " ";
        }
        new_s += s.substring(space_start, s.length());
        return new_s;
    }

    public String generateHeart() {
        String s = "";
        int length = 21;
        int height = 12;
        // generate the first line, clumsy
        s += " \u2665\u2665\u2665 ";
        // add (length - 8) spaces
        for (int i = 0; i < length - 8; i++) {
            s += " ";
        }
        s += " \u2665\u2665\u2665 ";
        s += "\n";

        // man, never thought generating a simple heart would be so difficult :\
        for (int i = 1; i < (height / 2); i++){
            String line = "";
            int j = length - (i + 1) * 4;
            if (j <= 0) {
                break;
            }
            for (; j < length; j = j + 1) {
                line = line + "\u2665";
            }
            switch (i) {
                case 1: line += "\u2665\u2665"; length = 23; break;
                case 2: line += "\u2665\u2665"; length = 23; break;
                case 3: line += "\u2665\u2665"; length = 23; break;
                case 4: line += ""; length = 23; break;
                default: length = 21;
            }
            s += cleaveLine(line, length);
            s +=  "\n";
        }

        // this is just an inverted triangle, easy right? :|
        length = 21;
        for (int i = 0; i < (height / 2); i++){
            int j = length - (i * 4);
            if (j <= 0) {
                break;
            }
            String line = "  ";
            if (i == 0) {
                line = " ";
            }
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
