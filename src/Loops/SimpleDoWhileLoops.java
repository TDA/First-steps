package Loops;

/**
 * Created by schandramouli on 4/26/17.
 */
public class SimpleDoWhileLoops {
    public static void main(String[] args) {
        int i = 0;
        // diff between while and do..while is that do..while ALWAYS executes at
        // least once, even if the condition is false
        do {
            System.out.println(i);
            i++;
        } while (i < 11);
    }
}
