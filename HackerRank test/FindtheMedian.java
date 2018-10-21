import java.io.*;
import java.util.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int[] a=new int[n];
    for(int i=0;i<n;i++)
    a[i]=in.nextInt();
      Arrays.sort(a);
    System.out.println(a[(n/2)]);
  
}
}
