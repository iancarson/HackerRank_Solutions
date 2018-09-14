import java.io.*;
import java.util.*;
import java.util.regex.*;
public class Solution
{
public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    for(int a0 = 0; a0 < t; a0++){
        int count = 0;
        int n = in.nextInt();
        String n1 = Integer.toString(n);
        int l = n1.length();
        int a[] = new int[l];
        for(int i = 0 ; i<l ; i++){
            a[i] = Integer.parseInt(n1.substring(i,i+1));                
            if((a[i] != 0) && (n%a[i]==0) ){
                count++;
            }

        }
         System.out.println(count); 
    }
}

}