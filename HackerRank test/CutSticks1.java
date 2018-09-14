import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int[] a= new int[n];
    Arrays.sort(a);
    int counter=0;
    int c=0;
    for(int i=0;i<n;i++)
    {
      a[i]=in.nextInt();
      
      }
    c=a[0];
    for(int i=0;i<n;)
    {
      System.out.println(a.length -i);
      i++;
      while (a[i]==c)
      {i++;
    }
    c=a[i];
  }
}
}