import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine().trim();
        String [] arr= s.split("[ !,?._'@]+");
        if(s.length()==0)
        {
            System.out.println("0");
        }
        else if(s.length()>=400000)
        {
            System.out.println();
        }
        else{

System.out.println(arr.length);
        for(int i=0;i<arr.length;i++)
        {
            System.out.println(arr[i]);
        }
        }
        
        
        // Write your code here.

        scan.close();
    }
}

