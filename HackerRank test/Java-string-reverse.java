import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        boolean pal=false;
        int forward=0;
        int backward=A.length()-1;
       while(backward>forward)
       {
           char forwardchar=A.charAt(forward++);
           char backwardchar=A.charAt(backward--);
           if(forwardchar==backwardchar)
           pal=true;
       }
       if(pal==true)
       System.out.println("Yes");
       else
       System.out.println("No");
        /* Enter your code here. Print output to STDOUT. */
        
    }
}



