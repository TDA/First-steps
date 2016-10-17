package ZenefitsInterviews;

/**
 * Created by schandramouli on 10/16/16.
 */
public class SortedPermutedRank {
    public static void main(String[] args) {
        String s = "ZCSFLVHXRYJ134#@WABGT";
//        String s = "STRING";
        System.out.println(findRank(s));
    }

    public static long findRank(String a) {
        // find all chars smaller than this to the right
        // multiply that by the combinations of the remaining stuff
        long rank = 1;
        for (int i = 0; i < a.length(); i++) {
            rank += findSmallerToRight(a.charAt(i), a.substring(i)) * findFact(a.length() - i - 1);
            rank = rank % 1000003;
            //System.out.println("Finding " + findSmallerToRight(a.charAt(i), a.substring(i)) + " times " + findFact(a.length() - i - 1));
        }
        return rank;
    }

    public static long findSmallerToRight(char x, String a) {
        long lesser = 0;
        for (int i = 0; i < a.length(); i++) {
            if (x > a.charAt(i)) {
                lesser++;
            }
        }
        return lesser;
    }

    public static long findFact(int x) {
        return x <= 1 ? 1 : x * findFact(x - 1);
    }

}
