import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class G2 {
	InputStream is;
	PrintWriter out;
	String INPUT = "";
	
	void solve()
	{
		int n = ni(), m = ni(), K = ni();
		int[] f = na(n);
		for(int i = 0;i < n;i++)f[i]--;
		long[] ps = new long[m];
		for(int i = 0;i < m;i++)ps[i] = nl();
		int mod = 998244353;
		long[] made = make(ps, K, n+1, 1);
		
		int H = (int)Math.sqrt(n)*8; // naive height limit
		int B = (int)Math.sqrt(n)*8; // cycle split period
		
		SplitResult sres = split(f);
		int[] tclus = new int[n];
		Arrays.fill(tclus, -1);
		for(int i = n-1;i >= 0;i--){
			int cur = sres.ord[i];
			if(sres.incycle[cur]){
				tclus[cur] = cur;
			}else{
				tclus[cur] = tclus[f[cur]];
			}
		}
//		tr("phase 1");
		long[] rets = new long[n];
		int[][] maps = makeBuckets(tclus, n);
		for(int i = 0;i < n;i++){
			if(maps[i].length > 0){
				int[] map = maps[i];
				int[] lpar = new int[map.length];
				int p = 0;
				for(int x : maps[i]){
					if(sres.incycle[x]){
						lpar[p++] = -1;
					}else{
						lpar[p++] = Arrays.binarySearch(map, f[x]);
					}
				}
				long[] res = solve(parentToG(lpar), lpar, made, H, Arrays.binarySearch(map, i));
				for(int j = 0;j < res.length;j++){
					if(!sres.incycle[map[j]]){
						rets[map[j]] += res[j];
					}
				}
			}
		}
		
		int[] maxdep = new int[n];
		for(int i = 0;i < n;i++){
			int cur = sres.ord[i];
			if(!sres.incycle[cur]){
				maxdep[f[cur]] = Math.max(maxdep[f[cur]], maxdep[cur]+1);
			}
		}
		int[] tdep = new int[n];
		for(int i = n-1;i >= 0;i--){
			int cur = sres.ord[i];
			if(!sres.incycle[cur]){
				tdep[cur] = tdep[f[cur]]+1;
			}
		}
		
		boolean[] ved = new boolean[n];
		int[] cycle = new int[n];
		Map<Long, long[]> cache = new HashMap<>();
		for(int i = 0;i < n;i++){
			if(sres.incycle[i] && !ved[i]){
				int p = 0;
				ved[i] = true;
				cycle[p++] = i;
				int lmaxdep = maxdep[i];
				for(int j = f[i];!ved[j];j = f[j]){
					ved[j] = true;
					cycle[p++] = j;
					lmaxdep = Math.max(lmaxdep, maxdep[j]);
				}
				int tail = lmaxdep+p+1;
				int fp = p;
				long[] di = cache.computeIfAbsent((long)tail<<32|fp, (z) -> {
					long[] res = make(ps, K, tail, fp);
					for(int j = res.length-fp-1;j >= 0;j--){
						res[j] += res[j+fp];
						if(res[j] >= mod)res[j] -= mod;
					}
					return res;
				});
				
				if(p <= B){
					for(int j = 0;j < p;j++){
						for(int v : maps[cycle[j]]){
							for(int k = tdep[v], l = j;k < tdep[v]+p;k++,l++){
								if(l == p)l = 0;
								rets[cycle[l]] += di[k];
							}
						}
					}
				}else{
					inputed = null;
					for(int b = 0;b < p;b+=B){
						long[] ents = new long[tail+1];
						for(int j = 0;j < b;j++){
							for(int v : maps[cycle[j]]){
								ents[tail-(b-j)-tdep[v]]++;
							}
						}
						for(int j = b+B;j < p;j++){
							for(int v : maps[cycle[j]]){
								ents[tail-b-(p-j)-tdep[v]]++;
							}
						}
						long[] ced = convoluteSimply(ents, di, mod, 3);
						inputed = saved;
						for(int k = b;k < p && k < b+B;k++){
							rets[cycle[k]] += ced[tail+k-b];
						}
					}
					inputed = null;
					
					// remainder
					for(int j = 0;j < p;j++){
						for(int v : maps[cycle[j]]){
							for(int k = tdep[v], l = j;k < tdep[v]+p && l < p && l < j/B*B+B;k++,l++){
								rets[cycle[l]] += di[k];
							}
							for(int k = tdep[v]+p-j+j/B*B, l = j/B*B;k < tdep[v]+p && l < j;k++,l++){
								rets[cycle[l]] += di[k];
							}
						}
					}
				}
			}
		}
//		tr("phase 4");
		
		long RN = invl(n, mod);
		for(long ret : rets){
			out.println(ret%mod*RN%mod);
		}
	}
	
	int mod = 998244353;
	
	long[] make(long[] ps, int K, int tail, int period)
	{
		long[] ms = ps;
		if(ps.length > tail+period){
			ms = Arrays.copyOf(ps, tail+period);
			for(int j = tail+period, k = tail;j < ps.length;j++,k++){
				if(k == tail+period)k -= period;
				ms[k] += ps[j];
				if(ms[k] >= mod)ms[k] -= mod;
			}
		}
		
		long[] pps = new long[1];
		pps[0] = 1;
		for(int i = 0;1<<i <= K;i++){
			if(K<<~i<0){
				long[] res = convoluteSimply(pps, ms, mod, 3);
				for(int j = res.length-1-period;j >= tail;j--){
					res[j] += res[j+period];
					res[j+period] = 0;
					if(res[j] >= mod)res[j] -= mod;
				}
				pps = Arrays.copyOf(res, Math.min(tail+period, pps.length+ms.length));
			}
			
			if(1<<i+1 <= K){
				long[] res = convoluteSimply(ms, ms, mod, 3);
				for(int j = res.length-1-period;j >= tail;j--){
					res[j] += res[j+period];
					res[j+period] = 0;
					if(res[j] >= mod)res[j] -= mod;
				}
				ms = Arrays.copyOf(res, Math.min(tail+period, ms.length+ms.length));
			}
		}
		if(pps.length < tail+period)pps = Arrays.copyOf(pps, tail+period);
		return pps;
	}
	
	long[] solve(int[][] g, int[] par, long[] di, int H, int root)
	{
		int n = g.length;
		long[] ret = new long[n];
		int[][] pars = parents3(g, root);
		int[] des = new int[n];
		long[] ws = new long[n];
		int[] ord = pars[1], dep = pars[2];
		int[] marked = new int[n];
		for(int i = n-1;i >= 0;i--){
			int cur = ord[i];
			des[cur]++;
			ws[cur] += des[cur];
			if(marked[cur] == 0 && ws[cur] > (long)H*des[cur]){
				marked[cur] = 1;
			}
			if(i > 0){
				des[par[cur]] += des[cur];
				ws[par[cur]] += ws[cur];
				if(marked[cur] >= 1)marked[par[cur]] = 2;
			}
		}
		
//		tr(g, root);
//		tr(marked);
		
		// large
		// marked node
		for(int i = 0;i < n;i++){
			if(marked[i] == 1){
				int[] fdep = new int[n];
				collect(i, par[i], g, dep, fdep);
				for(int j = par[i];j != -1 && marked[j] == 2;j = par[j]){
					fdep[dep[j]]++;
					marked[j] = 3;
				}
				
				int lmaxdep = n;
				for(int j = n-1;j >= 0;j--){
					if(fdep[j] > 0){
						lmaxdep = j;
						break;
					}
				}
				long[] rfdep = new long[lmaxdep+1];
				for(int j = 0;j <= lmaxdep;j++){
					rfdep[lmaxdep-j] = fdep[j];
				}
				
//				tr("fdep", fdep, marked);
				long[] ced = convoluteSimply(rfdep, Arrays.copyOf(di, lmaxdep+1), mod, 3);
				for(int j = i;j != -1;j = par[j]){
					ret[j] += ced[lmaxdep-dep[j]];
				}
			}
		}
		
		// small
		for(int i = 0;i < n;i++){
			if(marked[i] == 0){
				for(int j = i;j != -1 && marked[j] != 1;j = par[j]){
					ret[j] += di[dep[i]-dep[j]];
				}
			}
		}
		
		for(int i = 0;i < n;i++){
			ret[i] %= mod;
		}
		
		return ret;
	}
	
	void collect(int cur, int par, int[][] g, int[] dep, int[] fdep)
	{
		fdep[dep[cur]]++;
		for(int e : g[cur]){
			if(e != par)collect(e, cur, g, dep, fdep);
		}
	}
	
	// library
	
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

	
	public static final int[] NTTPrimes = {1053818881, 1051721729, 1045430273, 1012924417, 1007681537, 1004535809, 998244353, 985661441, 976224257, 975175681};
	public static final int[] NTTPrimitiveRoots = {7, 6, 3, 5, 3, 3, 3, 3, 3, 17};
//	public static final int[] NTTPrimes = {1012924417, 1004535809, 998244353, 985661441, 975175681, 962592769, 950009857, 943718401, 935329793, 924844033};
//	public static final int[] NTTPrimitiveRoots = {5, 3, 3, 3, 17, 7, 7, 7, 3, 5};
	
	static long[] inputed;
	static long[] saved;
	
	public static long[] convoluteSimply(long[] a, long[] b, int P, int g)
	{
		int m = Math.max(2, Integer.highestOneBit(Math.max(a.length, b.length)-1)<<2);
		long[] fa = nttmb(a, m, false, P, g);
		long[] fb = a == b ? fa : inputed != null ? inputed : nttmb(b, m, false, P, g);
		saved = fb;
		for(int i = 0;i < m;i++){
			fa[i] = fa[i]*fb[i]%P;
		}
		return nttmb(fa, m, true, P, g);
	}
	
	public static long[] convolute(long[] a, long[] b)
	{
		int USE = 2;
		int m = Math.max(2, Integer.highestOneBit(Math.max(a.length, b.length)-1)<<2);
		long[][] fs = new long[USE][];
		for(int k = 0;k < USE;k++){
			int P = NTTPrimes[k], g = NTTPrimitiveRoots[k];
			long[] fa = nttmb(a, m, false, P, g);
			long[] fb = a == b ? fa : nttmb(b, m, false, P, g);
			for(int i = 0;i < m;i++){
				fa[i] = fa[i]*fb[i]%P;
			}
			fs[k] = nttmb(fa, m, true, P, g);
		}
		
		int[] mods = Arrays.copyOf(NTTPrimes, USE);
		long[] gammas = garnerPrepare(mods);
		int[] buf = new int[USE];
		for(int i = 0;i < fs[0].length;i++){
			for(int j = 0;j < USE;j++)buf[j] = (int)fs[j][i];
			long[] res = garnerBatch(buf, mods, gammas);
			long ret = 0;
			for(int j = res.length-1;j >= 0;j--)ret = ret * mods[j] + res[j];
			fs[0][i] = ret;
		}
		return fs[0];
	}
	
	public static long[] convolute(long[] a, long[] b, int USE, int mod)
	{
		int m = Math.max(2, Integer.highestOneBit(Math.max(a.length, b.length)-1)<<2);
		long[][] fs = new long[USE][];
		for(int k = 0;k < USE;k++){
			int P = NTTPrimes[k], g = NTTPrimitiveRoots[k];
			long[] fa = nttmb(a, m, false, P, g);
			long[] fb = a == b ? fa : nttmb(b, m, false, P, g);
			for(int i = 0;i < m;i++){
				fa[i] = fa[i]*fb[i]%P;
			}
			fs[k] = nttmb(fa, m, true, P, g);
		}
		
		int[] mods = Arrays.copyOf(NTTPrimes, USE);
		long[] gammas = garnerPrepare(mods);
		int[] buf = new int[USE];
		for(int i = 0;i < fs[0].length;i++){
			for(int j = 0;j < USE;j++)buf[j] = (int)fs[j][i];
			long[] res = garnerBatch(buf, mods, gammas);
			long ret = 0;
			for(int j = res.length-1;j >= 0;j--)ret = (ret * mods[j] + res[j]) % mod;
			fs[0][i] = ret;
		}
		return fs[0];
	}
	
	// static int[] wws = new int[270000]; // outer faster
	
	// Modifed Montgomery + Barrett
	private static long[] nttmb(long[] src, int n, boolean inverse, int P, int g)
	{
		long[] dst = Arrays.copyOf(src, n);
		
		int h = Integer.numberOfTrailingZeros(n);
		long K = Integer.highestOneBit(P)<<1;
		int H = Long.numberOfTrailingZeros(K)*2;
		long M = K*K/P;
		
		int[] wws = new int[1<<h-1];
		long dw = inverse ? pow(g, P-1-(P-1)/n, P) : pow(g, (P-1)/n, P);
		long w = (1L<<32)%P;
		for(int k = 0;k < 1<<h-1;k++){
			wws[k] = (int)w;
			w = modh(w*dw, M, H, P);
		}
		long J = invl(P, 1L<<32);
		for(int i = 0;i < h;i++){
			for(int j = 0;j < 1<<i;j++){
				for(int k = 0, s = j<<h-i, t = s|1<<h-i-1;k < 1<<h-i-1;k++,s++,t++){
					long u = (dst[s] - dst[t] + 2*P)*wws[k];
					dst[s] += dst[t];
					if(dst[s] >= 2*P)dst[s] -= 2*P;
//					long Q = (u&(1L<<32)-1)*J&(1L<<32)-1;
					long Q = (u<<32)*J>>>32;
					dst[t] = (u>>>32)-(Q*P>>>32)+P;
				}
			}
			if(i < h-1){
				for(int k = 0;k < 1<<h-i-2;k++)wws[k] = wws[k*2];
			}
		}
		for(int i = 0;i < n;i++){
			if(dst[i] >= P)dst[i] -= P;
		}
		for(int i = 0;i < n;i++){
			int rev = Integer.reverse(i)>>>-h;
			if(i < rev){
				long d = dst[i]; dst[i] = dst[rev]; dst[rev] = d;
			}
		}
		
		if(inverse){
			long in = invl(n, P);
			for(int i = 0;i < n;i++)dst[i] = modh(dst[i]*in, M, H, P);
		}
		
		return dst;
	}
	
	// Modified Shoup + Barrett
	private static long[] nttsb(long[] src, int n, boolean inverse, int P, int g)
	{
		long[] dst = Arrays.copyOf(src, n);
		
		int h = Integer.numberOfTrailingZeros(n);
		long K = Integer.highestOneBit(P)<<1;
		int H = Long.numberOfTrailingZeros(K)*2;
		long M = K*K/P;
		
		long dw = inverse ? pow(g, P-1-(P-1)/n, P) : pow(g, (P-1)/n, P);
		long[] wws = new long[1<<h-1];
		long[] ws = new long[1<<h-1];
		long w = 1;
		for(int k = 0;k < 1<<h-1;k++){
			wws[k] = (w<<32)/P;
			ws[k] = w;
			w = modh(w*dw, M, H, P);
		}
		for(int i = 0;i < h;i++){
			for(int j = 0;j < 1<<i;j++){
				for(int k = 0, s = j<<h-i, t = s|1<<h-i-1;k < 1<<h-i-1;k++,s++,t++){
					long ndsts = dst[s] + dst[t];
					if(ndsts >= 2*P)ndsts -= 2*P;
					long T = dst[s] - dst[t] + 2*P;
					long Q = wws[k]*T>>>32;
					dst[s] = ndsts;
					dst[t] = ws[k]*T-Q*P&(1L<<32)-1;
				}
			}
//			dw = dw * dw % P;
			if(i < h-1){
				for(int k = 0;k < 1<<h-i-2;k++){
					wws[k] = wws[k*2];
					ws[k] = ws[k*2];
				}
			}
		}
		for(int i = 0;i < n;i++){
			if(dst[i] >= P)dst[i] -= P;
		}
		for(int i = 0;i < n;i++){
			int rev = Integer.reverse(i)>>>-h;
			if(i < rev){
				long d = dst[i]; dst[i] = dst[rev]; dst[rev] = d;
			}
		}
		
		if(inverse){
			long in = invl(n, P);
			for(int i = 0;i < n;i++){
				dst[i] = modh(dst[i] * in, M, H, P);
			}
		}
		
		return dst;
	}
	
	static final long mask = (1L<<31)-1;
	
	public static long modh(long a, long M, int h, int mod)
	{
		long r = a-((M*(a&mask)>>>31)+M*(a>>>31)>>>h-31)*mod;
		return r < mod ? r : r-mod;
	}
	
	private static long[] garnerPrepare(int[] m)
	{
		int n = m.length;
		assert n == m.length;
		if(n == 0)return new long[0];
		long[] gamma = new long[n];
		for(int k = 1;k < n;k++){
			long prod = 1;
			for(int i = 0;i < k;i++){
				prod = prod * m[i] % m[k];
			}
			gamma[k] = invl(prod, m[k]);
		}
		return gamma;
	}
	
	private static long[] garnerBatch(int[] u, int[] m, long[] gamma)
	{
		int n = u.length;
		assert n == m.length;
		long[] v = new long[n];
		v[0] = u[0];
		for(int k = 1;k < n;k++){
			long temp = v[k-1];
			for(int j = k-2;j >= 0;j--){
				temp = (temp * m[j] + v[j]) % m[k];
			}
			v[k] = (u[k] - temp) * gamma[k] % m[k];
			if(v[k] < 0)v[k] += m[k];
		}
		return v;
	}
	
	private static long pow(long a, long n, long mod) {
		//		a %= mod;
		long ret = 1;
		int x = 63 - Long.numberOfLeadingZeros(n);
		for (; x >= 0; x--) {
			ret = ret * ret % mod;
			if (n << 63 - x < 0)
				ret = ret * a % mod;
		}
		return ret;
	}
	
	private static long invl(long a, long mod) {
		long b = mod;
		long p = 1, q = 0;
		while (b > 0) {
			long c = a / b;
			long d;
			d = a;
			a = b;
			b = d % b;
			d = p;
			p = q;
			q = d - c * q;
		}
		return p < 0 ? p + mod : p;
	}
	
	public static int[][] parentToG(int[] par)
	{
		int n = par.length;
		int[] ct = new int[n];
		for(int i = 0;i < n;i++){
			if(par[i] >= 0){
				ct[i]++;
				ct[par[i]]++;
			}
		}
		int[][] g = new int[n][];
		for(int i = 0;i < n;i++){
			g[i] = new int[ct[i]];
		}
		for(int i = 0;i < n;i++){
			if(par[i] >= 0){
				g[par[i]][--ct[par[i]]] = i;
				g[i][--ct[i]] = par[i];
			}
		}
		return g;
	}

	
	public static int[][] makeBuckets(int[] a, int sup)
	{
		int n = a.length;
		int[][] bucket = new int[sup+1][];
		int[] bp = new int[sup+1];
		for(int i = 0;i < n;i++)bp[a[i]]++;
		for(int i = 0;i <= sup;i++)bucket[i] = new int[bp[i]];
		for(int i = n-1;i >= 0;i--)bucket[a[i]][--bp[a[i]]] = i;
		return bucket;
	}

	
	public static class SplitResult
	{
		public boolean[] incycle;
		public int[] ord;
	}
	
	public static SplitResult split(int[] f)
	{
		int n = f.length;
		boolean[] incycle = new boolean[n];
		Arrays.fill(incycle, true);
		int[] indeg = new int[n];
		for(int i = 0;i < n;i++)indeg[f[i]]++;
		int[] q = new int[n];
		int qp = 0;
		for(int i = 0;i < n;i++){
			if(indeg[i] == 0)q[qp++] = i;
		}
		for(int r = 0;r < qp;r++){
			int cur = q[r];
			indeg[cur] = -9999999;
			incycle[cur] = false;
			int e = f[cur];
			indeg[e]--;
			if(indeg[e] == 0)q[qp++] = e;
		}
		for(int i = 0;i < n;i++){
			if(indeg[i] == 1){
				q[qp++] = i;
			}
		}
		assert qp == n;
		SplitResult ret = new SplitResult();
		ret.incycle = incycle;
		ret.ord = q;
		return ret;
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
	
	public static void main(String[] args) throws Exception { new G2().run(); }
	
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