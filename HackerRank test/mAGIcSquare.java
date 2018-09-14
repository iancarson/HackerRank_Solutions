import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    for(int i=0;i<9;i++)
    {int a=in.nextInt();
     int b=in.nextInt();
     int c=in.nextInt();
     int d=in.nextInt();
     int e=in.nextInt();
     int f=in.nextInt();
     int g=in.nextInt();
     int h=in.nextInt();
     int j=in.nextInt();
      int sub1=15-(a + d + g);
     int sub2=15-(b + e + h);
     int sub3=15-(c + f+ j);
      int sub=sub1 + sub2 +sub3;
      System.out.println(sub);
    }
  }
}