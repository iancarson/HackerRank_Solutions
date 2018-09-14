import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class designerPdfViewer
{
  public static void main(String args[])
  {
    Scanner in=new Scanner(System.in);
    int[] h= new int[26];
    int max=0;
    for(int i=0;i<26;i++)
    {
     h[i]=in.nextInt();
    }
    String word=in.next();
    
      for(int i=0;i<word.length();i++)
      {
        if(max<h[word.charAt(i)-97])
        {
          max=h[word.charAt(i)-97];
        }
      }
    System.out.println(max * word.length());
  }
}