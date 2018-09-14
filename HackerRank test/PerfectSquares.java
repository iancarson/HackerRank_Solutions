import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.math.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int t=in.nextInt();
    int n=in.nextInt();
    int k=in.nextInt();
    int count=0;
    for(int m=0;m<t;m++)
    {
      for(int i=n;i<=k;i++)
      { for(int j=1;j * j<=i;j++)
        if( j * j ==i && )
          count ++;
      }
      System.out.println(count);
    }
  }
}