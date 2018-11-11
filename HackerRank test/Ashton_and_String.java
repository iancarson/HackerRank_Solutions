import java.io.*;
import java.math.*;
import java.util.*;

import static java.util.Arrays.*;

public class Solution {
	private static final int mod = (int)1e9+7;

	final Random random = new Random(0);
	final IOFast io = new IOFast();

	/// MAIN CODE
	public void run() throws IOException {
		int TEST_CASE = Integer.parseInt(new String(io.nextLine()).trim());
//		int TEST_CASE = 1;
		LOOP: while(TEST_CASE-- != 0) {
			char[] cs = io.next();
			int K = io.nextInt();
			SuffixArray sa = new SuffixArray(cs);
			io.out.println(sa.kthDistinctSubstring(K));
//			for(int i = 1; i <= 20; i++) {
//				io.out.print(sa.kthDistinctSubstring(i));
//				io.out.flush();
//			}
		}
	}
	
	
	static
	public class SuffixArray {
		private static final char[] mask = new char[] { 0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01 };
		
		private static boolean tget(final byte[] t, final int i) { return (t[i>>>3]&mask[i&7]) != 0; }
		private static void tset(final byte[] t, final int i, final boolean b) { 
			if(b) {
				t[i>>>3] |= mask[i&7];
			} else {
				t[i>>>3] &= ~mask[i&7];
			}
		}
		
		private static boolean isLMS(final byte[] t, final int i) { return i > 0 && tget(t, i) && !tget(t, i-1); }
		
		private static void getBuckets(final Slice s, int[] bkt, int n, int K, boolean end) {
			Arrays.fill(bkt, 0);
			for(int i = 0; i < n; i++) {
				bkt[s.get(i)]++;
			}
			for(int i = 0, sum = 0; i <= K; i++) {
				sum += bkt[i];
				bkt[i] = end ? sum : sum - bkt[i];
			}
		}
		
		private static void induceSAl(final byte[] t, final Slice SA, final Slice s, int[] bkt, int n, int K, boolean end) {
			getBuckets(s, bkt, n, K, end);
			for(int i = 0; i < n; i++) {
				final int j = SA.get(i) - 1;
				if(j >= 0 && !tget(t, j)) {
					SA.set(bkt[s.get(j)]++, j);
				}
			}
		}
		
		private static void induceSAs(final byte[] t, final Slice SA, final Slice s, int[] bkt, int n, int K, boolean end) {
			getBuckets(s, bkt, n, K, end);
			for(int i = n - 1; i >= 0; i--) {
				final int j = SA.get(i) - 1;
				if(j >= 0 && tget(t, j)) {
					SA.set(--bkt[s.get(j)], j);
				}
			}
		}
		
		private static void build(final Slice s, final Slice SA, final int n, final int K) {
			final byte[] t = new byte[n/8+1];
			
			tset(t, n-2, false);
			tset(t, n-1, true);
			
			for(int i = n - 3; i >= 0; i--) {
				tset(t, i, s.get(i) < s.get(i+1) || s.get(i) == s.get(i+1) && tget(t, i+1));
			}
			
			// stage 1
			final int[] bkt = new int[K + 1];
			getBuckets(s, bkt, n, K, true);
			for(int i = 0; i < n; i++) {
				SA.set(i, -1);
			}
			
			for(int i = 1; i < n; i++) {
				if(isLMS(t, i)) {
					SA.set(--bkt[s.get(i)], i);
				}
			}

			induceSAl(t, SA, s, bkt, n, K, false);
			induceSAs(t, SA, s, bkt, n, K, true);
			
			int n1 = 0;
			for(int i = 0; i < n; i++) {
				if(isLMS(t, SA.get(i))) {
					SA.set(n1++, SA.get(i));
				}
			}
			
			for(int i = n1; i < n; i++) {
				SA.set(i, -1);
			}
			
			int name = 0, prev = -1;
			for(int i = 0; i < n1; i++) {
				int pos = SA.get(i);
				boolean diff = false;
				for(int d = 0; d < n; d++) {
					if(prev == -1
					|| s.get(pos+d) != s.get(prev+d)
					|| (tget(t, pos+d) ^ tget(t, prev+d))) {
						diff = true;
						break;
					} else if(d > 0 && (isLMS(t, pos+d) || isLMS(t, prev+d))) {
						break;
					}
				}
				if(diff) { name++; prev = pos; }
				pos >>>= 1;
				SA.set(n1+pos, name - 1);
			}
			
			for(int i = n - 1, j = n - 1; i >= n1; i--) {
				if(SA.get(i) >= 0) {
					SA.set(j--, SA.get(i));
				}
			}
			
			// stage 2
			final Slice SA1 = new Slice(SA, 0);
			final Slice s1 = new Slice(SA, n - n1);
			if(name < n1) {
				build(s1, SA1, n1, name - 1);
			} else {
				for(int i = 0; i < n1; i++) {
					SA1.set(s1.get(i), i);
				}
			}
			
			// stage 3
//			bkt = new int[K + 1];
			getBuckets(s, bkt, n, K, true);
			for(int i = 1, j = 0; i < n; i++) {
				if(isLMS(t, i)) {
					s1.set(j++, i);
				}
			}
			
			for(int i = 0; i < n1; i++) {
				SA1.set(i, s1.get(SA1.get(i)));
			}
			
			for(int i = n1; i < n; i++) {
				SA.set(i, -1);
			}
			for(int i = n1 - 1, j; i >= 0; i--) {
				j = SA.get(i);
				SA.set(i, -1);
				SA.set(--bkt[s.get(j)], j);
			}
			induceSAl(t, SA, s, bkt, n, K, false);
			induceSAs(t, SA, s, bkt, n, K, true);
		}
		
