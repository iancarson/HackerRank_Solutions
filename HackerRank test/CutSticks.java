import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int arr[] = new int[n];
        for(int i =0; i<n;i++){
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        int min = arr[0];
        for(int i =0; i<n;){
            System.out.println(arr.length-i);
            i++;
            while(arr[i]==min){
                i++;
            }
            min = arr[i];
        }
        
    }
}