import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;


public class Solution
{
	static InputStream is;
	static PrintWriter out;
	static String INPUT ="";

	static void solve()
	{
		for( int T = ni(); T >= 1; T-- )
		{
			int n = ni(), k = ni();
			int[] a = new int[n];
			for(int i = 0; i < n; i++)
			{
				a[i] = ni();
			}
			boolean[][] g = new boolean[n][n];
			for(int i = 0;i < n;i++)
			{
				for(int j = i +1; j < n; j++)
				{
					if(Math.abs(a[i]- a[j]) >= k)
					{
						g[i][j] = true;
					}
				}
			}
			out.println( n - doBipartiteMatching(g));
		}
	}
	public static int doBipartiteMatching(boolean[][] g)
	{
		int n = g.length;
		if(n == 0) return 0;
		int m = g[0].length;
		if(m == 0) return 0;
		int [] im =new int[m];
		Arrays.fill(im, -1);
		BitSet visited = new BitSet();
		int matched = 0;
		for(int i = 0; i < n;i++)
		{
			if(visit(g, i, visited, im))
			{
				visited.clear();
				matched ++;
			}
		}
		return matched;
	}
	public static boolean visit(boolean[][] g,int cur,BitSet visited, int[] im)
	{
		if(cur == -1) return true;
		for(int i = visited.nextClearBit(0); i != g[cur].length;i = visited.nextClearBit(i + 1))
		{
			if(g[cur][i])
			{
				visited.set(i);
				if(visit(g, im[i], visited, im))
				{
					im[i] = cur;
					return true;
				}
			}
		}
		return false;
	}
	 public static void main(String[] args) {
	 	long S = System.currentTimeMillis();
	 	is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
	 	out = new PrintWriter(System.out);
	 	solve();
	 	out.flush();
	 	long G = System.currentTimeMillis();
	 	tr(G -S + "ms");
		
	}
	static boolean eof()
	{
		try {
			is.mark(1000);
			int b;
			while((b = is.read()) != -1 && !(b >= 33 && b <= 126));
			is.reset();
			return b == -1;
		} catch (IOException e) {
			return true;
		}
	}
		
	static int ni()
	{
		try {
			int num = 0;
			boolean minus = false;
			while((num = is.read()) != -1 && !((num >= '0' && num <= '9') || num == '-'));
			if(num == '-'){
				num = 0;
				minus = true;
			}else{
				num -= '0';
			}
			
			while(true){
				int b = is.read();
				if(b >= '0' && b <= '9'){
					num = num * 10 + (b - '0');
				}else{
					return minus ? -num : num;
				}
			}
		} catch (IOException e) {
		}
		return -1;
	}
	
	static void tr(Object... o) { if(INPUT.length() != 0)System.out.println(Arrays.deepToString(o)); }
}
