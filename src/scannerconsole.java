/**
 * Created by schandramouli on 7/11/15.
 */
import java.io.Console;
import java.util.Scanner;

public class scannerconsole {
    public static void main(String[] args){
        if(System.console() != null) {
            Console terminal = System.console();
            String username = terminal.readLine("User name: ");
            char[] passwd = terminal.readPassword("Password: ");
        }
        else{
            Scanner s = new Scanner(System.in);
            String username = s.nextLine();
            String passwd = s.nextLine();
        }
    }
    static {
        System.out.print("heya");
    }
}
