import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int[] ar=new int[n];
        for(int i=0;i<n;i++)
            ar[i]=in.nextInt();
        Vector<Integer> vec=new Vector<Integer>();
        for(int i=0;i<n-1;i++)
            if(ar[i]>ar[i+1])
            vec.add(i);
            int v_size=vec.size();
        if(v_size==0)
            System.out.println("yes");
        else if(v_size==1)
        {
            if(n==2)
                System.out.println("yes\nswap "+(vec.get(0)+1)+" "+(vec.get(0)+2));
            else if(ar[vec.get(0)]<ar[vec.get(0)+2])
                    System.out.println("yes\nswap "+(vec.get(0)+1)+" "+(vec.get(0)+2));
                else
                    System.out.println("no");            
        }
        else if(v_size==2)
            System.out.println("yes\nswap "+(vec.get(0)+1)+" "+(vec.get(1)+2));
        else if(vec.get(v_size-1)-vec.get(0)+1==v_size)
            System.out.println("yes\nreverse "+(vec.get(0)+1)+" "+(vec.get(v_size-1)+2));
            else
            System.out.println("no");
    }
}