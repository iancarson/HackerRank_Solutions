import java.util.*;
import java.io.*;
import java.lang.*;
public class Solution
{
public static void main(String[] args)
{
Scanner in=new Scanner(System.in);
int t=in.nextInt();
for(int i=0; i<t; i++){
            int p = s.nextInt();
            int m = s.nextInt();
            int id = s.nextInt();
            if((m+id-1)%p==0)
            System.out.println(p);
            else 
            System.out.println((m+id-1)%p);
        }
}
}