import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by schandramouli on 12/11/15.
 */
public class PeopleAgeDistribution {
    public static void main(String[] args) {
        // given a list of start and end (birth and death dates) of people
        // find which year has the most number of people born in it :D
        // hashmap i think?
        HashMap<Integer, Integer> peopleBirths = new HashMap<>();
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of people");
        int n = Integer.parseInt(s.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("Birth Year:");
        	int birthYear = s.nextInt();
            System.out.println();
            System.out.print("Death Year:");
        	int deathYear = s.nextInt();
            System.out.println();
            if (peopleBirths.containsKey(birthYear)) {
                int x = peopleBirths.get(birthYear);
                peopleBirths.put(birthYear, ++x);
            } else {
                peopleBirths.put(birthYear, 1);
            }
        }
        System.out.println(peopleBirths);
        // After this straightforward, cycle through the map
        // find largest value, and corresponding key
        int max = 0;
        int maxYear = 0;
        for (Map.Entry<Integer, Integer> entry: peopleBirths.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxYear = entry.getKey();
            }
        }
        System.out.println(maxYear + " is the year with largest number of births");
    }
}