		private static int[] constructSA(final int[] s_, final int n, final int K) {
			final Slice s = new Slice(s_, 0);
			final Slice SA = new Slice(new int[n], 0);
			build(s, SA, n, K);
//			return Arrays.copyOfRange(SA.ary, 1, SA.ary.length);
			return SA.ary;
		}
		
		
		
		private final int n;
		private final char[] cs;
		public final int[] sa, lcp, rank;
		public SuffixArray(final char[] cs) {
			this.cs = cs;
			int[] input = new int[cs.length + 1];
			for(int i = 0; i < cs.length; i++) {
				input[i] = cs[i] - 'a' + 1;
			}
			sa = constructSA(input, input.length, 27);
			n = cs.length;
			rank = new int[n + 1];
			lcp = new int[n];
			constructLcp();
		}
		
		private void constructLcp() {
			for(int i = 0; i <= n; i++) {
				rank[sa[i]] = i;
			}
			
			int h = 0;
			lcp[0] = 0;
			for(int i = 0; i < n; i++) {
				final int j = sa[rank[i] - 1];
				if(h > 0) h--;
				for(; j + h < n && i + h < n; h++) {
					if(cs[j + h] != cs[i + h]) {
						break;
					}
				}
				lcp[rank[i] - 1] = h;
			}
		}
		
		private static class Slice {
			private final int shift;
			private final int[] ary;
			
			public int get(final int idx) { return ary[shift + idx]; }
			public void set(final int idx, final int val) { ary[shift + idx] = val; }
			
			public Slice(final int[] ary, final int shift) {
				this.ary = ary;
				this.shift = shift;
			}
			
			public Slice(final Slice s, final int shift) {
				this.ary = s.ary;
				this.shift = s.shift + shift;
			}
		}
		
		public long numberOfDistinctSubstrings() {
//			long ret = n * (n + 1L) / 2;
			long ret = 0;
			for(int i = 1; i <= n; i++) {
//				ret -= lcp[i - 1];
				ret += n - sa[i] - lcp[i - 1];
			}
			return ret;
		}
		
		public char kthDistinctSubstring(long k) {
			for(int i = 1; ; i++) {
				for(int j = sa[i] + lcp[i-1]; j < n; j++) {
					for(int l = sa[i]; l <= j; l++) {
						if(--k == 0) {
							return cs[l];
						}
					}
				}
			}
		}
		
		/*
		public void outputKthDistinctSubstring(int k, IOFast io) {
			for(int i = 1, v; i <= n; i++, k -= v) {
				v = n - sa[i] - lcp[i - 1];
				if(v > k) {
					for(int j = sa[i]; j < sa[i] + lcp[i - 1] + 1 + k; j++) {
						io.out.print(cs[j]);
					}
					io.out.println();
					return;
				}
			}
		}
		*/
		
