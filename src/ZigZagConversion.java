import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ZigZagConversion {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        StringBuilder result = new StringBuilder();
        Queue<Character> charactersLeft = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            charactersLeft.add(c);
        }
        char[][] mat = new char[numRows][s.length() / numRows + numRows];
        // Loop here

        boolean zig = true;
        int i = 0;
        int j = -1;
        while (charactersLeft.peek() != null) {
            while (i <= numRows) {
                // do something
                if (charactersLeft.peek() == null) break;
//                i = Math.max(0, i);
                if (i == numRows) {
                    zig = false;
                    i = i - 2;
                    j++;
                } else if (i == 0) {
                    zig = true;
                    j++;
                } else if (i < 0) {
                    // outlier for 2 ranges
                    i = i + 2;
                }
                char e = charactersLeft.poll();
                if (zig) {
                    // zig move
                    mat[i++][j] = e;
                } else {
                    // zag move
                    mat[i--][j] = e;
                }
            }
        }
        System.out.println(Arrays.deepToString(mat));
        for (char[] chars : mat) {
            for (int l = 0; l < mat[0].length; l++) {
                if ((int) chars[l] != 0)
                    result.append(chars[l]);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(new ZigZagConversion().convert("PAYPALISHIRING", 3));
        System.out.println(new ZigZagConversion().convert("PAYPALISHIRING", 4));
        System.out.println(new ZigZagConversion().convert("ABCD", 2));
    }
}
