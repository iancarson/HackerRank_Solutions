import java.io.*;
import java.util.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int k=in.nextInt();
    BitSet b=new BitSet(n);
    for(int i=0;i<k;i++)
      b.set(in.nextInt());
    in.close();
      int start=0;
      int finish=b.nextSetBit(0);
      int max=finish - start;
      do
      {
        max=Math.max(max,(finish - start)/2);
        start=finish;
        finish=b.nextSetBit(start + 1);
      }
      while(finish > -1);
      max=Math.max(max,n-1-start);
      System.out.println(max);
  }
}