		public static long numberOfDistinctSubstringsNaive(String s) {
			Set<String> set = new TreeSet<String>();
			for(int i = 0; i < s.length(); i++) {
				for(int j = i + 1; j <= s.length(); j++) {
					set.add(s.substring(i, j));
				}
			}
			return set.size();
		}
		
		public static String kthDistinctSubstringNaive(String s, long k) {
			TreeSet<String> set = new TreeSet<String>();
			for(int i = 0; i < s.length(); i++) {
				for(int j = i + 1; j <= s.length(); j++) {
					set.add(s.substring(i, j));
				}
			}
			while(k-- > 0 && !set.isEmpty()) { set.pollFirst(); }
			return set.isEmpty() ? " " : set.first();
		}
	}


	/// TEMPLATE
	static int gcd(int n, int r) { return r == 0 ? n : gcd(r, n%r); }
	static long gcd(long n, long r) { return r == 0 ? n : gcd(r, n%r); }
	
	static <T> void swap(T[] x, int i, int j) {
		T t = x[i];
		x[i] = x[j];
		x[j] = t;
	}
	
	static void swap(int[] x, int i, int j) {
		int t = x[i];
		x[i] = x[j];
		x[j] = t;
	}
	

	static void radixSort(int[] xs) {
		int[] cnt = new int[(1<<16)+1];
		int[] ys = new int[xs.length];
		
		for(int j = 0; j <= 16; j += 16) {
			Arrays.fill(cnt, 0);
			for(int x : xs) { cnt[(x>>j&0xFFFF)+1]++; }
			for(int i = 1; i < cnt.length; i++) { cnt[i] += cnt[i-1]; }
			for(int x : xs) { ys[cnt[x>>j&0xFFFF]++] = x; }
			{ final int[] t = xs; xs = ys; ys = t; }
		}
	}
	
	static void radixSort(long[] xs) {
		int[] cnt = new int[(1<<16)+1];
		long[] ys = new long[xs.length];
		
		for(int j = 0; j <= 48; j += 16) {
			Arrays.fill(cnt, 0);
			for(long x : xs) { cnt[(int)(x>>j&0xFFFF)+1]++; }
			for(int i = 1; i < cnt.length; i++) { cnt[i] += cnt[i-1]; }
			for(long x : xs) { ys[cnt[(int)(x>>j&0xFFFF)]++] = x; }
			{ final long[] t = xs; xs = ys; ys = t; }
		}
	}
	

	static void arrayIntSort(int[][] x, int... keys) {
		Arrays.sort(x, new ArrayIntsComparator(keys));
	}
	
	static class ArrayIntsComparator implements Comparator<int[]> {
		final int[] KEY;
		
		public ArrayIntsComparator(int... key) {
			KEY = key;
		}
		
		@Override
		public int compare(int[] o1, int[] o2) {
			for(int k : KEY) if(o1[k] != o2[k]) return o1[k] - o2[k];
			return 0;
		}
	}
	
	static class ArrayIntComparator implements Comparator<int[]> {
		final int KEY;
		
