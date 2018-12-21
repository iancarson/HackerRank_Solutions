import java.io.*;
import java.util.*;
public class Solution
{
    static String solve(int[] a){
        int len =a.length;
        int j=len-1;
        int i=0, sum1=0, sum2=0;
        while(i<len && j>=0){
            if(sum1==sum2 && ((i-j)==0)){
                return "YES";
            }else if(sum1<sum2){
                sum1+=a[i++];
            }else{
                sum2+=a[j--];
            }
        }
        return "NO";
    }
    public static void main(String[] args)
    {
    Scanner in=new Scanner(System.in);
    int t=in.nextInt();
    for(int i=0;i<t;i++)
    {
    int n=in.nextInt();
    int [] arr=new int[n];
    for(int j=0;j<n;j++)
    {
    arr[j]=in.nextInt();
    }
    String ans=solve(arr);
    System.out.println(ans);
    }
    }
    }
    
