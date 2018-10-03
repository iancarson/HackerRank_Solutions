import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int k=in.nextInt();
    int [] a=new int[k];
    for(int i=0;i<k;i++)
    {
      a[i]=in.nextInt();
      if(a[i]==n)
        System.out.println(a[i]);
    }
  }
}