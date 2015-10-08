import java.util.Scanner;

/**
 * Created by schandramouli on 10/7/15.
 */
public class BubbleSort {
    public static void main(String[] args) {
        // 985 0 682 163 959 714 809 45 131 729 343 717 94 686 811 60 83 813 945 290 196 673 413 610 753
        Scanner s = new Scanner(System.in);

        int i = 0;
        String str = s.nextLine();
        String[] strArray = str.split(" ");
        int[] array = new int[strArray.length];
        for (String s1 : strArray) {
            array[i] = Integer.parseInt(s1);
            i++;
        }
        int count = 0;
        for (i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if(array[i] > array[j]) {
                    // swap them
                    int t = array[i];
                    array[i] = array[j];
                    array[j] = t;
                    count++;
                }
            }
        }
        System.out.println(count);
        for (int k = 0; k < array.length - 1; k++) {
            System.out.print(array[k]);
            System.out.print(" ");
        }
        System.out.print(array[array.length - 1]);
        System.out.println();
    }
}
