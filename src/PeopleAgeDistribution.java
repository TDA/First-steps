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
        // change this to find the most frequent AGE instead of birth year,
        // so start - end :)
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
            int age = deathYear - birthYear;
            // just for the record, this would be literally 2 lines with ruby/python :D :|
            if (peopleBirths.containsKey(age)) {
                int x = peopleBirths.get(age);
                peopleBirths.put(age, ++x);
            } else {
                peopleBirths.put(age, 1);
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
        System.out.println(maxYear + " is the age with largest number of people");
    }
}
