import java.util.Arrays;
import java.util.Scanner;

public class Solution
{
	public static void main(String[] args)
	{
		int a[][] = new int[6][6];
		int maxSum =	Integer.MIN_VALUE;
		try(Scanner in =  new Scanner(System.in))
		{
			for( int i = 0; i < 6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					a[i][j] = in.nextInt();
					if( i > 1 && j > 1)
					{
						int sum =
						a[i][j]
						+ a[i][j-1]
						+ a[i][j-2]
						+ a[i-1][j-1]
						+ a[i-2][j]
						+ a[i-2][j-1]
						+ a[i-2][j-2];
						if( sum > maxSum)
						{
							maxSum = sum;
						}
					}
				}
			}
		}
		System.out.println(maxSum);
	}
}