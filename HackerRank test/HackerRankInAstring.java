import java.io.*;
import java.util.*;
import java.lang.*;
public class Solution
{
  public static String    HackerankString(String s)
  {
    String Hack="hackerrank";
    if(s.length()<Hack.length())
      return "NO";
    int j=0;
    for(int i=0;i<s.length();i++)
    {
      if(j<Hack.length() && s.charAt(i)==Hack.charAt(j))
        j++;
    }
  return (j==Hack.length() ? "YES" : "NO");
  }
      
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
      int t=in.nextInt();
      for(int i=0;i<t;i++)
      {
  String S=in.next();
  String s=HackerankString(S);
      System.out.println(s);
      }
}
}