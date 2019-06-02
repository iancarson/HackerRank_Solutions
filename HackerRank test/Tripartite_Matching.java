import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

publi class D
{
	InputStream is;
	PrintWriter out;
	String INPUT = "";
	int[][] read(int n)
	{
		int m = ni();
		int[] from = new int[m];
		int[] to = new int[m];
		for(int i = 0;i  <  m;i++)
		{
			from[i] = ni() - 1;
			to[i] = ni() - 1;
		}
		return packU(n, from, to);
	}
	void solve()
	{
		int n = ni();
		int[][] ga = read(n);
		int[][] gb = read(n);
		int[][] gc = read(n);
		
		int S = (int)Math.sqrt(100000);
		long[][] gbb = new long[n][];
		long[][] gbc = new long[n][];
		for(int i = 0;i < n;i++){
			if(gb[i].length >= S){
				gbb[i] = new long[(n>>>6)+1];
				for(int e : gb[i]){
					gbb[i][e>>>6] |= 1L<<e;
				}
			}
			if(gc[i].length >= S){
				gbc[i] = new long[(n>>>6)+1];
				for(int e : gc[i]){
					gbc[i][e>>>6] |= 1L<<e;
				}
			}
			Arrays.sort(gb[i]);
			Arrays.sort(gc[i]);
		}
		
		int na = ga.length;
		long ret = 0;
		for(int a = 0;a < na;a++){
			for(int b : ga[a]){
				if(gbb[b] != null){
					if(gbc[a] != null){
						for(int i = 0;i < (n>>>6)+1;i++){
							ret += Long.bitCount(gbb[b][i]&gbc[a][i]);
						}
					}else{
						for(int e : gc[a]){
							if(gbb[b][e>>>6]<<~e<0)ret++;
						}
					}
				}else{
					if(gbc[a] != null){
						for(int e : gb[b]){
							if(gbc[a][e>>>6]<<~e<0)ret++;
						}
					}else{
						for(int i = 0, j = 0;i < gb[b].length && j < gc[a].length;){
							if(gb[b][i] == gc[a][j]){
								ret++; i++; j++;
							}else if(gb[b][i] < gc[a][j]){
								i++;
							}else{
								j++;
							}
						}
					}
				}
			}
		}
		out.println(ret);
	}
	static int[][] packU(int n, int[] from, int[] to)
	{
		int[][] g = new int[n][];
		int[] p = new int[n];
		for(int f : from)
		{
			p[f]++;
		}
		for(int t : to)
			p[t]++;
		for(int i = 0; i < n;i++)
		{
			g[i] = new int[p[i]];
		}
		for(int i = 0; i < from.length; i++)
		{
			g[from[i]][--p[from[i]]] = to[i];
			g[to[i]][--p[to[i]]] = from[i];
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
	
	public static void main(String[] args) throws Exception { new D().run(); }
	
	private byte[] inbuf = new byte[1024];
	private int lenbuf = 0, ptrbuf = 0;
	
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
