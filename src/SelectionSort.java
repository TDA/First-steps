import java.util.Arrays;

public class SelectionSort {
 
    public static int[] doSelectionSort(int[] arr){
         
        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                count++;
                System.out.println(Arrays.toString(arr));
                if (arr[j] < arr[index])
                    index = j;
            }
            int smallerNumber = arr[index]; 
            arr[index] = arr[i];
            arr[i] = smallerNumber;

        }
        return arr;
    }

    static int count;
    public static void main(String a[]){
         
        int[] arr1 = {55, 10, 34, 26, 56, 7, 67, 88, 42};
        //int[] arr1 = {3, 4, 5, 6, 7, 8, 9, 2};
        int[] arr2 = doSelectionSort(arr1);
        for(int i:arr2){
            System.out.print(i);
            System.out.print(", ");
        }
        System.out.println();
        System.out.println("total iterations" + count);
    }
}