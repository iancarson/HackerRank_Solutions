import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.*;
public class Solution
{
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    String s=in.next();
    String t=in.next();
    int k=in.nextInt();
    int Add=0;
    int S=0;
    int T=0;
    for(int i=0;i<t.length();i++)
    {
      for(int j=0;j<s.length();j++)
      {
        if( t.charAt(i)==s.charAt(j))
          Add++;
      }
    }
   S=s.length() -Add;
    T=t.length()-Add;
       if((s.length()+t.length()-2*Add)>k){
            System.out.println("No");
        }
      else if(S>T)
      {
          System.out.println("Yes");
      }
//CASE B
        else if((s.length()+t.length()-2*Add)%2==k%2){
            System.out.println("Yes");
        }
//CASE C
        else if((s.length()+t.length()-k)<0){
            System.out.println("Yes");
        }
      
//CASE D
        else{
            System.out.println("No");
        }
  }
}