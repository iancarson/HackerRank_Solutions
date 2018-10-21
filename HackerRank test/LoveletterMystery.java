import java.io.*;
import java.util.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int t=in.nextInt();
    int count=0;
    for(int q=0;q<t;q++)
    { 
      String s=in.next();
      int i=0, len=s.length()-1;
      
     while(i<len)
     {
       count +=Math.abs(s.charAt(i)-s.charAt(len));
       i++;
       len--;
     }
      System.out.println(count);
  }
}
}
  