import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.concurrent.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();  
    for(int i=0;i<n;i++)
    {
     int  x=in.nextInt();
      int y=in.nextInt();
      int z=in.nextInt();
    int  sum=Math.abs(x-z);
     int d=Math.abs(y-z);
      if(sum<d)
        System.out.println("Cat A");
      else if(d<sum)
        System.out.println("Cat B");
      else
      
        System.out.println("Mouse C");
      
    }
  }
}