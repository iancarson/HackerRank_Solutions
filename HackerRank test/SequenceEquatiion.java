import java.io.*;
import java.util.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int N=in.nextInt();
    int[] p=new int[N + 1];
    for(int i=1;i<=N;i++)
    {
      int pi=in.nextInt();
    p[pi]=i;
    }
in.close();
      for(int i=1;i<=N;i++)
      {
        int x=p[p[i]];
        System.out.println(x);
      }
  }
}
        
