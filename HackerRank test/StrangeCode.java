import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.math.*;
public class Solution
{
  static long strangeCounter(long t)
  { long p1 = 0; 
   long p2 = 3; 
   long p3 = 3; 
   while(p1<=t)
   { if(t<=p2 && t>p1)
   {
     break; 
   }
    else
   {
     p1 =p2; 
     p3 = p3*2; 
     p2 = p2+p3;
   }
   } 
   long tmp = p2-p1; 
   long ans = 0; 
   if(p1+1==t)
   { ans = tmp;
   } else if(p2==t)
   { ans = 1;
   } else
   { ans = p2-t+1;
   }
System.out.println(ans);
    return(ans);
}

  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    long t=in.nextLong();
    strangeCounter(t);
  }
}