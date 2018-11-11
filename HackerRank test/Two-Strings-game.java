import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;




import static java.util.Arrays.*;

public class Solution {
	private static final int mod = (int)1e9+7;

	final Random random = new Random(0);
	final IOFast io = new IOFast();

	/// MAIN CODE
	int grundyNaive(String s, String ss) {
		final Set<Integer> g = new TreeSet<>();
		for(char c = 'a'; c <= 'z'; c++) {
			if(s.indexOf(ss + c) >= 0) {
				g.add(grundyNaive(s, ss + c));
			}
		}
		for(int i = 0; ; i++) {
			if(!g.contains(i)) {
				System.err.println(s + " " + ss + " " + i);
				return i;
			}
		}
	}
	
	
	
	long K;
	SuffixTreeRevenge st1, st2;
	
	long cnt(int g) {
//		System.err.println("cnt: " + st2._sumGrundy[st2._root] + " " + st2._grundyMap[st2._root].get(g));
		/*
		return st2._grundyMap[st2._root].containsKey(g)
			? st2._sumGrundy[st2._root] - st2._grundyMap[st2._root].get(g)
			: st2._sumGrundy[st2._root];
		*/
		return st2._sumGrundy[st2._root] - st2._grundyMap2[g][st2._root];
	}
	
	long cnt3(int idx) {
		long ans = 0;
		/*
		for(Entry<Integer, Long> e : st1._grundyMap[idx].entrySet()) {
			ans += cnt(e.getKey()) * e.getValue();
			ans = Math.min(K + 1, ans);
		}
		*/
		for(int i = 0; i < st1._grundyMap2.length; i++) if(st1._grundyMap2[i][idx] != 0) {
			if(cnt(i) > K / st1._grundyMap2[i][idx]) {
				ans = K + 1;
			}
			else {
				ans += cnt(i) * st1._grundyMap2[i][idx];
				ans = Math.min(K + 1, ans);
			}
		}
		return ans;
	}
	
	long cnt2(int g, int idx) {
		return st2._sumGrundy[idx] - st2._grundyMap2[g][idx];
		/*
		return st2._grundyMap[idx].containsKey(g)
			? st2._sumGrundy[idx] - st2._grundyMap[idx].get(g)
			: st2._sumGrundy[idx];
		*/
	}
	
	int ansLen1, ansLen2;
	char[] cs1, cs2;
	char[] ans1, ans2;
	void restore(int g, int idx) {
//		if(idx == 0) System.err.println("restore: " + g + " " + st2._grundy[idx] + " " + idx + " " + K);
		if(st2._grundy[idx] != g) { K--; }
		if(K == 0) { return; }
		for(int i = 0; i < st2.ALPHABET_SIZE; i++) {
			final int ei = st2._nextEdge[i][idx];
			if(ei == -1) { continue; }
			if(K <= cnt2(g, ei)) {
				for(int j = 0, v = st2._startEdgeVal[ei]; j < st2._len[ei] - 1; j++) {
					if(v != g) { K--; }
					ans2[ansLen2++] = cs2[st2._start[ei] + j];
					if(K == 0) { return; }
					v ^= 1;
				}
				ans2[ansLen2++] = cs2[st2._start[ei] + st2._len[ei] - 1];
				restore(g, ei);
				return;
			}
			K -= cnt2(g, ei);
		}
	}
	
	boolean dfs(int idx) {
//		if(idx == 0 || true) System.err.println("dfs: " + st1._grundy[idx] + " " + cnt(st1._grundy[idx]) + " " + idx + " " + K);
		if(K <= cnt(st1._grundy[idx])) {
//			System.err.println("dfs: " + K + " " + idx + " " + cnt(st1._grundy[idx]));
			restore(st1._grundy[idx], st2._root);
			return true;
		}
		
		K -= cnt(st1._grundy[idx]);
		for(int i = 0; i < st1.ALPHABET_SIZE; i++) {
			final int ei = st1._nextEdge[i][idx];
			if(ei == -1) { continue; }
			if(K <= cnt3(ei)) {
				for(int j = 0, v = st1._startEdgeVal[ei]; j < st1._len[ei] - 1; j++) {
//					System.err.println("dfs2: " + idx + " " + i + " " + j + " " + v);
					ans1[ansLen1++] = cs1[st1._start[ei] + j];
					if(K <= cnt(v)) {
						restore(v, st2._root);
						return true;
					}
					K -= cnt(v);
					v ^= 1;
				}
				ans1[ansLen1++] = cs1[st1._start[ei] + st1._len[ei] - 1];
				if(dfs(ei)) return true;
			}
			K -= cnt3(ei);
		}
		return false;
	}
	
