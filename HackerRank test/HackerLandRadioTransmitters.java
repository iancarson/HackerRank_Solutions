import java.io.*;
import java.util.*;
public class Solution
{
	public static void main(String[] args)
	{
	Scanner in=new Scanner(System.in);
	int n=in.nextInt();
	int k=in.nextInt();
	int [] x=new int[n];
	for(int i=0;i<n;i++)
	{
	x[i]=in.nextInt();
	}
	Arrays.sort(x);
	int number=0;
	int i=0;
	while(i<n)
	{
	number ++;
	int loc=x[i] +k;
	while(i<n && x[i]<=loc)i++;
	loc=x[--i] + k;
	while(i < n && x[i]<=loc)i++;
	}
	System.out.println(number);
	}
}