import java.io.*;
import java.util.*;
import java.math.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner sc= new Scanner(System.in);
int t=sc.nextInt();

for(int i=0;i<t;i++)
    {
    int flag=0;
    String s=sc.next();
    int start=0,end=s.length()-1;

    while(start<(s.length()-2))
        {

        int first=(int)s.charAt(start);
        int last=(int)s.charAt(end);
        int sec=(int)s.charAt(start+1);
        int sec_last=(int)s.charAt(end-1);

        if(Math.abs(first-sec)!=Math.abs(last-sec_last))
        flag=1;

        start++;
        end--;
    }

    if(flag==0)
        System.out.println("Funny");
    else
        System.out.println("Not Funny");
}

}
}