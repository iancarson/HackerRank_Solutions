import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;
import java.math.*;
public class utopianTree
{
  public static void main(String args[])
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    for(int i=0;i<n;i++)
    {
      int b=in.nextInt();
      if(b==0)
      {
        System.out.println(1);
      }
      else if(b==1)
      {
        System.out.println(2);
      }
      else if(b%2==0)
      {
        System.out.println((int) (Math.pow(2,b/2)*2)-1);
      }
        else
        {
            System.out.println((int) ((Math.pow(2,(b-1)/2)*2)-1)*2);
        }
    }
  }
}