import java.util.*;
import static java.lang.Math.*;
import java.io.*;

public class Solution {
	static class Foo47 {
		/*
		 * after 2 dijkstra's parse, each node has 2 shortest path values from src and dest
		 * then from one shortest path tree, traverse the edges on shortest path from src to dest,
		 * while doing this, removing or adding edges cross the cut, and update the balanced bst
		 * each edge will be processed once, so this step will be O(nlgn) which will run in time
		 */
		static class Edge {
			int i;
			int j;
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj instanceof Edge) {
					Edge b = (Edge)obj;
					return i == b.i && j == b.j;
				}
				return false;
			}
			public int hashCode() {
				return i*31 + j;
			}
			public String toString() {
				return "[" + i + ", " + j + "]";
			}
			public Edge(int i, int j) {
				this.i = i;
				this.j = j;
			}
		}
		
		static class Shortest implements Comparable<Shortest> {
			long dist;
			int i;
			int j;
			public int compareTo(Shortest b) {
				if (dist != b.dist) return dist - b.dist < 0 ? -1 : 1;
				if (i != b.i) return i - b.i;
				return j - b.j;
			}
			public String toString() {
				return "[" + dist + ": " + i + ", " + j + "]";
			}
			public Shortest(int i, int j, long dist) {
				this.i = i;
				this.j = j;
				this.dist = dist;
			}
		}
		
		static class TreeNode {
			int i;
			int parent;
			HashSet<TreeNode> child = new HashSet<TreeNode>();
			public String toString() {
				return "" + i + ": " + child;
			}
		}
		
		static long INF = Long.MAX_VALUE/3;
		
		int N;
		TreeMap<Integer, Long>[] graph;
		long[] distSrc;
		long[] distDest;
		ArrayList<Integer> path = new ArrayList<Integer>();
		boolean reachable = true;
		HashMap<Edge, Long> removeMap = new HashMap<Edge, Long>();
		
		void main() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(System.in));
				String[] s = br.readLine().split(" ");
				N = Integer.parseInt(s[0].trim());
				int M = Integer.parseInt(s[1].trim());
				long t = System.currentTimeMillis();
				graph = new TreeMap[N];
				for (int i = 0; i < N; i++) {
					graph[i] = new TreeMap<Integer, Long>();
				}
				distSrc = new long[N];
				distDest = new long[N];
				
				for (int i = 0; i < M; i++) {
					s = br.readLine().split(" ");
					int u = Integer.parseInt(s[0].trim());
					int v = Integer.parseInt(s[1].trim());
					long wei = Long.parseLong(s[2].trim());
					graph[u].put(v, wei);
					graph[v].put(u, wei);
				}
				
				s = br.readLine().split(" ");
				int src = Integer.parseInt(s[0].trim());
				int dest = Integer.parseInt(s[1].trim());
				foo(src, dest);
				
				int Q = Integer.parseInt(br.readLine());
				for (int i = 0; i < Q; i++) {
					s = br.readLine().split(" ");
					int u = Integer.parseInt(s[0].trim());
					int v = Integer.parseInt(s[1].trim());
					long dist = getDist(u, v, dest);
					System.out.println(dist >= INF ? "Infinity" : dist);
				}
				
				//System.out.println(System.currentTimeMillis() - t);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		long getDist(int u, int v, int dest) {
			int i = min(u, v);
			int j = max(u, v);
			Long dis = removeMap.get(new Edge(i, j));
			if (dis == null)
				return distSrc[dest];
			return dis;
		}
		
		void foo(int src, int dest) {
			distSrc = new long[N];
			distDest = new long[N];
			TreeNode root = dijkstra(true, src, dest, distSrc);
			dijkstra(false, dest, src, distDest);
			if (path.get(0) != src) {
				reachable = false;
				return;
			}
			HashSet<Integer> left = new HashSet<Integer>();
			HashSet<Integer> right = new HashSet<Integer>();
			for (int i = 0; i < N; i++)
				right.add(i);
			TreeSet<Shortest> shortSet = new TreeSet<Shortest>();
			
			TreeNode node = root;
			for (int i = 0; i < path.size()-1; i++) {
				int u = path.get(i);
				int v = path.get(i+1);
				HashSet<Integer> middle = new HashSet<Integer>();
				fillMiddle(middle, right, node, v);
				for (int start : middle) {
					for (Map.Entry<Integer, Long> entry : graph[start].entrySet()) {
						int end = entry.getKey();
						long wei = entry.getValue();
						if (u == start && v == end) continue;
						if (u == start && node.parent == end) continue;
						if (left.contains(end)) {
							// need remove
							long dist = wei + distSrc[end] + distDest[start];
							if (dist >= INF)
								continue;
							shortSet.remove(new Shortest(min(start, end), max(start, end), dist));
						} else if (right.contains(end)) {
							// need add
							long dist = wei + distSrc[start] + distDest[end];
							if (dist >= INF)
								continue;
							shortSet.add(new Shortest(min(start, end), max(start, end), dist));
						}
					}
				}
				if (!shortSet.isEmpty()) {
					removeMap.put(new Edge(min(u, v), max(u, v)), shortSet.first().dist);
				} else {
					removeMap.put(new Edge(min(u, v), max(u, v)), INF);
				}
				for (TreeNode val : node.child) {
					if (val.i == v) {
						node = val;
						break;
					}
				}
				// move middle to left
				for (int val : middle) {
					left.add(val);
				}
			}
		}
		
		void fillMiddle(HashSet<Integer> middle, HashSet<Integer> right, TreeNode node, int v) {
			middle.add(node.i);
			right.remove(node.i);
			for (TreeNode val : node.child) {
				if (val.i == node.parent || val.i == v) continue;
				fillMiddle(middle, right, val, -1);
			}
		}
		
		static class Node implements Comparable<Node>{
			int i;
			long dist;
			int parent;
			public int compareTo(Node b) {
				if (dist != b.dist) return dist < b.dist? -1 : 1;
				return i - b.i;
			}
		}
		TreeNode dijkstra(boolean needTree, int src, int dest, long[] dist) {
			Arrays.fill(dist, INF);
			dist[src] = 0;
			Node[] state = new Node[N];
			TreeSet<Node> queue = new TreeSet<Node>();
			for (int i = 0; i < N; i++) {
				Node node = new Node();
				state[i] = node;
				node.i = i;
				node.parent = -1;
				node.dist = i == src ? 0 : INF;
				queue.add(node);
			}
			while (!queue.isEmpty()) {
				Node curr = queue.pollFirst();
				if (curr.dist == INF)
					break;
				int u = curr.i;
				dist[u] = curr.dist;
				for (Map.Entry<Integer, Long> entry : graph[u].entrySet()) {
					int v = entry.getKey();
					long wei = entry.getValue();
					if (dist[u] + wei < state[v].dist) {
						queue.remove(state[v]);
						state[v].dist = dist[u]+wei;
						state[v].parent = u;
						queue.add(state[v]);
					}
				}
			}
			if (!needTree) return null;
			TreeNode[] tree = new TreeNode[N];
			for (int i = 0; i < N; i++) {
				TreeNode node = new TreeNode();
				node.i = i;
				node.parent = -1;
				tree[i] = node;
			}
			for (int i = 0; i < N; i++) {
				if (state[i].parent != -1) {
					tree[i].parent = state[i].parent;
					tree[state[i].parent].child.add(tree[i]);
				}
			}
			// fill path
			int curr = dest;
			do {
				path.add(curr);
				curr = state[curr].parent;
			} while (curr != -1);
			Collections.reverse(path);
			return tree[src];
		}
		
	}
	public static void main(String[] args) {
		Foo47 foo = new Foo47();
		foo.main();
	}
}