	public void run() throws IOException {
//		int TEST_CASE = Integer.parseInt(new String(io.nextLine()).trim());
		int TEST_CASE = 1;
		while(TEST_CASE-- != 0) {
			int n = io.nextInt();
			int m = io.nextInt();
			K = io.nextLong();
			cs1 = io.next();
			cs2 = io.next();

			st1 = new SuffixTreeRevenge(26, cs1.length);
			st2 = new SuffixTreeRevenge(26, cs2.length);

			ans1 = new char[cs1.length];
			ans2 = new char[cs2.length];

			for(char c : cs1) st1.add(c - 'a');
			for(char c : cs2) st2.add(c - 'a');

			st1.initGrundy();
			st2.initGrundy();
			
			/*
			io.out.println(dfs(st1._root));
			io.out.println("ans: " + new String(ans1, 0, ansLen1));
			io.out.println("ans: " + new String(ans2, 0, ansLen2));
			*/
//			if(true) return;
			
			if(dfs(st1._root)) {
				io.out.println(new String(ans1, 0, ansLen1));
				io.out.println(new String(ans2, 0, ansLen2));
			}
			else {
				io.out.println("no solution");
			}

			/*
			grundyNaive(new String(cs1), "");
			grundyNaive(new String(cs2), "");
			//*/
		}
	}
	
	static
	public class SuffixTreeRevenge {
		final int MAX_EDGE;
		final int MAX_LEN;
		final int ALPHABET_SIZE;
		final int MAX_GRUNDY = 7;
		
		int _root;
		int _curEdge;
		int _curOffset;
		
		int _strIndex;
		int _edgeLen;
		
		int[] _suffixLink;
		int[] _start;
		int[] _len;
		int[] _str;
		int[] _parent;
		
		int[][] _nextEdge;
		
		int[] _startEdgeVal;
		int[] _grundy;
//		TreeMap<Integer, Long>[] _grundyMap;
		long[][] _grundyMap2;
		long[] _sumGrundy;
		
		void add(int idx, int key, long val) {
			_sumGrundy[idx] += val;
			/*
			if(_grundyMap[idx].containsKey(key)) {
				val += _grundyMap[idx].get(key);
			}
			_grundyMap[idx].put(key, val);
			*/
			_grundyMap2[key][idx] += val;
		}

		void initGrundy() {
			initGrundy(_root);
		}

		void initGrundy(int idx) {
			int grundyFlag = 0;
//			_grundyMap[idx] = new TreeMap<>();
			for(int i = 0; i < ALPHABET_SIZE; i++) {
				final int ei = _nextEdge[i][idx];
				if(ei != -1) {
					initGrundy(ei);
					if(_len[ei] == 1) {
//						set.add(_grundy[ei]);
						grundyFlag |= 1 << _grundy[ei];
					}
					else {
						if(_grundy[ei] == 0) {
//							set.add(1^_len[ei]&1);
							grundyFlag |= 1 << (1^_len[ei]&1);
						}
						else {
//							set.add(_len[ei]&1);
							grundyFlag |= 1 << (_len[ei]&1);
						}
					}
					/*
					add(idx, _grundy[ei], 1);
					if(_len[ei] == 1) {
						set.add(_grundy[ei]);
					}
					else {
						if(_grundy[ei] == 0) {
							set.add(1^_len[ei]&1);
							add(idx, 1, _len[ei] / 2);
							add(idx, 0, (_len[ei] - 1) / 2);
							_startEdgeVal[ei] = 1;
						}
						else {
							set.add(_len[ei]&1);
							add(idx, 0, _len[ei] / 2);
							add(idx, 1, (_len[ei] - 1) / 2);
							_startEdgeVal[ei] = 0;
						}
					}
					//*/
					for(int j = 0; j < MAX_GRUNDY; j++) {
//						_grundyMap2[j][idx] += _grundyMap2[j][ei];
						add(idx, j, _grundyMap2[j][ei]);
					}
					/*
					for(Entry<Integer, Long> e : _grundyMap[ei].entrySet()) {
						add(idx, e.getKey(), e.getValue());
					}*/
				}
			}
			for(int i = 0; ; i++) {
//				if(!set.contains(i)) {
				if((grundyFlag>>i&1) == 0) {
//					_sumGrundy[idx]++;
					_grundy[idx] = i;
//					System.err.println(_start[idx] + " " + _len[idx] + ": " + i);
					break;
				}
			}
			//*
			add(idx, _grundy[idx], 1);
			if(_len[idx] > 1) {
				if(_grundy[idx] == 0) {
					add(idx, 1, _len[idx] / 2);
					add(idx, 0, (_len[idx] - 1) / 2);
					_startEdgeVal[idx] = 1^_len[idx]&1;
				}
				else {
					add(idx, 0, _len[idx] / 2);
					add(idx, 1, (_len[idx] - 1) / 2);
					_startEdgeVal[idx] = _len[idx]&1;
				}
			}
			//*/
		}
		