		public ArrayIntComparator(int key) {
			KEY = key;
		}
		
		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[KEY] - o2[KEY];
		}
	}
	
	
	void main() throws IOException {
		//		IOFast.setFileIO("rle-size.in", "rle-size.out");
		try {
			run();
		}
		catch (EndOfFileRuntimeException e) { }
		io.out.flush();
	}

	public static void main(String[] args) throws IOException {
		new Solution().main();
	}
	
	static class EndOfFileRuntimeException extends RuntimeException {
		private static final long serialVersionUID = -8565341110209207657L; }

	static
	public class IOFast {
		private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		private PrintWriter out = new PrintWriter(System.out);

		void setFileIO(String ins, String outs) throws IOException {
			out.flush();
			out.close();
			in.close();
			in = new BufferedReader(new FileReader(ins));
			out = new PrintWriter(new FileWriter(outs));
			System.err.println("reading from " + ins);
		}

		//		private static final int BUFFER_SIZE = 50 * 200000;
		private static int pos, readLen;
		private static final char[] buffer = new char[1024 * 8];
		private static char[] str = new char[500*8*2];
		private static boolean[] isDigit = new boolean[256];
		private static boolean[] isSpace = new boolean[256];
		private static boolean[] isLineSep = new boolean[256];

		static {
			for(int i = 0; i < 10; i++) { isDigit['0' + i] = true; }
			isDigit['-'] = true;
			isSpace[' '] = isSpace['\r'] = isSpace['\n'] = isSpace['\t'] = true;
			isLineSep['\r'] = isLineSep['\n'] = true;
		}

		public int read() throws IOException {
			if(pos >= readLen) {
				pos = 0;
				readLen = in.read(buffer);
				if(readLen <= 0) { throw new EndOfFileRuntimeException(); }
			}
			return buffer[pos++];
		}

		public int nextInt() throws IOException {
			int len = 0;
			str[len++] = nextChar();
			len = reads(len, isSpace);
			
			int i = 0;
			int ret = 0;
			if(str[0] == '-') { i = 1; }
			for(; i < len; i++) ret = ret * 10 + str[i] - '0';
			if(str[0] == '-') { ret = -ret; }
			return ret;
//			return Integer.parseInt(nextString());
		}

		public long nextLong() throws IOException {
			int len = 0;
			str[len++] = nextChar();
			len = reads(len, isSpace);
			
			int i = 0;
			long ret = 0;
			if(str[0] == '-') { i = 1; }
			for(; i < len; i++) ret = ret * 10 + str[i] - '0';
			if(str[0] == '-') { ret = -ret; }
			return ret;
//			return Long.parseLong(nextString());
		}

		public char nextChar() throws IOException {
			while(true) {
				final int c = read();
				if(!isSpace[c]) { return (char)c; }
			}
		}
		
		int reads(int len, boolean[] accept) throws IOException {
			try {
				while(true) {
					final int c = read();
					if(accept[c]) { break; }
					
					if(str.length == len) {
						char[] rep = new char[str.length * 3 / 2];
						System.arraycopy(str, 0, rep, 0, str.length);
						str = rep;
					}
					
					str[len++] = (char)c;
				}
			}
			catch(EndOfFileRuntimeException e) { ; }
			
			return len;
		}
		
		int reads(char[] cs, int len, boolean[] accept) throws IOException {
			try {
				while(true) {
					final int c = read();
					if(accept[c]) { break; }
					cs[len++] = (char)c;
				}
			}
			catch(EndOfFileRuntimeException e) { ; }
			
			return len;
		}

		public char[] nextLine() throws IOException {
			int len = 0;
			str[len++] = nextChar();
//			str[len++] = (char)read();
			len = reads(len, isLineSep);
			
			try {
				if(str[len-1] == '\r') { len--; read(); }
			}
			catch(EndOfFileRuntimeException e) { ; }
			
			return Arrays.copyOf(str, len);
		}

		public String nextString() throws IOException {
			return new String(next());
		}

		public char[] next() throws IOException {
			int len = 0;
			str[len++] = nextChar();
			len = reads(len, isSpace);
			return Arrays.copyOf(str, len);
		}

		public int next(char[] cs) throws IOException {
			int len = 0;
			cs[len++] = nextChar();
			len = reads(cs, len, isSpace);
			return len;
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(nextString());
		}

		public long[] nextLongArray(final int n) throws IOException {
			final long[] res = new long[n];
			for(int i = 0; i < n; i++) {
				res[i] = nextLong();
			}
			return res;
		}

		public int[] nextIntArray(final int n) throws IOException {
			final int[] res = new int[n];
			for(int i = 0; i < n; i++) {
				res[i] = nextInt();
			}
			return res;
		}

		public int[][] nextIntArray2D(final int n, final int k) throws IOException {
			final int[][] res = new int[n][];
			for(int i = 0; i < n; i++) {
				res[i] = nextIntArray(k);
			}
			return res;
		}

		public int[][] nextIntArray2DWithIndex(final int n, final int k) throws IOException {
			final int[][] res = new int[n][k+1];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < k; j++) {
					res[i][j] = nextInt();
				}
				res[i][k] = i;
			}
			return res;
		}

		public double[] nextDoubleArray(final int n) throws IOException {
			final double[] res = new double[n];
			for(int i = 0; i < n; i++) {
				res[i] = nextDouble();
			}
			return res;
		}

	}

}