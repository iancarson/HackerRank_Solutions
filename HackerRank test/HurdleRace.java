import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.math.*;
public class Solution
{
  public static void main(String args[])
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int k=in.nextInt();
    int sub=0;
    for(int i=0;i<n;i++)
    {
      sub=Math.max(sub,in.nextInt());
    }
     
    if(sub>k)
    {
      int  sub1=Math.abs(sub-k);
      System.out.println(sub1);
    }
    else
    {
      System.out.println(0);
    
  }
  
}
}