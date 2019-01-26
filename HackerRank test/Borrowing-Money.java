import java.io.*;
import java.util.*;

public class Solution
{
	private static InputReader in;
	private static PrintWriter out;
	static final int BUBEN =23;
	public static long[][] cache;
	public static long[] adj;
	public static long[] val;
	private static long[] max(long left)
	{
		if(left==0)
			return new long[]{0,1};
		if(left < (1L << BUBEN))
		{
			long [] cached= cache[((int)left)];
			if(cached !=null)
				return cached;
		}
		int w=Long.numberOfTrailingZeros(Long.highestOneBit(left));
		long o1= left ^(1L<<w);
		long o2=(left ^(1L<<w)) & adj[w];
		long [] res=new long[2];
		if(o1==o2)
		{
			long [] x=max(o2);
			res=new long[] {x[0] +val[w],x[1] + (val[w]==0 ? x[1]:0)};

		}
		else
		{
			long [] a1=max(o1);
			long [] a2=max(o2);
			if(a1[0] ==a2[0] + val[w])
			{
				res=new long[]{a1[0],a1[1]+a2[1]};
			}else if(a1[0] >a2[0] + val[w])
			{
				res=a1;
			}
			else
			{
				res=new long[] {a2[0]+val[w],a2[1]};
			}
		}
		if(left<(1L << BUBEN))
		{
			cache[((int)left)]=res;
		}
		return res;
	}
}