		public SuffixTreeRevenge(final int alphabetSize, final int capacity) {
			ALPHABET_SIZE	= alphabetSize;
			MAX_LEN			= capacity;
			MAX_EDGE		= MAX_LEN * 2;
			
			_start		= new int[MAX_EDGE];
			_len		= new int[MAX_EDGE];
			_suffixLink	= new int[MAX_EDGE];
			_parent		= new int[MAX_EDGE];
			_nextEdge	= new int[ALPHABET_SIZE][MAX_EDGE];
			_str = new int[MAX_LEN];
//			_grundyMap = new TreeMap[MAX_EDGE];
			_grundyMap2 = new long[MAX_GRUNDY][MAX_EDGE];
			_grundy = new int[MAX_EDGE];
			_startEdgeVal = new int[MAX_EDGE];
			_sumGrundy = new long[MAX_EDGE];
			
			_curEdge = _root = _edgeLen++;
			_parent[_root] = -1;
			for(int i = 0; i < _nextEdge.length; i++) {
				_nextEdge[i][0] = -1;
			}
		}
		
		void appendEdge(final int c, int prevEdge) {
			while(true) {
				if(prevEdge != -1) {
					_suffixLink[prevEdge] = _curEdge;
				}
				prevEdge = _curEdge;
				
				if(_nextEdge[c][_curEdge] != -1) {
					_curOffset = 1;
					_curEdge = _nextEdge[c][_curEdge];
					break;
				}
				
				addEdge(c);
	
				if(_curEdge == _root) {
					_curOffset = 0;
					break;
				}
				
				_curEdge = _suffixLink[_curEdge];
			}
		}
		
		int addEdge(final int c, final int targetEdge) {
			final int e = _edgeLen++;
			
			_nextEdge[c][targetEdge] = e;
			
			_start[e] = _strIndex;
			_len[e] = MAX_LEN - _strIndex;
			_parent[e] = targetEdge;
			_suffixLink[e] = -1;
			for(int i = 0; i < _nextEdge.length; i++) {
				_nextEdge[i][e] = -1;
			}
			return e;
		}
		
		int addEdge(final int c) {
			return addEdge(c, _curEdge);
		}
		
		void moveEdge() {
			int offset = 0;
			int p = _start[_curEdge];
			
			if(_parent[_curEdge] == _root) {
				_curOffset--;
				p++;
			}
	
			int e = _suffixLink[_parent[_curEdge]];
			p -= _len[e];
			_curOffset += _len[e];
			while(_len[e] < _curOffset) {
				offset += _len[e];
				_curOffset -= _len[e];
				e = _nextEdge[_str[p + offset]][e];
			}
			_curEdge = e;
		}
		
		void sepEdge(final int c) {
			final int newEdge = _edgeLen++;
			
			for(int i = 0; i < _nextEdge.length; i++) {
				_nextEdge[i][newEdge] = -1;
			}
			
			final int cc = _str[_start[_curEdge] + _curOffset];
			
			final int p = _parent[_curEdge];
			final int s = _str[_start[_curEdge]];
			_nextEdge[s][p] = newEdge;
			
			_parent[newEdge] = _parent[_curEdge];
			_nextEdge[cc][newEdge] = _curEdge;
			_start[newEdge] = _start[_curEdge];
			_len[newEdge] = _curOffset;
			
			_parent[_curEdge] = newEdge;
			_start[_curEdge] += _curOffset;
			_len[_curEdge] -= _curOffset;
			
			// add
			addEdge(c, newEdge);
			
			_curEdge = newEdge;
		}
		
		void cutEdge(final int c) {
			int prevEdge = -1;
			while(true) {
				if(_curOffset == _len[_curEdge]) {
					appendEdge(c, prevEdge);
					break;
				}
				
				if(_str[_start[_curEdge] + _curOffset] == c) {
					_curOffset++;
					break;
				}
				
				sepEdge(c);
				
				if(_parent[_curEdge] == _root && _curOffset == -1) {
					_curEdge = _root;
					_curOffset = 0;
					break;
				}
				
				if(-1 != prevEdge) {
					_suffixLink[prevEdge] = _curEdge;
				}
				prevEdge = _curEdge;
				
				moveEdge();
			}
		}
		
		public void add(final int c) {
			_str[_strIndex] = c;
			cutEdge(c);
			_strIndex++;
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
			return Integer.parseInt(nextString());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(nextString());
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