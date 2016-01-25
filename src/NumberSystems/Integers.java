package NumberSystems;

/**
 * Created by schandramouli on 12/8/15.
 */
public class Integers {
    public static Integer getLCM(Integer first, Integer second) {
        Integer cloneI = first;
        Integer cloneSecond = second;
        while (cloneI.compareTo(cloneSecond) != 0){
            // second is greater
            if (cloneI.compareTo(cloneSecond) == -1) {
                cloneI += first;
            } else {
                // i is greater
                cloneSecond += second;
            }
        }
        return cloneI;
    }

    public static Double getLCM(Double first, Double second) {
        Double cloneI = first;
        Double cloneSecond = second;
        while (cloneI.compareTo(cloneSecond) != 0){
            // second is greater
            if (cloneI.compareTo(cloneSecond) == -1) {
                cloneI += first;
            } else {
                // i is greater
                cloneSecond += second;
            }
        }
        return cloneI;
    }

    public static int atoi(String s) {
        try {
            int val = Integer.parseInt(s);
            return val;
        } catch (NumberFormatException e) {
            // we know that the string is not in the proper format
            StringBuilder stringBuilder = new StringBuilder();
            // lets iterate through the string removing invalid characters
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c > 30 && c < 40) {

                }

            }
            return 0;
        }

    }

    public static void main(String[] args) {
        //System.out.println(Integers.getLCM(21.0, 9.0));
        //System.out.println(atoi(""));
        System.out.println(atoi("        123        "));
        System.out.println(atoi("%$%^#$@123"));
        System.out.println(atoi("123"));
        //System.out.println(atoi("12312353464575"));
    }
}
