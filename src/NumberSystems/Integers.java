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
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // we know that the string is not in the proper format
            StringBuilder stringBuilder = new StringBuilder();
            // lets iterate through the string removing invalid characters
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= 48 && c <= 57) {
                    stringBuilder.append(c);
                }
            }
            if ("".equals(stringBuilder.toString())) {
                stringBuilder.append(0);
            }
            return Integer.parseInt(stringBuilder.toString());
        }

    }

    public static void main(String[] args) {
        //System.out.println(Integers.getLCM(21.0, 9.0));
        //System.out.println(atoi(""));
        System.out.println(atoi("        123        "));
        System.out.println(atoi("%$%^#$@123"));
        System.out.println(atoi("123"));
        System.out.println(atoi("-123"));
        //System.out.println(atoi("12312353464575"));
    }
}
