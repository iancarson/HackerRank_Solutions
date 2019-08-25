import java.io.*;
import java.util.*;

public class Solution {

	BufferedReader br;
	PrintWriter out;
	StringTokenizer st;
	boolean eof;

	static final int INF = Integer.MAX_VALUE / 3;

	static class FlowGraph {
		int N, S, T;

		List<FlowEdge>[] g;

		int[] dist;
		private Deque<Integer> q;
		private int[] ptr;

		public FlowGraph(int n, int s, int t) {
			N = n;
			S = s;
			T = t;
			g = new List[N];
			for (int i = 0; i < N; i++) {
				g[i] = new ArrayList<>();
			}
			dist = new int[N];
			q = new ArrayDeque<>();
			ptr = new int[N];
		}

		public void addDirectedEdge(int v1, int v2, int cap) {
			FlowEdge e1 = new FlowEdge(v2, cap);
			FlowEdge e2 = new FlowEdge(v1, 0);
			e1.rev = e2;
			e2.rev = e1;
			g[v1].add(e1);
			g[v2].add(e2);
		}

		public void addUndirectedEdge(int v1, int v2, int cap) {
			FlowEdge e1 = new FlowEdge(v2, cap);
			FlowEdge e2 = new FlowEdge(v1, cap);
			e1.rev = e2;
			e2.rev = e1;
			g[v1].add(e1);
			g[v2].add(e2);
		}

		private boolean bfs() {
			Arrays.fill(dist, INF);
			dist[S] = 0;
			q.clear();
			q.add(S);

			while (!q.isEmpty()) {
				int v = q.poll();
				List<FlowEdge> adj = g[v];
				for (int i = 0; i < adj.size(); i++) {
					FlowEdge e = adj.get(i);
					if (e.flow < e.cap && dist[e.to] > dist[v] + 1) {
						dist[e.to] = dist[v] + 1;
						q.add(e.to);
					}
				}
			}
			return dist[T] != INF;
		}

		private int dfs(int v, int curFlow) {
			if (v == T)
				return curFlow;
			List<FlowEdge> adj = g[v];
			while (ptr[v] < adj.size()) {
				FlowEdge e = adj.get(ptr[v]);
				if (dist[e.to] == dist[v] + 1 && e.flow != e.cap) {
					int go = dfs(e.to, Math.min(curFlow, e.cap - e.flow));
					if (go != 0) {
						e.flow += go;
						e.rev.flow -= go;
						return go;
					}
				}
				ptr[v]++;
			}
			return 0;
		}

		public int maxFlow(int S, int T) {
			clear();
			this.S = S;
			this.T = T;
			int flow = 0;

			while (bfs()) {
				Arrays.fill(ptr, 0);
				int addFlow;
				do {
					addFlow = dfs(S, INF);
					flow += addFlow;
				} while (addFlow > 0);
			}

			return flow;
		}

		public void clear() {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < g[i].size(); j++) {
					g[i].get(j).flow = 0;
				}
			}
		}

		public boolean[] getCut() {
			/**
			 * only call after maxFlow invocation!
			 * using dist array from the last 
			 * bfs() call
			 */
			boolean[] ret = new boolean[N];
			for (int i = 0; i < N; i++) {
				ret[i] = dist[i] != INF;
			}
			return ret;
		}

	}

	static class FlowEdge {
		int to;
		int flow, cap;

		FlowEdge rev;

		public FlowEdge(int to, int cap) {
			this.to = to;
			this.cap = cap;
		}
	}

	void solve() throws IOException {
		int n = nextInt();
		int m = nextInt();
		FlowGraph g = new FlowGraph(n, -1, -1);
		for (int i = 0; i < m; i++) {
			int v1 = nextInt() - 1;
			int v2 = nextInt() - 1;
			int cap = nextInt();
			if (v1 == v2) {
				throw new AssertionError("Or should I?");
			}
			g.addUndirectedEdge(v1, v2, cap);
		}

		int ans = 1;

		int[] p = new int[n];
		Arrays.fill(p, 0);

		int[][] flowVal = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(flowVal[i], INF);
		}

		for (int i = 1; i < n; i++) {
			int val = g.maxFlow(i, p[i]);
			boolean[] cut = g.getCut();
			for (int j = i + 1; j < n; j++) {
				if (cut[j] && p[j] == p[i]) {
					p[j] = i;
				}
			}
			flowVal[i][p[i]] = flowVal[p[i]][i] = val;
			for (int j = 0; j < i; j++) {
				if (j != p[i]) {
					flowVal[i][j] = flowVal[j][i] = Math.min(val, flowVal[p[i]][j]);
				}
			}
		}
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < i; j++) {
				ans = mul(ans, flowVal[i][j]);
			}
		
		out.println(ans);
	}

	int mul(int a, int b) {
		return (int) ((long) a * b % MOD);
	}

	static final int MOD = 1_000_000_007;

	void inp() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new Solution().inp();
	}

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return null;
			}
		}
		return st.nextToken();
	}

	String nextString() {
		try {
			return br.readLine();
		} catch (IOException e) {
			eof = true;
			return null;
		}
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
}