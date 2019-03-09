import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;

public class Solution {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";
	
	static int[] primes = sieveEratosthenes((int)Math.pow(10,4.5));
	
	static class Datum
	{
		public int[][] fs;
		public int[] map;
		public int[][] g;
	}
	
	static Datum makeDatum(int[] a)
	{
		int n = a.length;
		int[][] fs = new int[n][];
		int[] fb = new int[10];
		for(int i = 0;i < n;i++){
			int q = a[i];
			int fp = 0;
			for(int j = 0;j < primes.length;j++){
				int p = primes[j];
				if(p*p > q)break;
				if(q % p == 0){
					fb[fp++] = p;
					q /= p;
				}
				while(q % p == 0)q /= p;
			}
			if(q > 1){
				fb[fp++] = q;
			}
			fs[i] = Arrays.copyOf(fb, fp);
		}
		
		int pp = 0;
		for(int[] f : fs)pp += f.length;
		int[] ap = new int[pp];
		pp = 0;
		for(int[] f : fs){
			for(int fu : f)ap[pp++] = fu;
		}
		Arrays.sort(ap);
		
		int[] map = new int[pp];
		int[] fre = new int[pp];
		int up = 0;
		int pre = 0;
		for(int i = 0;i < pp;i++){
			if(i == pp-1 || ap[i] != ap[i+1]){
				map[up] = ap[i];
				fre[up] = i-pre+1;
				up++;
				pre = i+1;
			}
		}
		map = Arrays.copyOf(map, up);
		fre = Arrays.copyOf(fre, up);
		
		int[][] g = new int[map.length][];
		for(int i = 0;i < map.length;i++)g[i] = new int[fre[i]];
		int[] gp = new int[map.length];
		for(int i = 0;i < fs.length;i++){
			for(int v : fs[i]){
				int ind = Arrays.binarySearch(map, v);
				g[ind][gp[ind]++] = i;
			}
		}
		
		Datum d = new Datum();
		d.g = g;
		d.map = map;
		d.fs = fs;
		
		return d;
	}
	
	static void solve()
	{
		int n = ni();
		int[] a = na(n);
		int[] b = na(n);
		
		Datum da = makeDatum(a);
		Datum db = makeDatum(b);
		int[] ua = new int[n];
		int[] ub = new int[n];
		Arrays.fill(ua, -1);
		Arrays.fill(ub, -1);
		for(int i = da.map.length-1, j = db.map.length-1;i >= 0 && j >= 0;){
			int vi = da.map[i], vj = db.map[j];
			if(vi > vj){
				i--;
			}else if(vi < vj){
				j--;
			}else{
				for(int k = 0, l = 0;;){
					while(k < da.g[i].length && ua[da.g[i][k]] >= 0)k++;
					while(l < db.g[j].length && ub[db.g[j][l]] >= 0)l++;
					if(k == da.g[i].length || l == db.g[j].length)break;
					ua[da.g[i][k]] = db.g[j][l]; ub[db.g[j][l]] = da.g[i][k];
				}
				i--; j--;
			}
		}
		int[] dbmap = new int[100000];
		Arrays.fill(dbmap, -1);
		for(int i = 0;i < db.map.length;i++){
			if(db.map[i] < 100000)dbmap[db.map[i]] = i;
		}
		
		boolean[] visited = new boolean[n];
		boolean[] vved = new boolean[db.g.length];
		for(int i = 0;i < n;i++){
			if(ua[i] == -1){
//				tr(i);
				if(visit(i, vved, visited, da, db, ub, dbmap)){
					Arrays.fill(visited, false);
					Arrays.fill(vved, false);
				}
			}
		}
		
		int mat = 0;
		for(int i = 0;i < n;i++){
			if(ub[i] >= 0)mat++;
		}
		out.println(mat);
	}
	
