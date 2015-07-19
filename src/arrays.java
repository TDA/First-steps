import java.util.Arrays;

/**
 * Created by schandramouli on 7/19/15.
 */
public class arrays {
    public static void main(String[] args){
        int[] arr = {13,2134,546,743,76,34,23,12,1};
        System.out.println("Selection Sort");
        System.out.println(prettyPrint(SelectionSort(arr.clone()),","));
        System.out.println();

        System.out.println("What Sort");
        System.out.println(prettyPrint(WhatSort(arr.clone()),","));
        System.out.println();

        System.out.println("Bubble Sort");
        System.out.println(prettyPrint(BubbleSort(arr.clone()),","));
        System.out.println();

        System.out.println("Insertion Sort");
        System.out.println(prettyPrint(InsertionSort(arr.clone()),","));
        System.out.println();

        System.out.println("Merge Sort");
        System.out.println(prettyPrint(MergeSort(arr.clone()),","));
        System.out.println();

    }
    public static String prettyPrint(String s, String delimiter){
        s = s.replace("[","");
        s = s.replace("]","");
        s = s.replace(",", delimiter);
        return s;
    }

    public static String SelectionSort(int [] a){
        int i,
            j,
            index,
            len = a.length,
            loopCounter = 0;

        for(i=0;i<len;i++){
            index = i;
            for(j=i+1;j<len;j++){
                if(a[j]<a[index]){
                    index = j;
                    // the new smaller element
                }
                loopCounter++;
            }
            //swap them up
            swap(i, index, a);
        }
        System.out.println("    Loops run " + loopCounter);
        return Arrays.toString(a);
    }

    public static String BubbleSort(int [] a){
        int i,
            j,
            len = a.length,
            loopCounter = 0;
        for(i=0;i<len;i++){
            for(j=0;j<len-1;j++){
                if(a[j]>a[j+1]){
                    // swap them
                    swap(j+1, j, a);
                }
                loopCounter++;
            }
        }
        System.out.println("    Loops run " + loopCounter);
        return Arrays.toString(a);
    }

    public static String WhatSort(int [] a){
        int i,
                j,
                len = a.length,
                loopCounter = 0;
        for(i=0;i<len;i++){
            for(j=i+1;j<len;j++){
                if(a[j]<a[i]){
                    // swap them
                    swap(i, j, a);
                }
                loopCounter++;
            }
        }
        System.out.println("    Loops run " + loopCounter);
        return Arrays.toString(a);
    }

    public static String InsertionSort(int [] a){
        int i,
            j,
            index,
            len = a.length,
            loopCounter = 0;

        for(i=0;i<len-1;i++){
            index = i;
            for(j=i+1;j>=0;j--){
                if(a[j]<a[index]){
                    index = j;
                    // the new smaller element
                }
                loopCounter++;
            }
            //swap them up
            swap(i, index, a);
        }
        System.out.println("    Loops run " + loopCounter);
        return Arrays.toString(a);
    }

    public static String MergeSort(int [] a){
        int start = 0,
            end = a.length - 1;
        doMergeSort(a, start, end);
        return Arrays.toString(a);
    }

    public static void doMergeSort(int [] a, int start, int end) {
        if (start < end){
            int mid = (start + end) /2;
            doMergeSort(a, start, mid);
            doMergeSort(a, mid + 1, end);
            merge(a, start, mid, end);
        }
    }

    public static void merge(int[] a,int start, int mid, int end){
        int i = start,
            j = mid + 1,
            k = start;

        int [] temp = a.clone();

        while(i <= mid && j <= end){
            if(temp[i] > temp[j]){
                a[k] = temp[j];
                j++;
            }
            else {
                a[k] = temp[i];
                i++;
            }
            k++;
        }
        while (i <= mid) {
            a[k] = temp[i];
            k++;
            i++;
        }
        //System.out.println(Arrays.toString(a));
    }
    public static void swap(int i, int index, int a[]){
        int temp = a[i];
        a[i] = a[index];
        a[index] = temp;
    }
}
