import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Queue;

public class E {
	InputStream is;
	PrintWriter out;
	String INPUT = "";
	
	void solve()
	{
		int n = ni(), m = ni();
		int[] from = new int[m];
		int[] to = new int[m];
		int[] w = new int[m];
		for(int i = 0;i < m;i++){
			from[i] = ni()-1;
			to[i] = ni()-1;
			w[i] = ni()%10;
		}
		int[][][] g = packWU(n, from, to, w);
		int[] d = new int[n];
		Arrays.fill(d, -1);
		DJSet ds = new DJSet(n);
		
		long[] ret = new long[10];
		for(int i = 0;i < n;i++){
			if(d[i] == -1){
				Queue<Integer> q = new ArrayDeque<>();
				q.add(i);
				d[i] = 0;
				boolean c1 = false, c2 = false, c5 = false;
				int[] f = new int[10];
				f[0]++;
				int num = 1;
				while(!q.isEmpty()){
					int cur = q.poll();
					for(int[] e : g[cur]){
						if(d[e[0]] == -1){
							d[e[0]] = (d[cur] + e[1])%10;
							f[d[e[0]]]++;
							num++;
							q.add(e[0]);
						}else{
							int x = (d[cur]+e[1]+10-d[e[0]])%10;
							if(x != 0){
								x = gcd(x, 10);
								if(x == 1){
									c1 = true;
								}else if(x == 2){
									c2 = true;
								}else{
									c5 = true;
								}
							}
						}
					}
				}
				
				long[] xf = new long[10];
				for(int j = 0;j < 10;j++){
					for(int k = 0;k < 10;k++){
						xf[(k+10-j)%10] += (long)f[j]*f[k];
					}
				}
				xf[0] -= num;
				if(c2 && c5)c1 = true;
				if(c1){
					for(int j = 0;j < 10;j++){
						for(int k = 0;k < 10;k++){
							ret[k] += xf[j];
						}
					}
				}else if(c2){
					for(int j = 0;j < 10;j++){
						for(int k = 0;k < 10;k+=2){
							ret[(j+k)%10] += xf[j];
						}
					}
				}else if(c5){
					for(int j = 0;j < 10;j++){
						for(int k = 0;k < 10;k+=5){
							ret[(j+k)%10] += xf[j];
						}
					}
				}else{
					for(int j = 0;j < 10;j++){
						ret[j] += xf[j];
					}
				}
			}
		}
		for(long re : ret){
			out.println(re);
		}
	}
	
	public static int gcd(int a, int b) {
		while (b > 0) {
			int c = a;
			a = b;
			b = c % b;
		}
		return a;
	}
	
	
	public static class DJSet {
		public int[] upper;

		public DJSet(int n) {
			upper = new int[n];
			Arrays.fill(upper, -1);
		}

		public int root(int x) {
			return upper[x] < 0 ? x : (upper[x] = root(upper[x]));
		}

		public boolean equiv(int x, int y) {
			return root(x) == root(y);
		}

		public boolean union(int x, int y) {
			x = root(x);
			y = root(y);
			if (x != y) {
				if (upper[y] < upper[x]) {
					int d = x;
					x = y;
					y = d;
				}
				upper[x] += upper[y];
				upper[y] = x;
			}
			return x == y;
		}

		public int count() {
			int ct = 0;
			for (int u : upper)
				if (u < 0)
					ct++;
			return ct;
		}
	}

	public static int[][][] packWU(int n, int[] from, int[] to, int[] w) {
		int[][][] g = new int[n][][];
		int[] p = new int[n];
		for (int f : from)
			p[f]++;
		for (int t : to)
			p[t]++;
		for (int i = 0; i < n; i++)
			g[i] = new int[p[i]][2];
		for (int i = 0; i < from.length; i++) {
			--p[from[i]];
			g[from[i]][p[from[i]]][0] = to[i];
			g[from[i]][p[from[i]]][1] = w[i];
			--p[to[i]];
			g[to[i]][p[to[i]]][0] = from[i];
			g[to[i]][p[to[i]]][1] = (10-w[i])%10;
		}
		return g;
	}

	
	void run() throws Exception
	{
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
		
		long s = System.currentTimeMillis();
		solve();
		out.flush();
		if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");
	}
	
	public static void main(String[] args) throws Exception { new E().run(); }
	
	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;
	
	private int readByte()
	{
		if(lenbuf == -1)throw new InputMismatchException();
		if(ptrbuf >= lenbuf){
			ptrbuf = 0;
			try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
			if(lenbuf <= 0)return -1;
		}
		return inbuf[ptrbuf++];
	}
	
	private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
	private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
	
	private double nd() { return Double.parseDouble(ns()); }
	private char nc() { return (char)skip(); }
	
	private String ns()
	{
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
	
	private char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while(p < n && !(isSpaceChar(b))){
			buf[p++] = (char)b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
	
	private char[][] nm(int n, int m)
	{
		char[][] map = new char[n][];
		for(int i = 0;i < n;i++)map[i] = ns(m);
		return map;
	}
	
	private int[] na(int n)
	{
		int[] a = new int[n];
		for(int i = 0;i < n;i++)a[i] = ni();
		return a;
	}
	
	private int ni()
	{
		int num = 0, b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}
		
		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
	
	private long nl()
	{
		long num = 0;
		int b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}
		
		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
	
	private static void tr(Object... o) { System.out.println(Arrays.deepToString(o)); }
}
