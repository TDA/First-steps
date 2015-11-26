import java.util.Scanner;

/**
 * Created by schandramouli on 11/26/15.
 */
enum TYPES {
    STATEMENT,
    LOOP
}
public class bigOCount {
    // for each program fed in as input, calculate the big o running complexity
    // this is a simple one that doesnt think much about loops
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while (scanner.hasNext()) {
            // read in line by line
            String line = scanner.nextLine();
            switch (findType(line)) {
                case STATEMENT: count++;
                    break;
                case LOOP:
                    // add logic here to find the amount of looping required, nested loops etc
                    break;
            }



        }
    }

    public static TYPES findType(String line) {
        if (line.matches("for|while|do")) {
            return TYPES.LOOP;
        } else {
            return TYPES.STATEMENT;
        }
    }
}
