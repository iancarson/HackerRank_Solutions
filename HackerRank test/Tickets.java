/* HackerRank Template */

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

public class Solution {
	
	static long initTime;
	static final Random rnd = new Random(7777L);
	static boolean writeLog = false;
	
	public static void main(String[] args) throws IOException {
//		initTime = System.currentTimeMillis();
//		try {
//			writeLog = "true".equals(System.getProperty("LOCAL_RUN_7777"));
//		} catch (SecurityException e) {}
//		new Thread(null, new Runnable() {
//			public void run() {
//				try {
//					try {
//						if (new File("input.txt").exists())
//							System.setIn(new FileInputStream("input.txt"));
//					} catch (SecurityException e) {}
//					long prevTime = System.currentTimeMillis();
//					new Solution().run();
//					writeLog("Total time: " + (System.currentTimeMillis() - prevTime) + " ms");
//					writeLog("Memory status: " + memoryStatus());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}, "1", 1L << 24).start();
		new Solution().run();
	}

	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		solve();
		out.close();
		in.close();
	}
	
	/*************************************************************** 
	 * Solution: min-cost-flow on multi-list
	 **************************************************************/

	final int INF = Integer.MAX_VALUE >> 1;
	final Map<String, Integer> indexes = new HashMap<String, Integer>(120);
	final int costOffset = 10000;
	final int edgesCap = 30;
	
	void solve() throws IOException  {
		
		int n = nextInt();
		int m = nextInt();
		int k = nextInt();
		
		int[] prices = new int [k];
		int[] destionations = new int [n];
		
		for (int i = 0; i < k; i++) {
			prices[indexOf(nextToken())] = 5 * nextInt();
		}
		
		for (int i = 0; i < n; i++) {
			destionations[i] = indexOf(nextToken());
		}
		
		
		
		int S = n + n;
		int T = S + 1;
		Net net = new Net(T + 1, n + n * (n - 1) / 2 + n);
		
		for (int i = 0; i < n; i++) {
			net.addDirectedEdge(S, i, 1, 0);
		}
		
		for (int i = 0; i < n; i++) {
			boolean first = true;
			int edges = 0;
			for (int j = i + 1; j < n; j++) {
				int discount = getDiscount(prices, destionations, i, j);
				if (discount == 0) {
					if (++edges <= edgesCap) {
						net.addDirectedEdge(i, n + j, 1, costOffset);
					}
				} else if (first) {
					first = false;
					net.addDirectedEdge(i, n + j, 1, costOffset - discount);
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			net.addDirectedEdge(n + i, T, 1, 0);
		}
		
		MinCostFlowAlgorithm minCostFlowAlgorithm = new MinCostFlowAlgorithm(net, S, T);
		
		int totalCost = minCostFlowAlgorithm.run(n - m);
		
		for (int i = 0; i < n; i++) {
			totalCost += prices[destionations[i]];
		}
		
		out.println(0.2 * totalCost);
		
		int[] pathIndexes = getPathIndexes(n, net);
		
		for (int pathIndex : pathIndexes) {
			out.println(pathIndex);
		}
	}
	
	int[] getPathIndexes(int n, Net net) {
		int[] pathIndexes = new int [n];
		
		int curIndex = 1;
		
		for (int i = 0; i < n; i++) {
			if (pathIndexes[i] == 0) {
				dfs(net, n, i, curIndex++, pathIndexes);
			}
		}
		
		return pathIndexes;
	}

	void dfs(Net net, int n, int v, int pathIndex, int[] pathIndexes) {
		pathIndexes[v] = pathIndex;
		
		for (int i = net.head[v]; i != 0; i = net.next[i]) {
			NetEdge edge = net.edges[i];
			if (edge.flow == 1) {
				dfs(net, n, edge.to - n, pathIndex, pathIndexes);
			}
		}
		
	}

	int getDiscount(int[] prices, int[] destionations, int i, int j) {
		if (destionations[i] == destionations[j]) {
			return prices[destionations[i]] / 5;
		}
		return 0;
	}

	int indexOf(String key) {
		Integer index = indexes.get(key);
		if (index == null) indexes.put(key, index = indexes.size());
		return index;
	}
	
	class MinCostFlowAlgorithm {
		Net net;
		int[] dist;
		int[] used;
		int[] phi;
		NetEdge[] prev;
		int tick = 1;
		
		int S, T;
		int cost;
		int flow;
		
		MinCostFlowAlgorithm(Net net, int S, int T) {
			this.net = net;
			dist = new int [net.vNum];
			used = new int [net.vNum];
			phi = new int [net.vNum];
			prev = new NetEdge [net.vNum];
			this.S = S;
			this.T = T;
		}
		
		int run(int minFlow) {
			
			cost = flow = 0;
			
			for (;;) {
				
				int pathCost = dijkstra();
				
//				if (pathCost == INF && flow < minFlow) {
//					throw new RuntimeException("Something wrong");
//				}
				
				if (pathCost >= 0 && flow >= minFlow) {
					break;
				}
				
				updateFlow();
			}
			
			return cost - flow * costOffset;
		}
		
		int dijkstra() {
			fill(dist, INF);
			tick++;
			dist[S] = 0;
			
			for (;;) {
				int v = -1;
				for (int i = 0; i < net.vNum; i++)
					if (used[i] != tick && dist[i] != INF && (v == -1 || dist[v] > dist[i]))
						v = i;
				if (v == -1) break;
				used[v] = tick;
				for (int i = net.head[v]; i != 0; i = net.next[i]) {
					NetEdge e = net.edges[i];
					if (e.restCapacity() > 0)
						if (dist[e.to] > dist[v] + e.cost + phi[v] - phi[e.to]) {
							dist[e.to] = dist[v] + e.cost + phi[v] - phi[e.to];
							prev[e.to] = e;
						}
				}
			}
			
			return dist[T];
		}
		
		void updateFlow() {
			for (int v = T; v != S; v = prev[v].from)
				cost += prev[v].inc();
			flow++;
			for (int v = 0; v < net.vNum; v++)
				phi[v] += dist[v] != INF ? dist[v] : dist[T];
		}
		
	}
	
	class NetEdge {
		int from;
		int to;
		int capacity;
		int flow;
		int cost;
		NetEdge back;
		
		NetEdge(int from, int to, int capacity, int cost) {
			this.from = from;
			this.to = to;
			this.capacity = capacity;
			this.cost = cost;
		}
		
		int restCapacity() {
			return capacity - flow;
		}
		
		void connect(NetEdge back) {
			this.back = back;
		}
		
		int inc() {
			flow++;
			back.flow--;
			return cost;
		}
	}
	
	class Net {
		int vNum;
		int[] head;
		int[] next;
		NetEdge[] edges;
		int cnt = 1;
		
		Net(int vNum, int eNum) {
			this.vNum = vNum;
			head = new int [vNum];
			next = new int [2 * eNum + 1];
			edges = new NetEdge [2 * eNum + 1];
		}
		
		void add(int v, NetEdge e) {
			next[cnt] = head[v];
			edges[cnt] = e;
			head[v] = cnt++;
		}
		
		void addDirectedEdge(int from, int to, int capacity, int cost) {
			NetEdge forward = new NetEdge(from, to, capacity, cost);
			NetEdge back = new NetEdge(to, from, 0, -cost);
			forward.connect(back);
			back.connect(forward);
			this.add(from, forward);
			this.add(to, back);
		}
	}
	
	/*************************************************************** 
	 * Input 
	 **************************************************************/
	BufferedReader in;
	PrintWriter out;
	StringTokenizer st = new StringTokenizer("");
	
	String nextToken() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(in.readLine());
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
	
	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}
	
	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}
	
	int[] nextIntArray(int size) throws IOException {
		int[] ret = new int [size];
		for (int i = 0; i < size; i++)
			ret[i] = nextInt();
		return ret;
	}
	
	long[] nextLongArray(int size) throws IOException {
		long[] ret = new long [size];
		for (int i = 0; i < size; i++)
			ret[i] = nextLong();
		return ret;
	}
	
	double[] nextDoubleArray(int size) throws IOException {
		double[] ret = new double [size];
		for (int i = 0; i < size; i++)
			ret[i] = nextDouble();
		return ret;
	}
	
	String nextLine() throws IOException {
		st = new StringTokenizer("");
		return in.readLine();
	}
	
	boolean EOF() throws IOException {
		while (!st.hasMoreTokens()) {
			String s = in.readLine();
			if (s == null)
				return true;
			st = new StringTokenizer(s);
		}
		return false;
	}
	
	/*************************************************************** 
	 * Output 
	 **************************************************************/
	void printRepeat(String s, int count) {
		for (int i = 0; i < count; i++)
			out.print(s);
	}
	
	void printArray(int[] array) {
		if (array == null || array.length == 0)
			return;
		for (int i = 0; i < array.length; i++) {
			if (i > 0) out.print(' ');
			out.print(array[i]);
		}
		out.println();
	}
	
	void printArray(long[] array) {
		if (array == null || array.length == 0)
			return;
		for (int i = 0; i < array.length; i++) {
			if (i > 0) out.print(' ');
			out.print(array[i]);
		}
		out.println();
	}
	
	void printArray(double[] array) {
		if (array == null || array.length == 0)
			return;
		for (int i = 0; i < array.length; i++) {
			if (i > 0) out.print(' ');
			out.print(array[i]);
		}
		out.println();
	}
	
	void printArray(double[] array, String spec) {
		if (array == null || array.length == 0)
			return;
		for (int i = 0; i < array.length; i++) {
			if (i > 0) out.print(' ');
			out.printf(Locale.US, spec, array[i]);
		}
		out.println();
	}
	
	void printArray(Object[] array) {
		if (array == null || array.length == 0)
			return;
		boolean blank = false;
		for (Object x : array) {
			if (blank) out.print(' '); else blank = true;
			out.print(x);
		}
		out.println();
	}
	
	@SuppressWarnings("rawtypes")
	void printCollection(Collection collection) {
		if (collection == null || collection.isEmpty())
			return;
		boolean blank = false;
		for (Object x : collection) {
			if (blank) out.print(' '); else blank = true;
			out.print(x);
		}
		out.println();
	}
	
	/*************************************************************** 
	 * Utility
	 **************************************************************/
	static String memoryStatus() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() >> 20) + "/" + (Runtime.getRuntime().totalMemory() >> 20) + " MB";
	}
	
	static void checkMemory() {
		System.err.println(memoryStatus());
	}
	
	static long prevTimeStamp = Long.MIN_VALUE;
	
	static void updateTimer() {
		prevTimeStamp = System.currentTimeMillis();
	}
	
	static long elapsedTime() {
		return (System.currentTimeMillis() - prevTimeStamp);
	}
	
	static void checkTimer() {
		System.err.println(elapsedTime() + " ms");
	}
	
	static void chk(boolean f) {
		if (!f) throw new RuntimeException("Assert failed");
	}
	
	static void chk(boolean f, String format, Object ... args) {
		if (!f) throw new RuntimeException(String.format(format, args));
	}
	
	static void writeLog(String format, Object ... args) {
		if (writeLog) System.err.println(String.format(Locale.US, format, args));
	}
	
	static void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	static void swap(long[] a, int i, int j) {
		long tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	static void swap(double[] a, int i, int j) {
		double tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	static void shuffle(int[] a, int from, int to) {
		for (int i = from; i < to; i++)
			swap(a, i, rnd.nextInt(a.length));
	}
	
	static void shuffle(long[] a, int from, int to) {
		for (int i = from; i < to; i++)
			swap(a, i, rnd.nextInt(a.length));
	}
	
	static void shuffle(double[] a, int from, int to) {
		for (int i = from; i < to; i++)
			swap(a, i, rnd.nextInt(a.length));
	}
	
	static void shuffle(int[] a) {
		if (a == null) return;
		shuffle(a, 0, a.length);
	}
	
	static void shuffle(long[] a) {
		if (a == null) return;
		shuffle(a, 0, a.length);
	}
	
	static void shuffle(double[] a) {
		if (a == null) return;
		shuffle(a, 0, a.length);
	}
	
	static long[] getPartialSums(int[] a) {
		final long[] sums = new long [a.length + 1];
		for (int i = 0; i < a.length; i++)
			sums[i + 1] = sums[i] + a[i];
		return sums;
	}
	
	static long[] getPartialSums(long[] a) {
		final long[] sums = new long [a.length + 1];
		for (int i = 0; i < a.length; i++)
			sums[i + 1] = sums[i] + a[i];
		return sums;
	}
	
	static int[] getOrderedSet(int[] a) {
		final int[] set = Arrays.copyOf(a, a.length);
		if (a.length == 0) return set;
		shuffle(set);
		sort(set);
		int k = 1;
		int prev = set[0];
		for (int i = 1; i < a.length; i++) {
			if (prev != set[i]) {
				set[k++] = prev = set[i];
			}
		}
		return Arrays.copyOf(set, k);
	}
	
	static long[] getOrderedSet(long[] a) {
		final long[] set = Arrays.copyOf(a, a.length);
		if (a.length == 0) return set;
		shuffle(set);
		sort(set);
		int k = 1;
		long prev = set[0];
		for (int i = 1; i < a.length; i++) {
			if (prev != set[i]) {
				set[k++] = prev = set[i];
			}
		}
		return Arrays.copyOf(set, k);
	}
}