/**
 * Created by schandramouli on 6/11/17.
 */

import java.util.HashSet;

public class PermutationStringDuplicates {
    static int fact(int num) {
        int fact0 = 1;
        for (int i = 1; i <= num; i++)
            fact0 = fact0 * i;
        return fact0;
    }

    public static void main(String[] args) {
        String input = "ABCA";
        char[][] perm;
        char[] word;
        word = input.toCharArray();
        perm = new char[fact(input.length())][input.length()];
        int k, j, i, index1 = 0, index2 = 0, check = 0;

        for (k = 0; k < input.length(); k++) {
            index1 = 0;
            for (i = 0; i < (fact(input.length()) / fact(input.length() - (k + 1))); i++) {
                for (j = 0; j < fact(input.length() - (k + 1)); j++) {
                    while (perm[index1][index2] != '\0') {
                        index2 = (index2 + 1) % input.length();
                        check++;
                        if (check == input.length()) break;
                    }
                    if (check != input.length())
                        perm[index1][index2] = word[k];

                    check = 0;
                    index1++;
                }
                index2 = (index2 + 1) % input.length();
            }
        }
        HashSet<String> permutations = new HashSet<>();

        for (int l = 0; l < perm.length; l++) {
            String s = getString(perm[l]);
            permutations.add(s);
        }

        System.out.println(permutations.size());
        System.out.println(permutations);
    }

    static String getString(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
}