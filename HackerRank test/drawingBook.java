import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.regex.*;
public class pageCount
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int[] page;
    page=new int[n];
    int FromFront=0;
    int FromBack=0;
    for(int i=1;i<page.length;i++)
    {
      for(int j=n;j>page.length-1;j--)
      {
        int p=in.nextInt();
        if(page[i]==p)
        {
          FromFront++;
        }
        else if
        (page[j]==p)
        {
          FromBack++;
        }
      }
      if(FromBack<FromFront)
      {
        System.out.println(FromBack);
      }
      else
      {System.out.println(FromFront);
      }
    }
  }
}