import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Solution {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";
	
	static long[] F = new long[10001];
	
	static void solve()
	{
		F[0] = 1;
		for(int i = 1;i <= 10000;i++){
			F[i] = F[i-1] * i % mod;
		}
		
		for(int T = ni();T >= 1;T--){
			int n = ni();
			int[] f = new int[n-1];
			int[] t = new int[n-1];
			for(int i = 0;i < n-1;i++){
				f[i] = ni();
				t[i] = ni();
			}
			if(n == 2){
				out.println(0);
				continue;
			}
			if(n == 1){
				out.println(1);
				continue;
			}
			
			int[][] g = packU(n, f, t);
			int[][] pars = parents(g, 0);
			int[] par = pars[0];
			int[] ord = pars[1];
			int[] des = new int[n];
			for(int i = n-1;i >= 0;i--){
				int cur = ord[i];
				int max = 0;
				for(int e : g[cur]){
					if(par[cur] != e){
						max = Math.max(max, des[e]+1);
					}
				}
				des[cur] = max;
			}
			
			int root = 0;
			if(g[0].length == 1 && g[g[0][0]].length >= 2){
				root = g[0][0];
			}
			
			int over = 0;
			for(int e : g[root]){
				if(par[root] != e && des[e] > 0)over++;
			}
			long r;
			if(over >= 3){
				r = 0;
			}else if(over == 0){
				r = F[g[root].length];
			}else{
				r = 2*F[g[root].length-over];
				for(int e : g[root]){
					if(par[root] != e){
						r = r * rec(e, g, par, des) % mod;
					}
				}
			}
			out.println(r);
		}
	}
	static long mod = 1000000007;
	
	static long rec(int cur, int[][] g, int[] par, int[] des)
	{
		int oo = -1;
		int one = 0;
		for(int e : g[cur]){
			if(par[cur] != e){
				if(des[e] > 0){
					if(oo != -1)return 0;
					oo = e;
				}else{
					one++;
				}
			}
		}
		if(oo != -1){
			return rec(oo, g, par, des) * F[one] % mod;
		}else{
			return F[one];
		}
	}
	
	public static int[][] parents(int[][] g, int root)
	{
		int n = g.length;
		int[] par = new int[n];
		Arrays.fill(par, -1);
		
		int[] q = new int[n];
		q[0] = root;
		for(int p = 0, r = 1;p < r;p++) {
			int cur = q[p];
			for(int nex : g[cur]){
				if(par[cur] != nex){
					q[r++] = nex;
					par[nex] = cur;
				}
			}
		}
		return new int[][] {par, q};
	}
	
	public static int[][] packU(int n, int[] from, int[] to)
	{
		int[][] g = new int[n][];
		int[] p = new int[n];
		for(int f : from)p[f]++;
		for(int t : to)p[t]++;
		for(int i = 0;i < n;i++)g[i] = new int[p[i]];
		for(int i = 0;i < from.length;i++){
			g[from[i]][--p[from[i]]] = to[i];
			g[to[i]][--p[to[i]]] = from[i];
		}
		return g;
	}
	
	public static void main(String[] args) throws Exception
	{
		long S = System.currentTimeMillis();
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
		
		solve();
		out.flush();
		long G = System.currentTimeMillis();
		tr(G-S+"ms");
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