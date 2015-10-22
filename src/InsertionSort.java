import java.util.Scanner;

public class InsertionSort {
 
    public static void main(String a[]){
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        int[] arr1 = new int[t];
        for (int i = 0; i < t; i++) {
            arr1[i] = scanner.nextInt();
        }
        int[] arr2 = doInsertionSort(arr1);
    }
     
    public static int[] doInsertionSort(int[] input){
         
        int temp;
        for (int i = 1; i < input.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(input[j] < input[j-1]){
                    temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
        }
        return input;
    }

    private static void printArray(int[] ar) {
        for(int n: ar){
            System.out.print(n+" ");
        }
        System.out.println("");
    }
}