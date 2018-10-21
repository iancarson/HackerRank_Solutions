import java.io.*;
import java.util.*;
import java.lang.*;

public class Solution
{
  static int palindromeIndex(String s){
    	for(int i =0,j =s.length()-1; i<j; i++, j--)
    		if(s.charAt(i)!=s.charAt(j))
    			if(isPalindrome(s, i))
    				return i;
    			else if(isPalindrome(s, j))
    					return j;
    	return -1;
    }
    
    static boolean isPalindrome(String s, int index){
		for(int i =index+1,j =s.length()-1-index; i<j; i++, j--)
    		if(s.charAt(i)!=s.charAt(j))
    			return false;
		return true;
	}
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    int t=in.nextInt();
    String  s=null;
    for(int i=0;i<t;i++)
    {
      s=in.nextLine();
     int pos=palindromeIndex(s);
      System.out.println(pos);
    }
  }
}
                             