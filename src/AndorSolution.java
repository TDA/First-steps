/**
 * Created by schandramouli on 9/22/15.
 */
import java.io.*;
import java.util.*;
import java.util.Stack;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class AndorSolution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = in.nextInt();
        }

        Stack<Integer> stack = new Stack<Integer>();
        int result = 0;
        for(int i = 0; i < n; i++){
            while(true){
                if(stack.isEmpty()){
                    stack.add(arr[i]);
                    break;
                } else if(stack.peek() < arr[i]){
                    result = Math.max(result, stack.peek() ^ arr[i]);
                    stack.add(arr[i]);
                    break;
                } else{
                    stack.pop();
                }
            }
        }
        stack = new Stack<Integer>();

        for(int i = n - 1; i >= 0; i--){
            while(true){
                if(stack.isEmpty()){
                    stack.add(arr[i]);
                    break;
                } else if(stack.peek() < arr[i]){
                    result = Math.max(result, stack.peek() ^ arr[i]);
                    stack.add(arr[i]);
                    break;
                } else{
                    stack.pop();
                }
            }
        }

        System.out.println(result);
    }
}
