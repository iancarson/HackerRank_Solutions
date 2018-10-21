import java.io.*;
import java.util.regex.*;
import java.util.*;
public class Solution
{
  public static int countchanges(String S)
  {
    String Signal="SOS";
    int count=0;
    int n=S.length();
    for(int i=0;i<n;i++)
    {
      if(S.charAt(i )!=Signal.charAt(i % 3))
         count ++;
    }
    return count;
  }
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    String s=in.next();
   int count =countchanges(s);
    System.out.println(count);
  }
}