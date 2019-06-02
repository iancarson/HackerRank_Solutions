import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


public class Solution {
	static int a[][];
	static int n, m, cheat, q, ex, ey, sx, sy, tx, ty;
	static boolean visited[][];
	static int dx[] = {0, 0, 1, -1};
	static int dy[] = {1, -1, 0, 0};
	
	static class CB {
		int x;
		int y;
		public CB(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override public int hashCode() {
			int res = 17;
			res = res * 31 + x;
			res = res * 31 + y;
			return res;
		}
		@Override public boolean equals(Object o) {
			if (!(o instanceof CB)) return false;
			CB cb = (CB)o;
			return x == cb.x && y == cb.y;
		}
		@Override public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}
	
	static boolean isok(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < m && a[x][y] == 1;
	}
	
	static int bfs(CB start, CB end) {
		Map<CB, Integer> map = new HashMap<CB, Integer>();
		map.put(start, 0);
		Queue<CB> queue = new ArrayDeque<CB>();
		queue.add(start);
		
		while(!queue.isEmpty()) {
			CB head = queue.poll();
			int dist = map.get(head);
			if (dist >= cheat) return -1;
			if (head.equals(end)) return dist;
			
			for (int k=0; k<dx.length; k++) {
				int x = head.x + dx[k];
				int y = head.y + dy[k];
				
				CB nextStep = new CB(x, y);
				if (isok(x, y)  && !map.containsKey(nextStep)) {
					map.put(nextStep, dist + 1);
					queue.add(nextStep);
				}
			}
		}
		return -1;
	}
	
	static int queue[] = new int[222 * 222 * 20];
	
	static int doit(CB caocaoInit, CB exit, CB emptyInit) {
		if (caocaoInit.equals(exit)) return 0;
		
		int d[] = new int[vIndex];
		Arrays.fill(d, -1);
		
		boolean visited[] = new boolean[vIndex];
		int head = 0, tail = 0;
		
		
		// do not forget
		a[caocaoInit.x][caocaoInit.y] = 0; 
		for (int i=0; i<4; i++) {
			int x = caocaoInit.x + dx[i];
			int y = caocaoInit.y + dy[i];
			int time = -1;
			if (isok(x, y)) {
				time = bfs(emptyInit, new CB(x, y));
				if (time < 0 || time > cheat) time = cheat;
			
				if (new CB(x, y).equals(exit)) {
					a[caocaoInit.x][caocaoInit.y] = 0;
					return time + 1;
				}
			
				int u = stateIndex[caocaoInit.x][caocaoInit.y][i];
				d[u] = time;
				queue[tail++] = u;
				visited[u] = true;
			}
		}
		a[caocaoInit.x][caocaoInit.y] = 1;
		
		
		while(head != tail) {
			int u = queue[head++];
			if (head == queue.length) head = 0;
			visited[u] = false;
			
			for (Edge edge : e[u]) {
				int v = edge.v;
				int w = edge.w;
				if (d[v] == -1 || d[u] + w < d[v]) {
					d[v] = d[u] + w;
					if (!visited[v]) {
						visited[v] = true;
						queue[tail++] = v;
						if (tail == queue.length) tail = 0;
					}
				}
			}
		}
		
		
		int ans = Integer.MAX_VALUE;
		for (int i=0; i<vIndex; i++)
			if (vInverse[i].equals(exit) && d[i] > -1 && d[i] < ans) ans = d[i];
		
		
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}
	
	static class Edge {
		int v, w;
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
		@Override public String toString() {
			return String.format("( v = %s, w = %d )", vInverse[v], w);
		}
	}
	
	@SuppressWarnings("unchecked")
	static ArrayList<Edge> e[] = new ArrayList[222 * 222 * 222];
	static int vIndex = 0;
	static CB vInverse[] = new CB[222 * 222 * 222]; 
	static int dirInverse[] = new int[222 * 222 * 222];
	static int stateIndex[][][];
	
	static int twin(int k) {
		if (k == 0) return 1;
		else if (k == 1) return 0;
		else if (k == 2) return 3;
		else return 2;
	}
	
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		n = cin.nextInt();
		m = cin.nextInt();
		cheat = cin.nextInt(); 
		q = cin.nextInt();
		a = new int[n][m];
		stateIndex = new int[n][m][4];
		for (int i=0; i<n; i++) 
			for (int j=0; j<m; j++)
				a[i][j] = cin.nextInt();

		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++)
				for (int k=0; k<4; k++) {
					vInverse[vIndex] = new CB(i, j);
					dirInverse[vIndex] = k;
					stateIndex[i][j][k] = vIndex++;
				}
		
		for (int i=0; i<vIndex; i++)
			e[i] = new ArrayList<Edge>();
		
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++) {
				if (a[i][j] == 0) continue;
				
				a[i][j] = 0;
				
				for (int k=0; k<4; k++) {
					int u = stateIndex[i][j][k];
					
					int x = i + dx[k];
					int y = j + dy[k];
					if (!isok(x, y)) continue;
					
					for (int l=0; l<4; l++) {
						int xx = i + dx[l];
						int yy = j + dy[l];
						if (!isok(xx, yy)) continue;
						
						int v = stateIndex[xx][yy][twin(l)];
						int w = bfs(new CB(x, y), new CB(xx, yy));
						if (w < 0 || w > cheat) w = cheat;
						w++;
						e[u].add(new Edge(v, w));
					}	
				}
				a[i][j] = 1;
			}
		
		
		for (int T=0; T<q; T++) {
			ex = cin.nextInt() - 1;
			ey = cin.nextInt() - 1;
			sx = cin.nextInt() - 1;
			sy = cin.nextInt() - 1;
			tx = cin.nextInt() - 1;
			ty = cin.nextInt() - 1;
			
			System.out.println(doit(new CB(sx, sy), new CB(tx, ty), new CB(ex, ey)));
		}
		cin.close();
	}

}