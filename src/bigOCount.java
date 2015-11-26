import java.io.*;
import java.util.Scanner;

/**
 * Created by schandramouli on 11/26/15.
 */
enum TYPES {
    STATEMENT,
    LOOP,
    COMMENT,
    EMPTY,
    DECLARATION,
    ASSIGNMENT,
    UNMATCHED
}
public class bigOCount {
    // for each program fed in as input, calculate the big o running complexity
    // this is a simple one that doesnt think much about loops
    // only takes basic statements into account, like assignment, if, switch etc
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
        }

        if (line.matches(".*(public)|(class)|(interface)|(enum)|(union)|(struct)")) {
            return TYPES.DECLARATION;
        }
        if (line.matches(".*(\\+|\\-|/|\\*|(if)|(switch)|=).*")) {
            return TYPES.STATEMENT;
        }
        return TYPES.UNMATCHED;
    }
}
