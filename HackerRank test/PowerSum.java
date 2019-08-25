import java.util.Scanner;
import java.lang.Math;

public class Solution
{
	 static int totnum(int x, int N, int num)
	 {
	 	if(Math.pow(num,N) < x)
	 		return totnum(x, N, num + 1 ) + totnum(x - Math.pow(num, N),N,num + 1);
	 	else if(Math.pow(num, N) == x)
	 		return 1;
	 	else
	 		return 0;
	 }
	 public static void main(String[] args) {
	  	Scanner in = new Scanner(System.in);
	  	int x = in.nextInt();
	  	int N = in.nextInt();
	  	System.out.println(totnum(x, N, 1));
	  } 
}