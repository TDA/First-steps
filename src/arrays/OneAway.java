package arrays;

public class OneAway {
    boolean isOneAway(String a, String b) {
        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();
        int i = 0, j = 0;
        int countMismatch = 0;
        int lengthDiff = Math.abs(charA.length - charB.length);
        if (lengthDiff > 1) {
            return false;
        }

        while (i < charA.length && j < charB.length) {
            if (charA[i] == charB[j]) {
                // matched, so move on
                i++;
                j++;
            } else if (countMismatch <= 1) {
                countMismatch++;
                // insert or removal ops
                if (charA.length < charB.length) {
                    j++;
                    continue;
                }
                if (charA.length > charB.length) {
                    i++;
                    continue;
                }

                // replace op
                i++;
                j++;
            } else {
                break;
            }
        }
        return countMismatch <= 1;
    }

    public static void main(String[] args){
        OneAway oneAway = new OneAway();
        System.out.println(oneAway.isOneAway("pale", "ple"));
        System.out.println(oneAway.isOneAway("pales", "pale"));
        System.out.println(oneAway.isOneAway("paile", "pale"));
        System.out.println(oneAway.isOneAway("pale", "bale"));
        System.out.println(oneAway.isOneAway("pale", "bake"));
        System.out.println(oneAway.isOneAway("sai", "baii"));
    }
}
