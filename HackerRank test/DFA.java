//dfa.java
import java.io.*; 
class dfa 
{ 
public static void main(String arg[])throws IOException
{ 
  BufferedReader br=new BufferedReader(new InputStreamReader (System.in)); 
  int i,ip,ns,l; 
  System.out.println("Enter the length of string :"); 
  l=Integer.parseInt(br.readLine()); 
  System.out.print("Enter the String 0/1 :"); 
  int a[]=new int[l]; 
  for(i=0;i<l;i++)
  { 
   a[i]=Integer.parseInt(br.readLine()); 
  } 
  int q[][]={{0,1},{0,2},{0,3},{4,3},{4,4}}; 
  int in=0,fs=4,cs=in; 
  for(i=0;i<l;i++) 
  { 
   System.out.print("-->q"+in);
   ip=a[i]; 
   ns=q[cs][ip]; 
   cs=ns; 
  } 
  System.out.println("Currant State Is="+cs); 
  System.out.println("final  State Is="+fs); 
  if(cs==fs) 
    System.out.println("String is Accepted"); 
  else 
   System.out.println("string is rejected"); 
 } 
}