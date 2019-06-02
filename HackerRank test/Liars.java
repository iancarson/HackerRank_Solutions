
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Solution {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";
	
	static void solve()
	{
		int n = ni(), m = ni();
		int[][] r = new int[m][];
		for(int i = 0;i < m;i++){
			r[i] = new int[]{ni(), ni(), ni()};
		}
		
		int min = 0, max = 0;
		{
			int[] from = new int[m*2+n*2];
			int[] to = new int[m*2+n*2];
			int[] w = new int[m*2+n*2];
			int p = 0;
			for(int i = 0;i < n;i++){
				from[p] = i; to[p] = i+1; w[p] = 1; p++;
				from[p] = i+1; to[p] = i; w[p] = 0; p++;
			}
			for(int i = 0;i < m;i++){
				from[p] = r[i][0]-1; to[p] = r[i][1]; w[p] = r[i][2]; p++;
				from[p] = r[i][1]; to[p] = r[i][0]-1; w[p] = -r[i][2]; p++;
			}
			min = -bf(from, to, w, n+1, n)[0];
		}
		{
			int[] from = new int[m*2+n*2];
			int[] to = new int[m*2+n*2];
			int[] w = new int[m*2+n*2];
			int p = 0;
			for(int i = 0;i < n;i++){
				from[p] = i; to[p] = i+1; w[p] = 0; p++;
				from[p] = i+1; to[p] = i; w[p] = 1; p++;
			}
			for(int i = 0;i < m;i++){
				from[p] = r[i][1]; to[p] = r[i][0]-1; w[p] = r[i][2]; p++;
				from[p] = r[i][0]-1; to[p] = r[i][1]; w[p] = -r[i][2]; p++;
			}
			max = bf(from, to, w, n+1, n)[0];
		}
		out.println(min + " " + max);
	}
	
	public static int[] bf(int[] from, int[] to, int[] w, int n, int start)
	{
		int[] d = new int[n];
		Arrays.fill(d, Integer.MAX_VALUE / 3);
		d[start] = 0;
		int m = from.length;
		
		while(true){ // <=n-1
			boolean updated = false;
			for(int j = 0;j < m;j++){
				if(d[from[j]] + w[j] < d[to[j]]){
					d[to[j]] = d[from[j]] + w[j];
					updated = true;
				}
			}
			if(!updated)break;
		}
		for(int i = 0;i < m;i++){
			if(d[from[i]] + w[i] < d[to[i]]){
				return null;
			}
		}
		
		return d;
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
