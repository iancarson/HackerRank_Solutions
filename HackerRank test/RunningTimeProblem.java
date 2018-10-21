import java.io.*;
import java.util.*;
import java.lang.*;
public class Solution
{
  static void insertionTimer(int n,int[] a)
  {
    int count=0;
    n=a.length;
    for(int i=1;i<n;i++)
    {
      int value=a[i];
      int j= i;
      while(j>=1 && a[j-1]>value)
      {
        a[j]=a[j-1];
        j--;
        count++;
      }
      a[j]=value;
     
    }
    System.out.println(count);
  }
    
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int [] a=new int[n];
    for(int i=0;i<n;i++)
    {
      a[i]=in.nextInt();
    }
    insertionTimer(n,a);
    
  }
}
    