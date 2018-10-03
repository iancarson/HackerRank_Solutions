import java.io.*;
import java.util.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int [] a=new int[n];
    int count=0;
    int sum=0;
    int sum2=0;
    for(int i=0;i<n;i++)
    {
      a[i]=in.nextInt();
    sum +=a[i];
    }
      if(sum %2 !=0)
      {
        System.out.println("NO");
      }
        else
        {
           for(int i=0;i<n;i++)
           {
             if(a[i]%2 !=0)
             {
               
             a[1]=a[i] + 1;
               a[i + 1]=a[i + 1] + 1;
               count +=2;
    }
  System.out.println(count);
}
  }
}