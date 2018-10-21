import java.util.*;
import java.io.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
  int size=Integer.parseInt(in.nextLine());
    StringBuffer[] a=new StringBuffer[100];
    for(int i=0;i<100;i++)
    {
      a[i]=new     StringBuffer();
      
    }
    for(int i=0;i<size;i++)
    {
      String b=in.nextLine();
      String []c=b.split("[\\s]+");
      int k=Integer.parseInt(c[0]);
      String s;
      if(i<size/2)
        s="-" + " ";
      else
        s=c[1] + " ";
      a[k]=a[k].append(s);
    }
    for(int i=0;i<100;i++)
    {
      System.out.print(a[i]);
    }
  }
}