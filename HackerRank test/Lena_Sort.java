import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Lena_Sort
{
	public static void main (String [] args)
	{
		long [] mins  = new long[100001];
		long [] maxes = new long[100001];
		mins[2] = 1;
		maxes[2] = 1;
		for(int i = 3; i <= 100000; i++)
		{
			mins[i] = i - 1+ mins[(i - 1)/2]+ mins[(i - 1) - (i - 1)/2];
			maxes[i] = maxes[i -1]+ i - 1;
		}
		Scanner in = new Scanner(System.in);
		int q = in.nextInt();
		for(int a0 = 0; a0 < q; a0++)
		{
			int len = in.nextInt();
			int c = in.nextInt();
			if(maxes[len] < c || mins[len] > c)
			{
				System.out.println(-1);
				continue;
			}
			System.out.println(portion(len,c,1, mins, maxes, new StringBuilder()));

		}
	}
	public static StringBuilder portion(int len, long c, int offset, long[] mins, long[] maxes, StringBuilder ans)
	{
		if(len == 0)
		{
			return ans;
		}
		if(len == 1)
		{
			ans.append(offset + " ");
			return ans;
		}
		int pivot = 0;
		c -= len - 1;
		while(mins[pivot]+mins[len-pivot-1] > c || maxes[pivot]+maxes[len-pivot-1] < c)
			pivot++;
		long newc = mins[pivot];
		while(mins[len-pivot-1] > c - newc || maxes[len-pivot-1] < c - newc)
			newc++;
		ans.append((pivot+offset) + " ");
		portion(pivot, newc, offset, mins, maxes, ans);
		portion(len-pivot-1, c - newc, offset+pivot+1,mins, maxes, ans);
		return ans;
	}
}
