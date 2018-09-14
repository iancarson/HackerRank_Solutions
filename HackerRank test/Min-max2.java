import java.util.*;
import java.io.*;
import java.math.*;
public class solution
{  
   public static void Solution(int[5] arr)
   {
     Scanner input=new Scanner(System.in);
     int max=0;
     int min=0;
     int n= arr.length;
     Arrays.sort(arr);
     for(int i=0;i<n;i++)
     {
       int value=input.nextInt();
       max =arr[i+1] + arr[i];
       min=arr[i] + arr[i-1];
     }
     System.out.printf("", +max +"" +min);
   }
  public static void main(String args[]){
    
    Solution(arr);
  }
}
      
       