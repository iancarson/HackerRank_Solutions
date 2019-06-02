import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution
{
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";
	public static int[][][] packWU(int n, int[] from, int[] to, int[] w)
	{
		int[][][] g = new int[n][][];
		int [] p = new int[n];
		for(int f : from) p[f]++;
			for(int t : to)p[t]++;
				for(int i = 0; i < n; i++)g[i] = new int[p[i]][2];
					for(int i = 0;i < from.length; i++)
					{
						--p[from[i]];
						g[from[i]][p[from[i]]][0] = to[i];
						g[from[i]][p[from[i]]][1] = w[i];
						--p[to[i]];
						g[to[i]][p[to[i]]][0] = from[i];
						g[to[i]][p[to[i]]][1] = w[i];
					}
					return g;
	}
	static void solve()
	{
		int n = ni(), k = ni();
		int[] from = new int[n -1];
		int[] to = new int[n- 1];
		int[] w = new int[ n - 1];
		long all = 0;
		for(int i = 0; i < n-1; i++)
		{
			from[i] = ni();
			to[i] = ni();
			w[i] = ni();
			all += w[i];
		}
		int [][][] g = packWU(n, from, to, w);
		boolean[] en = new boolean[n];
		for(int i = 0;i < k;i++){
			en[ni()] = true;
		}
		Queue<int[]> q = new PriorityQueue<int[]>(200000, new Comparator<int[]>(){
			public int compare(int[] a, int[] b){
				return -(a[2] - b[2]);
			}
		});
		DjSet ds = new DjSet(n);
		for(int i = 0; i < n;i++){
			if(en[i])
			{
				for(int [] e : g[i]){
					q.add(new int[]{i, en[0], e[1]});
				}
				all -= cur[2];
			}
		}
		out.println(all);
	}
	public static class DjSet{ public int[] upper;
		public DjSet(int n)
		{
			upper = new int[n];
			Arrays.fill(upper,-1);
		}
		public int root(int x)
		{
			return upper[x] < 0 ? x : (upper[x] = root(upper[x]));
		}
		public boolean equiv(int x, int y)
		{
			return root(x) == root(y);
		}
		public void union(int x, int y)
		{
			x = root(x);
			y = root(y);
			if(x != y)
			{
				if(upper[y] < upper[x])
				{
					int d = x;
					x = y;
					 y = d;
				}
				upper[x] += upper[y];
				upper[y] = x;
			}
		}
		public int count()
		{
			int ct = 0;
			for(int i = 0; i < upper.length; i++)
			{
				if(upper[i] < 0) ct++;
			}
			return ct;
		}
	}


}