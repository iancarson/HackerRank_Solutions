import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        String B=sc.next();
        /* Enter your code here. Print output to STDOUT. */
        System.out.println(A.length() + B.length());
        if(A.compareTo(B)>0)
        {
            System.out.println("Yes");
        }
        else
            System.out.println("No");
        //char [] Achar=A.toCharArray();
        //char [] Bchar=B.toCharArray();
        //char c=Achar[0];
        //char d=Bchar[0];
        String AA=Character.toUpperCase(A.charAt(0)) +A.substring(1,A.length());
        //String AB=A.substring(1,A.length()-1);
        String BB=Character.toUpperCase(B.charAt(0)) + B.substring(1,B.length());
        //String Bc=B.substring(1,B.length()-1);
        System.out.println(AA + " " + BB);  
    }
}



