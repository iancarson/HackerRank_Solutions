
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class F {
	InputStream is;
	PrintWriter out;
	String INPUT = "";
	
	long ret;
	int[] freq;
	int[] pfreq;
	EulerTour et;
	int[] lpf = enumLowestPrimeFactors(10000005);
	int[] mob = enumMobiusByLPF(10000005, lpf);
	int[] a;
	
	void solve()
	{
		int n = ni(), Q = ni();
		a = na(n);
		for(int i = 0;i < n;i++){
			int pre = -1;
			int mul = 1;
			for(int j = a[i];j > 1;j /= lpf[j]){
				if(pre != lpf[j]){
					mul *= lpf[j];
					pre = lpf[j];
				}
			}
			a[i] = mul;
		}
		
		int[] from = new int[n - 1];
		int[] to = new int[n - 1];
		for (int i = 0; i < n - 1; i++) {
			from[i] = ni() - 1;
			to[i] = ni() - 1;
		}
		int[][] g = packU(n, from, to);
		int[][] pars = parents3(g, 0);
		int[] par = pars[0], ord = pars[1], dep = pars[2];
		
		et = nodalEulerTour(g, 0);
		int[][] spar = logstepParents(par);
		
		int[][] qs = new int[Q][];
		int[] special = new int[Q];
		Arrays.fill(special, -1);
		for(int i = 0;i < Q;i++){
			int x = ni()-1, y = ni()-1;
			int lca = lca2(x, y, spar, dep);
			if(lca == x){
				qs[i] = new int[]{et.first[x], et.first[y]};
			}else if(lca == y){
				qs[i] = new int[]{et.first[y], et.first[x]};
			}else if(et.first[x] < et.first[y]){
				qs[i] = new int[]{et.last[x], et.first[y]};
				special[i] = lca;
			}else{
				qs[i] = new int[]{et.last[y], et.first[x]};
				special[i] = lca;
			}
		}
		
		long[] pqs = sqrtSort(qs, 2*n-1);
		
		int L = 0, R = -1;
		freq = new int[n];
		
		long[] ans = new long[Q];
		pfreq = new int[10000005];
		for(long pa : pqs){
			int ind = (int)(pa&(1<<25)-1);
			int ql = qs[ind][0], qr = qs[ind][1];
			while(R < qr)change(++R, 1);
			while(L > ql)change(--L, 1);
			while(R > qr)change(R--, -1);
			while(L < ql)change(L++, -1);
			if(special[ind] != -1)change(et.first[special[ind]], 1);
//			tr(qs[ind], freq, special[ind]);
//			trnz(pfreq);
			ans[ind] = ret;
			if(special[ind] != -1)change(et.first[special[ind]], -1);
		}
//		tr(et.vs);
		for(long v : ans){
			out.println(v);
		}
	}
	
	public static void trnz(int... o)
	{
		for(int i = 0;i < o.length;i++)if(o[i] != 0)System.out.print(i+":"+o[i]+" ");
		System.out.println();
	}

	
	public static int[] enumMobiusByLPF(int n, int[] lpf)
	{
		int[] mob = new int[n+1];
		mob[1] = 1;
		for(int i = 2;i <= n;i++){
			int j = i/lpf[i];
			if(lpf[j] == lpf[i]){
//				mob[i] = 0;
			}else{
				mob[i] = -mob[j];
			}
		}
		return mob;
	}
	
	void dfs(int cur, int n, int d)
	{
		if(n == 1){
			if(d > 0)ret += mob[cur] * pfreq[cur];
			pfreq[cur] += d;
			if(d < 0)ret -= mob[cur] * pfreq[cur];
			return;
		}
		
		dfs(cur, n/lpf[n], d);
		dfs(cur/lpf[n], n/lpf[n], d);
	}
	
	void change(int x, int d)
	{
		int ind = et.vs[x];
		if(freq[ind] == 1){
			dfs(a[ind], a[ind], -1);
		}
		freq[ind] += d;
		if(freq[ind] == 1){
			dfs(a[ind], a[ind], 1);
		}
	}
	
	public static long[] sqrtSort(int[][] qs, int n)
	{
		int m = qs.length;
		long[] pack = new long[m];
		int S = (int)Math.sqrt(n);
		for(int i = 0;i < m;i++){
			pack[i] = (long)qs[i][0]/S<<50|(long)((qs[i][0]/S&1)==0?qs[i][1]:(1<<25)-1-qs[i][1])<<25|i;
		}
		Arrays.sort(pack);
		return pack;
	}
	
	
	public static int lca2(int a, int b, int[][] spar, int[] depth) {
		if (depth[a] < depth[b]) {
			b = ancestor(b, depth[b] - depth[a], spar);
		} else if (depth[a] > depth[b]) {
			a = ancestor(a, depth[a] - depth[b], spar);
		}

		if (a == b)
			return a;
		int sa = a, sb = b;
		for (int low = 0, high = depth[a], t = Integer.highestOneBit(high), k = Integer
				.numberOfTrailingZeros(t); t > 0; t >>>= 1, k--) {
			if ((low ^ high) >= t) {
				if (spar[k][sa] != spar[k][sb]) {
					low |= t;
					sa = spar[k][sa];
					sb = spar[k][sb];
				} else {
					high = low | t - 1;
				}
			}
		}
		return spar[0][sa];
	}

	protected static int ancestor(int a, int m, int[][] spar) {
		for (int i = 0; m > 0 && a != -1; m >>>= 1, i++) {
			if ((m & 1) == 1)
				a = spar[i][a];
		}
		return a;
	}

	public static int[][] logstepParents(int[] par) {
		int n = par.length;
		int m = Integer.numberOfTrailingZeros(Integer.highestOneBit(n - 1)) + 1;
		int[][] pars = new int[m][n];
		pars[0] = par;
		for (int j = 1; j < m; j++) {
			for (int i = 0; i < n; i++) {
				pars[j][i] = pars[j - 1][i] == -1 ? -1 : pars[j - 1][pars[j - 1][i]];
			}
		}
		return pars;
	}


	
	public static class EulerTour
	{
		public int[] vs; // vertices
		public int[] first; // first appeared time
		public int[] last; // last appeared time
		
		public EulerTour(int[] vs, int[] f, int[] l) {
			this.vs = vs;
			this.first = f;
			this.last = l;
		}
	}
	
	public static EulerTour nodalEulerTour(int[][] g, int root)
	{
		int n = g.length;
		int[] vs = new int[2*n];
		int[] f = new int[n];
		int[] l = new int[n];
		int p = 0;
		Arrays.fill(f, -1);
		
		int[] stack = new int[n];
		int[] inds = new int[n];
		int sp = 0;
		stack[sp++] = root;
		outer:
		while(sp > 0){
			int cur = stack[sp-1], ind = inds[sp-1];
			if(ind == 0){
				vs[p] = cur;
				f[cur] = p;
				p++;
			}
			while(ind < g[cur].length){
				int nex = g[cur][ind++];
				if(f[nex] == -1){ // child
					inds[sp-1] = ind;
					stack[sp] = nex;
					inds[sp] = 0;
					sp++;
					continue outer;
				}
			}
			inds[sp-1] = ind;
			if(ind == g[cur].length){
				vs[p] = cur;
				l[cur] = p;
				p++;
				sp--;
			}
		}
		
		return new EulerTour(vs, f, l);
	}


	public static int[][] parents3(int[][] g, int root) {
		int n = g.length;
		int[] par = new int[n];
		Arrays.fill(par, -1);

		int[] depth = new int[n];
		depth[0] = 0;

		int[] q = new int[n];
		q[0] = root;
		for (int p = 0, r = 1; p < r; p++) {
			int cur = q[p];
			for (int nex : g[cur]) {
				if (par[cur] != nex) {
					q[r++] = nex;
					par[nex] = cur;
					depth[nex] = depth[cur] + 1;
				}
			}
		}
		return new int[][] { par, q, depth };
	}

	static int[][] packU(int n, int[] from, int[] to) {
		int[][] g = new int[n][];
		int[] p = new int[n];
		for (int f : from)
			p[f]++;
		for (int t : to)
			p[t]++;
		for (int i = 0; i < n; i++)
			g[i] = new int[p[i]];
		for (int i = 0; i < from.length; i++) {
			g[from[i]][--p[from[i]]] = to[i];
			g[to[i]][--p[to[i]]] = from[i];
		}
		return g;
	}

	
	public static int[] enumLowestPrimeFactors(int n) {
		int tot = 0;
		int[] lpf = new int[n + 1];
		int u = n + 32;
		double lu = Math.log(u);
		int[] primes = new int[(int) (u / lu + u / lu / lu * 1.5)];
		for (int i = 2; i <= n; i++)
			lpf[i] = i;
		for (int p = 2; p <= n; p++) {
			if (lpf[p] == p)
				primes[tot++] = p;
			int tmp;
			for (int i = 0; i < tot && primes[i] <= lpf[p] && (tmp = primes[i] * p) <= n; i++) {
				lpf[tmp] = primes[i];
			}
		}
		return lpf;
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
	
	public static void main(String[] args) throws Exception { new F().run(); }
	
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