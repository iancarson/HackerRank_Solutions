
import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.math.*;
public class Solution
{
  public static void main(String args[])
  {    
    Scanner input=new Scanner(System.in);
    int n=input.nextInt();
      int high=input.nextInt();
      int low=high;
      int max=0;
      int min=0;
      for(int i=1;i<n;i++)
      {    
      int s=input.nextInt();
       
        if(s>high)
        {
      high=s;
        max++;
      }
       if(s<low)
       {
           low=s;
         min++;
       }
      }
      
  
       System.out.println(max + " " + min);
  }
  }
  