	public static boolean visit(int cur, boolean[] vved, boolean[] visited, Datum da, Datum db, int[] im, int[] dbmap)
	{
		if(cur == -1)return true;
		
		for(int f : da.fs[cur]){
			int ind;
			if(f < 100000){
				ind = dbmap[f];
			}else{
				ind = Arrays.binarySearch(db.map, f);
			}
			if(ind >= 0 && !vved[ind]){
				vved[ind] = true;
				for(int c : db.g[ind]){
					if(!visited[c]){
						visited[c] = true;
						if(visit(im[c], vved, visited, da, db, im, dbmap)){
							im[c] = cur;
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static int[] sieveEratosthenes(int n) {
		if(n <= 32){
			int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };
			for(int i = 0;i < primes.length;i++){
				if(n < primes[i]){
					return Arrays.copyOf(primes, i);
				}
			}
			return primes;
		}

		int u = n + 32;
		double lu = Math.log(u);
		int[] ret = new int[(int) (u / lu + u / lu / lu * 1.5)];
		ret[0] = 2;
		int pos = 1;

		int[] isp = new int[(n + 1) / 32 / 2 + 1];
		int sup = (n + 1) / 32 / 2 + 1;

		int[] tprimes = { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };
		for(int tp : tprimes){
			ret[pos++] = tp;
			int[] ptn = new int[tp];
			for(int i = (tp - 3) / 2;i < tp << 5;i += tp)
				ptn[i >> 5] |= 1 << (i & 31);
			for(int i = 0;i < tp;i++){
				for(int j = i;j < sup;j += tp)
					isp[j] |= ptn[i];
			}
		}

		// 3,5,7
		// 2x+3=n
		int[] magic = { 0, 1, 23, 2, 29, 24, 19, 3, 30, 27, 25, 11, 20, 8, 4,
				13, 31, 22, 28, 18, 26, 10, 7, 12, 21, 17, 9, 6, 16, 5, 15, 14 };
		int h = n / 2;
		for(int i = 0;i < sup;i++){
			for(int j = ~isp[i];j != 0;j &= j - 1){
				int pp = i << 5 | magic[(j & -j) * 0x076be629 >>> 27];
				int p = 2 * pp + 3;
				if(p > n)
					break;
				ret[pos++] = p;
				for(int q = pp;q <= h;q += p)
					isp[q >> 5] |= 1 << (q & 31);
			}
		}

		return Arrays.copyOf(ret, pos);
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
	
	private static boolean eof()
	{
		if(lenbuf == -1)return true;
		int lptr = ptrbuf;
		while(lptr < lenbuf)if(!isSpaceChar(inbuf[lptr++]))return false;
		
		try {
			is.mark(1000);
			while(true){
				int b = is.read();
				if(b == -1){
					is.reset();
					return true;
				}else if(!isSpaceChar(b)){
					is.reset();
					return false;
				}
			}
		} catch (IOException e) {
			return true;
		}
	}
	
	private static byte[] inbuf = new byte[1024];
	static int lenbuf = 0, ptrbuf = 0;
	
	private static int readByte()
	{
		if(lenbuf == -1)throw new InputMismatchException();
		if(ptrbuf >= lenbuf){
			ptrbuf = 0;
			try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
			if(lenbuf <= 0)return -1;
		}
		return inbuf[ptrbuf++];
	}
	
	private static boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
	private static int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
	
	private static double nd() { return Double.parseDouble(ns()); }
	private static char nc() { return (char)skip(); }
	
	private static String ns()
	{
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
	
	private static char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while(p < n && !(isSpaceChar(b))){
			buf[p++] = (char)b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
	
	private static char[][] nm(int n, int m)
	{
		char[][] map = new char[n][];
		for(int i = 0;i < n;i++)map[i] = ns(m);
		return map;
	}
	
	private static int[] na(int n)
	{
		int[] a = new int[n];
		for(int i = 0;i < n;i++)a[i] = ni();
		return a;
	}
	
	private static int ni()
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
	
	private static long nl()
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
	
	private static void tr(Object... o) { if(INPUT.length() != 0)System.out.println(Arrays.deepToString(o)); }
}