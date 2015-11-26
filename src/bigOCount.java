import java.io.*;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/26/15.
 */
enum TYPES {
    STATEMENT,
    LOOP,
    COMMENT,
    EMPTY
}
public class bigOCount {
    // for each program fed in as input, calculate the big o running complexity
    // this is a simple one that doesnt think much about loops
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(System.in);
        File f1 = new File("src/bignumbers.java");
        BufferedReader buff = new BufferedReader(new FileReader(f1));
        int count = 0;
        String line;
        while ((line = buff.readLine()) != null) {
            // read in line by line
            switch (findType(line)) {
                case STATEMENT: count++;
                    break;
                case LOOP:
                    // add logic here to find the amount of looping required, nested loops etc
                    break;
                case COMMENT:
                    // do nothing
                    break;
            }
        }
        System.out.println("Big o was " + count);
    }

    public static TYPES findType(String line) {
        // java and its double slashes :|
        System.out.println(line);
        // skip empty lines
        if (line.matches("^\\s*$")) {
            return TYPES.EMPTY;
        }

        // ignore comments
        if (line.matches("^(//|/\\*|\\s*\\*|(import)).*")) {
            return TYPES.COMMENT;
        }
        if (line.matches("for|while|do")) {
            return TYPES.LOOP;
        } else {
            return TYPES.STATEMENT;
        }
    }
}
