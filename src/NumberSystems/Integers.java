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

    public static void main(String[] args) {
        System.out.println(Integers.getLCM(21.0, 9.0));
    }
}
