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
        	int birthYear = s.nextInt();
        	int deathYear = s.nextInt();
            if (map.containsKey(birthYear)) {
                int x = map.get(birthYear);
                map.put(birthYear, ++x);
            } else {
                map.put(birthYear, 1);
            }
        }
        System.out.println(peopleBirths);
    }
}
