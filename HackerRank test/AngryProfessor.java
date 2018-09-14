import java.util.*;
import java.io.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    for(int i=0;i<n;i++)
    {
      int a=in.nextInt();
      int b=in.nextInt();
      int c=0;
      for(int j=0;j<a;j++)
      {
        int arrTime=in.nextInt();
        if(arrTime<=0)
        {
          c++;
        }
      }
      if(c>=b)
      {
        System.out.println("NO");
      }
      else
      {
        System.out.println("Yes");
      }
    }
  }
}